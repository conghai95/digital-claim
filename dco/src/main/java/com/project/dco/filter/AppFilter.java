package com.project.dco.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class AppFilter extends OncePerRequestFilter {

    @Autowired
    ObjectMapper mapper;

    protected static final String[] PUBLIC_URIS = new String[]{
            "/claims",
            "/users",
            "/file",
            "/mail",
            "/file",
            "/sysworkflow"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicURI(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public boolean isPublicURI(String uri) {
        for (String el : PUBLIC_URIS) {
            if (uri.startsWith(el)) {
                return true;
            }
        }
        return false;
    }

    public String[] getPublicUris() {
        return Stream.of(this.PUBLIC_URIS).map(e -> e + "/**").toArray(String[]::new);
    }
}
