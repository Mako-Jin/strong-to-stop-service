package com.smk.cpp.sts.core.filter;

import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.common.util.JwtTokenUtils;
import com.smk.cpp.sts.common.util.ServletUtils;
import com.smk.cpp.sts.core.security.token.DefaultToken;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:39
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = ServletUtils.getToken(request, IConstants.JWT_ACCESS_TOKEN_KEY);
            if (StringUtils.hasText(jwtToken) && jwtToken.startsWith(IConstants.JWT_TOKEN_PREFIX)) {
                final String authToken = jwtToken.substring(IConstants.JWT_TOKEN_PREFIX.length());
                if (!JwtTokenUtils.isTokenExpired(authToken)) {
                    UserDetailsModel userDetails = JwtTokenUtils.getUserFromToken(authToken);
                    if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        AbstractAuthenticationToken authentication = new DefaultToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    SecurityContextHolder.clearContext();
                    handlerExceptionResolver.resolveException(request, response,null,
                            new NonceExpiredException(ResultStatusEnums.NONCE_EXPIRED_EXCEPTION.getMessage()));
                    return;
                }
            }
        } catch (SignatureException signatureException) {
            SecurityContextHolder.clearContext();
            handlerExceptionResolver.resolveException(request, response,null,
                    new SignatureException(ResultStatusEnums.TOKEN_IS_INVALID.getMessage()));
            return;
        }
        filterChain.doFilter(request, response);
    }

}
