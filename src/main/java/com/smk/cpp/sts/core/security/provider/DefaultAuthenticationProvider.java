package com.smk.cpp.sts.core.security.provider;

import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.business.system.service.UserService;
import com.smk.cpp.sts.core.security.token.DefaultToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月26日 22:27
 * @Description:
 */
public class DefaultAuthenticationProvider
        implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
     * String)} on when the user is not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private UserCache userCache = new NullUserCache();

    /**
     * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
     * on when the user is not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private volatile String userNotFoundEncodedPassword;

    private UserService userDetailsService;

    private PasswordEncoder passwordEncoder;

    private boolean forcePrincipalAsString = false;

    protected boolean hideUserNotFoundExceptions = true;

    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    
    public DefaultAuthenticationProvider(UserService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(DefaultToken.class, authentication,
                () -> this.messages.getMessage("DefaultAuthenticationProvider.onlySupports",
                        "Only DefaultToken is supported"));
        String username = determineUsername(authentication);
        boolean cacheWasUsed = true;
        UserDetailsModel user = (UserDetailsModel) this.userCache.getUserFromCache(username);
        if (user == null) {
            cacheWasUsed = false;
            try {
                user = retrieveUser(username, (DefaultToken) authentication);
            }
            catch (UsernameNotFoundException ex) {
                logger.debug("Failed to find user '" + username + "'");
                if (!this.hideUserNotFoundExceptions) {
                    throw ex;
                }
                throw new BadCredentialsException(this.messages
                        .getMessage("DefaultAuthenticationProvider.badCredentials",
                                "Bad credentials"));
            }
            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }
        try {
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (DefaultToken) authentication);
        }
        catch (AuthenticationException ex) {
            if (!cacheWasUsed) {
                throw ex;
            }
            // There was a problem, so try again after checking
            // we're using latest data (i.e. not from the cache)
            cacheWasUsed = false;
            user = retrieveUser(username, (DefaultToken) authentication);
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (DefaultToken) authentication);
        }
        this.postAuthenticationChecks.check(user);
        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }
        Object principalToReturn = user;
        if (this.forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, DefaultToken authentication) 
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", 
                            "Bad credentials"));
        }
        UserDetailsModel userDetailsModel = (UserDetailsModel) userDetails;
        String presentedPassword = this.passwordEncoder.encode(authentication.getCredentials().toString());
        if (!this.passwordEncoder.matches(presentedPassword, userDetailsModel.getPassword())) { 
            logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
        }
    }

    protected UserDetailsModel retrieveUser(String username, DefaultToken authentication) throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            UserDetailsModel loadedUser = (UserDetailsModel) this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DefaultToken.class.isAssignableFrom(authentication);
    }

    private String determineUsername(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(DefaultToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

    protected Authentication createSuccessAuthentication(
            Object principal, Authentication authentication, UserDetailsModel user) {
        boolean upgradeEncoding = this.userDetailsService != null
                && this.passwordEncoder.upgradeEncoding(user.getPassword());
        if (upgradeEncoding) {
            String presentedPassword = authentication.getCredentials().toString();
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            user = (UserDetailsModel) this.userDetailsService.updatePassword(user, newPassword);
        }
        DefaultToken result = new DefaultToken(principal, authentication.getCredentials(),
                user.getAuthorities());
        result.setDetails(authentication.getDetails());
        logger.debug("Authenticated user");
        return result;
    }

    public UserService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                DefaultAuthenticationProvider.logger
                        .debug("Failed to authenticate since user account is locked");
                throw new LockedException(DefaultAuthenticationProvider.this.messages
                        .getMessage("DefaultAuthenticationProvider.locked",
                                "User account is locked"));
            }
            if (!user.isEnabled()) {
                DefaultAuthenticationProvider.logger
                        .debug("Failed to authenticate since user account is disabled");
                throw new DisabledException(DefaultAuthenticationProvider.this.messages
                        .getMessage("DefaultAuthenticationProvider.disabled",
                                "User is disabled"));
            }
            if (!user.isAccountNonExpired()) {
                DefaultAuthenticationProvider.logger
                        .debug("Failed to authenticate since user account has expired");
                throw new AccountExpiredException(DefaultAuthenticationProvider.this.messages
                        .getMessage("DefaultAuthenticationProvider.expired",
                                "User account has expired"));
            }
        }

    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                DefaultAuthenticationProvider.logger
                        .debug("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException(DefaultAuthenticationProvider.this.messages
                        .getMessage("DefaultAuthenticationProvider.credentialsExpired",
                                "User credentials have expired"));
            }
        }

    }
    
}
