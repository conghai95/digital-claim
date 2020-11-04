package com.project.dco.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.api.HttpStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        AppResponseEntity res = AppResponseEntity.withError(new HttpStatusResponse("403", HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase()));
        response.getOutputStream().write(mapper.writeValueAsBytes(res.getBody()));
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
