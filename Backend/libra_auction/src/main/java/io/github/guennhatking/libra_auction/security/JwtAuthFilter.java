package io.github.guennhatking.libra_auction.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtRSA jwtRSA;
    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtRSA jwtRSA, JwtUtils jwtUtils) {
        this.jwtRSA = jwtRSA;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip WebSocket
        String upgrade = request.getHeader("Upgrade");
        String connection = request.getHeader("Connection");
        if ("websocket".equalsIgnoreCase(upgrade) &&
                connection != null &&
                connection.toUpperCase().contains("UPGRADE")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = null;
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            if (token == null) {
                token = getTokenFromCookie(request);
            }

            if (token != null && jwtRSA.verifyToken(token, jwtUtils.getPublicKey())) {
                String userId = jwtRSA.extractClaim(token, "sub");

                JwtUserDetails principal = new JwtUserDetails(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, new ArrayList<>());

                logger.info("PRINCIPAL: " + principal.toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication", e);
        }
        logger.info("REQUEST URI: " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;

        for (var cookie : request.getCookies()) {
            if ("jwtToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
