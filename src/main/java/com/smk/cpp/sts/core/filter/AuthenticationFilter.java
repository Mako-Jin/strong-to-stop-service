package com.smk.cpp.sts.core.filter;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.SecurityServiceException;
import com.smk.cpp.sts.base.model.AuthenticationParameterModel;
import com.smk.cpp.sts.common.properties.AuthenticationParameterProperties;
import com.smk.cpp.sts.common.util.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月30日 19:21
 * @Description:
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final String SPRING_SECURITY_FORM_AUTH_TYPE_KEY = "authType";
    private String authTypeParameter = SPRING_SECURITY_FORM_AUTH_TYPE_KEY;
    
    private AuthenticationParameterProperties authenticationParameterProperties;

    private boolean postOnly = true;
    
    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
        logger.debug("AuthenticationFilter loading ...");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 只允许 /login 为 post 的的请求进入
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            logger.error("Authentication method not supported: {}", request.getMethod());
            throw new SecurityServiceException(ResultStatusEnums.REQUEST_METHOD_NOT_SUPPORT.getStatusKey());
        }
        try {
            AbstractAuthenticationToken authenticationToken = getAuthenticationToken(request);
            if (authenticationToken == null) {
                throw new SecurityServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR.getStatusKey());
            }
            setDetails(request, authenticationToken);
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (ClassNotFoundException e) {
            logger.debug("request param token class name is ==> {}", e.getMessage());
            throw new SecurityServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR.getStatusKey());
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException exception) {
            logger.debug("get authentication token ==> {}", exception.getMessage());
            throw new AuthenticationServiceException(exception.getMessage());
        }
    }

    protected AbstractAuthenticationToken getAuthenticationToken(HttpServletRequest request)
            throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String requestBody = HttpRequestUtils.getBody(request);
        String authType = obtainAuthenticationType(request, requestBody);
        Map<String, AuthenticationParameterModel> authParam =
                this.authenticationParameterProperties.getParam();
        if (!StringUtils.hasText(authType) || authParam == null 
                || !authParam.containsKey(authType)) {
            return null;
        }
        AuthenticationParameterModel authenticationParameterModel =
                authParam.get(authType);
        if (authenticationParameterModel.isHasImageCode()
                && StringUtils.hasText(authenticationParameterModel.getImageCodeParameter())) {
            String imageVerificationCode = obtainImageVerificationCode(
                    request, authenticationParameterModel.getImageCodeParameter(), requestBody);
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new SecurityServiceException(ResultStatusEnums.IMAGE_CODE_IS_EXPIRED.getStatusKey());
            }
            String expectImageCode = 
                    session.getAttribute(authenticationParameterModel.getImageCodeParameter())
                    .toString();
            if (!expectImageCode.equals(imageVerificationCode)) {
                logger.debug("expectImageCode ==> {} imageVerificationCode ==> {}",
                        expectImageCode, imageVerificationCode);
                throw new SecurityServiceException(ResultStatusEnums.AUTHENTICATION_IMAGE_CODE_ERROR.getStatusKey());
            }
        }
        Class targetClass = Class.forName(authenticationParameterModel.getTokenClassName());
        String principal = obtainPrincipal(
                request, authenticationParameterModel.getUsernameParameter(), requestBody);
        String credentials = obtainCredentials(
                request, authenticationParameterModel.getPasswordParameter(), requestBody);
        AbstractAuthenticationToken authenticationToken =
                (AbstractAuthenticationToken) targetClass.getDeclaredConstructor
                    (Object.class, Object.class).newInstance(principal, credentials);
        return authenticationToken;
    }

    protected String obtainImageVerificationCode(
            HttpServletRequest request, String imageVerificationCodeKey, String requestBody) {
        String imageVerificationCode = HttpRequestUtils
                .getParameterValue(request, imageVerificationCodeKey, requestBody);
        return StringUtils.hasText(imageVerificationCode) ? imageVerificationCode : imageVerificationCode;
    }

    protected String obtainAuthenticationType(HttpServletRequest request, String requestBody) {
        String authType = HttpRequestUtils.getParameterValue(request, authTypeParameter, requestBody);
        return StringUtils.hasText(authType) ? authType : "";
    }

    protected String obtainPrincipal(
            HttpServletRequest request, String principalKey, String requestBody) {
        String principal = HttpRequestUtils.getParameterValue(request, principalKey, requestBody);
        return StringUtils.hasText(principal) ? principal : principalKey;
    }

    protected String obtainCredentials(
            HttpServletRequest request, String credentialsKey, String requestBody) {
        String credentials = HttpRequestUtils.getParameterValue(request, credentialsKey, requestBody);
        return StringUtils.hasText(credentials) ? credentials : credentialsKey;
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected <T extends AbstractAuthenticationToken> void setDetails(
            HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public AuthenticationParameterProperties getAuthenticationParameterProperties() {
        return authenticationParameterProperties;
    }

    public void setAuthenticationParameterProperties(
            AuthenticationParameterProperties authenticationParameterProperties) {
        this.authenticationParameterProperties = authenticationParameterProperties;
    }
}
