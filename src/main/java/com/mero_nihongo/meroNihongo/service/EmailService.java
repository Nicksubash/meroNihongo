package com.mero_nihongo.meroNihongo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;
    
    @Value("${spring.mail.username:meronihongo@gmail.com}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Password Reset - MeroNihongo");
            
            String resetUrl = frontendUrl + "/reset-password?token=" + token;
            String emailBody = buildPasswordResetEmailBody(resetUrl);
            
            message.setText(emailBody);
            mailSender.send(message);
            
            System.out.println("Password reset email sent to: " + toEmail);
            System.out.println("Reset URL: " + resetUrl); // For testing purposes
            
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    private String buildPasswordResetEmailBody(String resetUrl) {
        return """
            Hello,
            
            You have requested to reset your password for your MeroNihongo account.
            
            Please click the link below to reset your password:
            %s
            
            This link will expire in 1 hour for security reasons.
            
            If you did not request this password reset, please ignore this email.
            
            Best regards,
            The MeroNihongo Team
            """.formatted(resetUrl);
    }

    // For testing without actual email server
    public void sendPasswordResetEmailConsole(String toEmail, String token) {
        String resetUrl = frontendUrl + "/reset-password?token=" + token;
        
        System.out.println("=== PASSWORD RESET EMAIL (CONSOLE MODE) ===");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: Password Reset - MeroNihongo");
        System.out.println("Reset URL: " + resetUrl);
        System.out.println("Token: " + token);
        System.out.println("==========================================");
    }
}
