package com.rabin.onlinebookstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/auth/") || request.getServletPath().contains("/api/books")) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtService jwtService = new JwtService();

        String token = JwtService.extractToken(request);
        boolean valid = jwtService.validateToken(token);
        if (!valid) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseWrapper responseWrapper = new ResponseWrapper(false, 401, "Invalid or Missing token", null);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
        } else {
            Claims claims = jwtService.decodeToken(token);
            int userId = claims.get("userId", Integer.class);
            String username = claims.get("username", String.class);
            String role = claims.get("role", String.class);

            if (request.getServletPath().contains("/api/admin") && !role.equals("ADMIN")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ResponseWrapper responseWrapper = new ResponseWrapper(false, 401, "Not authorized", null);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
            } else {
                request.setAttribute("userId", userId);
                request.setAttribute("username", username);
                request.setAttribute("role", role);
                filterChain.doFilter(request, response);
            }
        }
    }
}
