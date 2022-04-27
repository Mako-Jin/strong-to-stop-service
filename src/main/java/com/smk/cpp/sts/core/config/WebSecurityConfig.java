package com.smk.cpp.sts.core.config;

import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.business.system.service.UserService;
import com.smk.cpp.sts.common.properties.AuthenticationParameterProperties;
import com.smk.cpp.sts.core.crypto.NoCryptPasswordEncoder;
import com.smk.cpp.sts.core.crypto.PBKDF2PasswordEncoder;
import com.smk.cpp.sts.core.filter.AuthenticationFilter;
import com.smk.cpp.sts.core.filter.JwtTokenFilter;
import com.smk.cpp.sts.core.interceptor.SecurityInterceptor;
import com.smk.cpp.sts.core.security.handler.AccessDecisionHandler;
import com.smk.cpp.sts.core.security.handler.AccessLimitHandler;
import com.smk.cpp.sts.core.security.handler.AuthenticationEntryPointHandler;
import com.smk.cpp.sts.core.security.handler.LoginFailureHandler;
import com.smk.cpp.sts.core.security.handler.LoginSuccessHandler;
import com.smk.cpp.sts.core.security.handler.LogoutHandler;
import com.smk.cpp.sts.core.security.handler.MetadataSourceHandler;
import com.smk.cpp.sts.core.security.vote.RoleVoteHandler;
import com.smk.cpp.sts.core.security.vote.WhiteListVoteHandler;
import com.smk.cpp.sts.core.security.provider.DefaultAuthenticationProvider;
import com.smk.cpp.sts.core.security.provider.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月11日 19:01
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationParameterProperties authenticationParameterProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(
            Arrays.asList(
                defaultAuthenticationProvider(),
                phoneCodeAuthenticationProvider()
            )
        );
    }

    @Bean
    protected DefaultAuthenticationProvider defaultAuthenticationProvider() {
        return new DefaultAuthenticationProvider(
                userService, pbkdf2PasswordEncoder()
        );
    }

    @Bean
    protected SmsCodeAuthenticationProvider phoneCodeAuthenticationProvider() {
        return new SmsCodeAuthenticationProvider(
                userService, noCryptPasswordEncoder()
        );
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder noCryptPasswordEncoder() {
        return new NoCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder pbkdf2PasswordEncoder() {
        return new PBKDF2PasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disables the AbstractHttpConfigurer by removing it. 
                // After doing so a fresh version of the configuration can be applied.
                .csrf((csrf ->
                    csrf.disable()
                ))
                .cors(( cors ->
                    cors.disable()
                ))
                // Spring Security will never create an HttpSession 
                // and it will never use it to obtain the SecurityContext
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1)
                )
                .authorizeRequests((authorizeRequests) ->
                    authorizeRequests
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/auth/v1/token-refresh").permitAll()
                    .anyRequest().authenticated()
                        // fullyAuthenticated 每次认证之后，就可以访问;
                        // authenticated 认证和记住，都可以访问
                )
                .formLogin((formLogin) ->
                    formLogin
                        .loginProcessingUrl("/login")
                        .usernameParameter(IConstants.USERNAME)
                        .passwordParameter(IConstants.PASSWORD)
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        // .loginPage()  配置登录页
                        // .defaultSuccessUrl("") 登录成功默认访问地址
                        .permitAll()
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(new AuthenticationEntryPointHandler())
                                .accessDeniedHandler(new AccessLimitHandler())
                )
                .logout((logout) ->
                        logout.logoutSuccessHandler(new LogoutHandler())
                )
                // .headers((headers) -> 
                //     headers.contentTypeOptions().disable()        
                // )
                .addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
    }
    
    @Bean
    public JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        authenticationFilter.setAuthenticationParameterProperties(authenticationParameterProperties);
        return authenticationFilter;
    }

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        securityInterceptor.setAccessDecisionManager(getAccessDecisionManager());
        securityInterceptor.setSecurityMetadataSource(getMetadataSourceHandler());
        securityInterceptor.setAuthenticationManager(authenticationManager());
        return securityInterceptor;
    }

    private AccessDecisionManager getAccessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WhiteListVoteHandler());
        decisionVoters.add(new RoleVoteHandler());
        return new AccessDecisionHandler(decisionVoters);
    }

    @Bean
    public MetadataSourceHandler getMetadataSourceHandler() {
        return new MetadataSourceHandler();
    }

}
