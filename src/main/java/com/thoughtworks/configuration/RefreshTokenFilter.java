package com.thoughtworks.configuration;

import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class RefreshTokenFilter extends GenericFilterBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication instanceof KeycloakAuthenticationToken)) {
            chain.doFilter(request, response);

            return;
        }
        OidcKeycloakAccount account = ((KeycloakAuthenticationToken) authentication).getAccount();
        RefreshableKeycloakSecurityContext keycloakSecurityContext = (RefreshableKeycloakSecurityContext) account.getKeycloakSecurityContext();

        if(!keycloakSecurityContext.isActive()) {
            keycloakSecurityContext.refreshExpiredToken(true);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }
}
