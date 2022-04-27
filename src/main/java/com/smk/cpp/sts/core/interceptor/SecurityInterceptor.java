package com.smk.cpp.sts.core.interceptor;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月13日 22:03
 * @Description:
 */
public class SecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final String FILTER_APPLIED = "SanMako_Strong_to_stop_filterSecurityInterceptor_filterApplied";

    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    
    private boolean observeOncePerRequest = true;

    private ApplicationEventPublisher eventPublisher;

    private void publishEvent(ApplicationEvent event) {
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(event);
        }
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource().getAttributes(object);
        if (CollectionUtils.isEmpty(attributes)) {
            AccessDeniedException ex =new AccessDeniedException(ResultStatusEnums.RESOURCE_WITHOUT_PERMISSIONS.getStatusKey());
            this.publishEvent(new AuthorizationFailureEvent(object, attributes, authentication, ex));
            throw ex;
        }
        return super.beforeInvocation(object);
    }

    /**
     * Not used (we rely on IoC container lifecycle services instead)
     * @param arg0 ignored
     *
     */
    @Override
    public void init(FilterConfig arg0) {
    }

    /**
     * Not used (we rely on IoC container lifecycle services instead)
     */
    @Override
    public void destroy() {
    }

    /**
     * Method that is actually called by the filter chain. Simply delegates to the
     * {@link #invoke(FilterInvocation)} method.
     * @param request the servlet request
     * @param response the servlet response
     * @param chain the filter chain
     * @throws IOException if the filter chain fails
     * @throws ServletException if the filter chain fails
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        invoke(new FilterInvocation(request, response, chain));
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // first time this request being called, so perform security checking
        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }
        InterceptorStatusToken token = this.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    private boolean isApplied(FilterInvocation filterInvocation) {
        return (filterInvocation.getRequest() != null)
                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
    }

    /**
     * Indicates whether once-per-request handling will be observed. By default this is
     * <code>true</code>, meaning the <code>FilterSecurityInterceptor</code> will only
     * execute once-per-request. Sometimes users may wish it to execute more than once per
     * request, such as when JSP forwards are being used and filter security is desired on
     * each included fragment of the HTTP request.
     * @return <code>true</code> (the default) if once-per-request is honoured, otherwise
     * <code>false</code> if <code>FilterSecurityInterceptor</code> will enforce
     * authorizations for each and every fragment of the HTTP request.
     */
    public boolean isObserveOncePerRequest() {
        return this.observeOncePerRequest;
    }

    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
        this.observeOncePerRequest = observeOncePerRequest;
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
