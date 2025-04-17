package com.mero_nihongo.meroNihongo.security;

import com.mero_nihongo.meroNihongo.dto.LoginRequest;
import com.mero_nihongo.meroNihongo.dto.RegisterRequest;
import com.mero_nihongo.meroNihongo.model.User;
import com.mero_nihongo.meroNihongo.repository.UserRepository;
import com.mero_nihongo.meroNihongo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public String authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) throw new RuntimeException("Invalid Credentials");

        //mail
        if(!user.getEmail().equalsIgnoreCase(request.getEmail())) throw  new RuntimeException("Wrong Email Address!!");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getUsername());
    }

    public User getCurrentUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

}
