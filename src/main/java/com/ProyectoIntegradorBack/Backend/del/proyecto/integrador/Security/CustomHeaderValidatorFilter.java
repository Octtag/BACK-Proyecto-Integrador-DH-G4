package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomHeaderValidatorFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomHeaderValidatorFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Custom filter called...");
        if (StringUtils.isEmpty(request.getHeader("X-Application-Name"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
