package com.mero_nihongo.meroNihongo.controller;

import com.mero_nihongo.meroNihongo.dto.LoginRequest;
import com.mero_nihongo.meroNihongo.dto.RegisterRequest;
import com.mero_nihongo.meroNihongo.model.User;
import com.mero_nihongo.meroNihongo.security.AuthService;
import com.mero_nihongo.meroNihongo.service.LoginResponse;
import com.mero_nihongo.meroNihongo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Google OAuth2 redirect
    @GetMapping("/google")
    public RedirectView googleLogin() {
        return new RedirectView("/oauth2/authorization/google");
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        // First try to get from JWT token
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                String username = jwtUtil.extractUsername(jwt);
                User user = authService.getCurrentUser(username);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                System.err.println("JWT extraction failed: " + e.getMessage());
            }
        }

        // Fallback to Spring Security context (for OAuth2)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username = null;

            if (principal instanceof org.springframework.security.core.userdetails.User) {
                username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            } else if (principal instanceof DefaultOAuth2User) {
                Object email = ((DefaultOAuth2User) principal).getAttributes().get("email");
                if (email instanceof String) {
                    username = (String) email;
                }
            }

            System.out.println("Principal class: " + principal.getClass().getName());

            if (username != null) {
                User user = authService.getCurrentUser(username);
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
