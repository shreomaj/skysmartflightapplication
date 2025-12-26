package com.ai.skysmart.config;

import com.ai.skysmart.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // ------------------------
        // 1. Extract Token
        // ------------------------
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            log.info("JWT Token extracted: {}", token);

            try {
                email = jwtService.extractEmail(token);
                log.info("Email extracted from JWT: {}", email);
            } catch (Exception e) {
                log.error("Error extracting email from token: {}", e.getMessage());
            }
        } else {
            log.warn("No Bearer token found");
        }

        // ------------------------
        // 2. Authenticate User
        // ------------------------
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("Loaded user: {}", userDetails.getUsername());
            log.info("DB Roles: {}", userDetails.getAuthorities());

            if (jwtService.validateToken(token, userDetails)) {

                String role = jwtService.extractRole(token);   // ROLE_USER / ROLE_ADMIN
                log.info("Role extracted from JWT: {}", role);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                log.info("User '{}' authenticated with role {}", email, role);
            } else {
                log.warn("JWT validation failed for token {}", token);
            }
        }

        chain.doFilter(request, response);
    }
}


//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        String header = request.getHeader("Authorization");
//        String token = null;
//        String email = null;
//
//        if (header != null && header.startsWith("Bearer ")) {
//            token = header.substring(7).trim();
//            email = jwtService.extractEmail(token);
//        }
//
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//            if (jwtService.validateToken(token, userDetails)) {
//
//                // read role exactly as stored
//                String role = jwtService.extractRole(token);   // returns "ROLE_USER"
//
//                List<SimpleGrantedAuthority> authorities =
//                        List.of(new SimpleGrantedAuthority(role));  // ✔ No extra ROLE_
//
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                authorities
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}
