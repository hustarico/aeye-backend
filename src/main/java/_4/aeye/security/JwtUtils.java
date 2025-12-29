package _4.aeye.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    /* =========================
       TOKEN GENERATION
       ========================= */

    public String generateJwtToken(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String roleName = mapRoleIdToRoleName(userDetails.getRoleId());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", userDetails.getId())
                .claim("role", roleName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    /* =========================
       TOKEN PARSING
       ========================= */

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public Integer getUserIdFromToken(String token) {
        return getAllClaims(token).get("userId", Integer.class);
    }

    public String getRoleFromToken(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    /* =========================
       TOKEN VALIDATION
       ========================= */

    public boolean validateJwtToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            System.err.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }

    /* =========================
       INTERNAL HELPERS
       ========================= */

    private Claims getAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String mapRoleIdToRoleName(int roleId) {
        return switch (roleId) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_MANAGER";
            case 3 -> "ROLE_USER";
            default -> throw new IllegalArgumentException(
                    "Invalid role id: " + roleId
            );
        };
    }
}
