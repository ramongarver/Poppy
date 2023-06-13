package com.ramongarver.poppy.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.info("Incoming request: method={}, path={}", request.getMethod(), request.getRequestURI());

        // Continue processing the rest of the filter chain.
        filterChain.doFilter(request, response);

        LOGGER.info("Outgoing response: status={}", response.getStatus());
    }

}
