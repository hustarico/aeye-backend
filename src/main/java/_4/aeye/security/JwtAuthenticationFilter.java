package _4.aeye.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import _4.aeye.services.TokenBlacklistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Check if token is blacklisted (logged out)
                if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
                    logger.warn("Attempt to use blacklisted token");
                    filterChain.doFilter(request, response);
                    return;
                }

                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                
                // Extract roles from JWT token
                List<String> rolesFromToken = jwtUtils.getRolesFromJwtToken(jwt);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                
                if (rolesFromToken != null && !rolesFromToken.isEmpty()) {
                    for (String role : rolesFromToken) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    logger.debug("Loaded roles from JWT token for user: " + username);
                } else {
                    // Fallback to authorities from UserDetails if roles not in token
                    authorities.addAll(userDetails.getAuthorities());
                    logger.debug("Using fallback authorities from UserDetails for user: " + username);
                }
                
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication set for user: " + username);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}
