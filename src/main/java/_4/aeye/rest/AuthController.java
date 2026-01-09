package _4.aeye.rest;

import _4.aeye.dtos.AuthResponse;
import _4.aeye.dtos.LoginRequest;
import _4.aeye.dtos.LogoutRequest;
import _4.aeye.dtos.RegisterRequest;
import _4.aeye.entites.User;
import _4.aeye.security.CustomUserDetails;
import _4.aeye.security.JwtUtils;
import _4.aeye.services.TokenBlacklistService;
import _4.aeye.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserService userService;

        @Autowired
        private TokenBlacklistService tokenBlacklistService;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
                try {
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        loginRequest.getUsername(),
                                                        loginRequest.getPassword()));

                        String jwt = jwtUtils.generateJwtToken(authentication);

                        return ResponseEntity.ok(new AuthResponse(
                                        jwt,
                                        loginRequest.getUsername(),
                                        "Login successful"));
                } catch (AuthenticationException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new AuthResponse(null, null, "Invalid username or password"));
                }
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
                try {
                        User user = userService.registerUser(registerRequest);

                        // Authenticate the newly registered user to generate token
                        // We use the returned User object directly to avoid database round-trip or
                        // transaction visibility issues
                        CustomUserDetails userDetails = new CustomUserDetails(user);

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());

                        String jwt = jwtUtils.generateJwtToken(authentication);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(new AuthResponse(
                                                        jwt,
                                                        registerRequest.getUsername(),
                                                        "Registration successful"));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new AuthResponse(null, null, "Registration failed: " + e.getMessage()));
                }
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
                try {
                        if (logoutRequest.getToken() == null || logoutRequest.getToken().trim().isEmpty()) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(new AuthResponse(null, null, "Token is required for logout"));
                        }

                        // Validate token before blacklisting
                        if (!jwtUtils.validateJwtToken(logoutRequest.getToken())) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .body(new AuthResponse(null, null, "Invalid or expired token"));
                        }

                        // Blacklist the token (revoke it)
                        tokenBlacklistService.blacklistToken(logoutRequest.getToken());

                        return ResponseEntity.ok(new AuthResponse(
                                        null,
                                        null,
                                        "Logout successful"));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(new AuthResponse(null, null, "Logout failed: " + e.getMessage()));
                }
        }
}
