package com.mero_nihongo.meroNihongo.service;

import com.mero_nihongo.meroNihongo.model.User;
import com.mero_nihongo.meroNihongo.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final Map<String, String> resetCodes = new HashMap<>();

    public EmailService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendResetCode(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) throw  new UsernameNotFoundException("User not Exist");
        String code = String.valueOf(new Random().nextInt(900000)+10000); //6 digit code
        resetCodes.put(email,code);

        //sending mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("nicksubash9@gmail.com");
        message.setSubject("Password Reset Code");
        message.setText("Your Password reset code is :" + code );
        javaMailSender.send(message);
    }

    public boolean resetPassword(String email, String code, String newPassword) {
        String storedCode = resetCodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
            resetCodes.remove(email);
            return true;
        }
        return false;
    }
}
