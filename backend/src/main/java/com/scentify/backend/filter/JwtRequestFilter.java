package com.scentify.backend.filter;

import com.scentify.backend.service.CustomUserDetailsService;
import com.scentify.backend.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(
        @org.springframework.lang.NonNull HttpServletRequest request,
        @org.springframework.lang.NonNull HttpServletResponse response,
        @org.springframework.lang.NonNull FilterChain filterChain
) throws ServletException, IOException {

    String uri = request.getRequestURI();

    // ✅ Bypass endpoint publik tanpa validasi JWT
    if (uri.startsWith("/api/auth/")
            || uri.equals("/api/admin/register")
            || uri.equals("/api/buyer/register")
            || uri.startsWith("/api/products")) {
        filterChain.doFilter(request, response);
        return;
    }

    final String authHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        jwt = authHeader.substring(7);
        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            // Token invalid atau expired
        }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var userDetails = userDetailsService.loadUserByUsername(username);

        // Ambil role dari token
        String role = jwtUtil.extractClaim(jwt, claims -> claims.get("role", String.class));

        // ⛔ Pembatasan akses admin
        if (uri.startsWith("/api/admin") && !"ADMIN".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
            var authorities = List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
    }
}