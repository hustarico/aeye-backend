package _4.aeye.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service to maintain a blacklist of revoked/logged-out JWT tokens.
 * In production, use Redis for persistence and distributed cache.
 */
@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * Add a token to the blacklist (revoke it)
     * @param token JWT token to blacklist
     */
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Check if a token is blacklisted
     * @param token JWT token to check
     * @return true if token is blacklisted, false otherwise
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    /**
     * Clear all blacklisted tokens (optional, for debugging)
     */
    public void clearBlacklist() {
        blacklistedTokens.clear();
    }

    /**
     * Get the count of blacklisted tokens (optional, for monitoring)
     */
    public int getBlacklistSize() {
        return blacklistedTokens.size();
    }
}
