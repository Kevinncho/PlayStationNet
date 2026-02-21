package com.kefessan.playstationet.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                               UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    System.out.println("JWT FILTER CONSTRUCTOR CALLED");
}

    // 🔑 AQUÍ se excluyen endpoints públicos
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       return false;
    }

   @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain)
        throws ServletException, IOException {

    System.out.println("======== JWT FILTER ========");
    System.out.println("URI: " + request.getRequestURI());
    System.out.println("Method: " + request.getMethod());

    String header = request.getHeader("Authorization");
    System.out.println("Authorization header: " + header);

    System.out.println("Authentication BEFORE: " +
            SecurityContextHolder.getContext().getAuthentication());

    if (header != null && header.startsWith("Bearer ")) {

        String token = header.substring(7);
        System.out.println("Token extracted: " + token);

        String username = jwtUtil.extractUsername(token);
        System.out.println("Username from token: " + username);

        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            System.out.println("Authorities from UserDetails: " +
                    userDetails.getAuthorities());

            if (jwtUtil.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("Authentication AFTER SET: " +
                        SecurityContextHolder.getContext().getAuthentication());
            }
        }
    }

    chain.doFilter(request, response);
}

}
