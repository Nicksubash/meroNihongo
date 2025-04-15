package com.mero_nihongo.meroNihongo.security;

import com.mero_nihongo.meroNihongo.model.User;
import com.mero_nihongo.meroNihongo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        if (email != null) {
            User existingUser = userRepository.findByEmail(email);
            if (existingUser == null) {
                String randomPassword = UUID.randomUUID().toString();
                User newUser = new User();
                newUser.setUsername(email);
                newUser.setEmail(email);
                newUser.setPassword(passwordEncoder.encode(randomPassword));
                userRepository.save(newUser);
            }
        }

        return oAuth2User;
    }
}