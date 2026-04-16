package io.github.guennhatking.libra_auction.controllers;

import io.github.guennhatking.libra_auction.services.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller for email notifications
 */
@RestController
@RequestMapping("/api/test")
public class TestEmailController {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailNotificationService emailNotificationService;
    
    /**
     * Send a test email
     * @param toEmail Recipient email address
     * @param subject Email subject
     * @param body Email body
     * @return Response with success status
     */
    @GetMapping("/send-email")
    public ResponseEntity<String> sendTestEmail(
            @RequestParam String toEmail,
            @RequestParam(defaultValue = "Test Email from Libra Auction") String subject,
            @RequestParam(defaultValue = "This is a test email to verify the email notification system is working correctly.") String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("noreply@auction.com");
            
            mailSender.send(message);
            return ResponseEntity.ok("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
    
    /**
     * Send a test welcome email
     * @param email Email address
     * @return Response with success status
     */
    @GetMapping("/send-welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestParam String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[Libra Auction] Chào mừng bạn đến với nền tảng đấu giá của chúng tôi");
            message.setText("Xin chào,\n\n" +
                    "Cảm ơn bạn đã đăng ký tài khoản trên Libra Auction!\n\n" +
                    "Tài khoản của bạn đã được tạo thành công và sẵn sàng để sử dụng.\n\n" +
                    "Bạn có thể:\n" +
                    "- Tạo phiên đấu giá cho các sản phẩm của bạn\n" +
                    "- Tham gia đấu giá trên các sản phẩm khác\n" +
                    "- Quản lý hồ sơ của bạn\n\n" +
                    "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.\n\n" +
                    "Trân trọng,\n" +
                    "Libra Auction Team");
            message.setFrom("noreply@auction.com");
            
            mailSender.send(message);
            return ResponseEntity.ok("Welcome email sent successfully to " + email);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending welcome email: " + e.getMessage());
        }
    }
}
