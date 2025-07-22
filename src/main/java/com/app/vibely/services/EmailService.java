
package com.app.vibely.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationCode(String toEmail, String code, String purpose) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Vibely - Verification Code");

            String emailBody = buildEmailBody(code, purpose);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("Verification email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send verification email");
        }
    }

    private String buildEmailBody(String code, String purpose) {
        return switch (purpose) {
            case "registration" -> String.format(
                    "Welcome to Vibely!\n\n" +
                            "Thank you for registering. Please use the following verification code to activate your account:\n\n" +
                            "Verification Code: %s\n\n" +
                            "This code will expire in 15 minutes.\n\n" +
                            "Best regards,\n" +
                            "The Vibely Team", code
            );
            case "login" -> String.format(
                    "Vibely - Account Verification Required\n\n" +
                            "Your account needs to be verified before you can log in. Please use the following verification code:\n\n" +
                            "Verification Code: %s\n\n" +
                            "This code will expire in 15 minutes.\n\n" +
                            "Best regards,\n" +
                            "The Vibely Team", code
            );
            case "password_reset" -> String.format(
                    "Vibely - Password Reset Request\n\n" +
                            "You have requested to reset your password. Please use the following verification code:\n\n" +
                            "Verification Code: %s\n\n" +
                            "This code will expire in 15 minutes.\n\n" +
                            "If you didn't request this, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "The Vibely Team", code
            );
            default -> String.format(
                    "Vibely - Verification Code\n\n" +
                            "Your verification code is: %s\n\n" +
                            "This code will expire in 15 minutes.\n\n" +
                            "Best regards,\n" +
                            "The Vibely Team", code
            );
        };
    }
}