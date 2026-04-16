package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.models.NguoiDung;
import io.github.guennhatking.libra_auction.models.PhienDauGia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Service to send email notifications for auction events
 */
@Service
public class EmailNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.from:noreply@auction.com}")
    private String fromEmail;
    
    @Value("${app.auction.name:Libra Auction}")
    private String auctionName;
    
    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    /**
     * Send notification when an auction has started
     * @param auction The auction
     */
    public void sendAuctionStartedNotification(PhienDauGia auction) {
        try {
            String productName = auction.getTaiSan() != null ? auction.getTaiSan().getTenTaiSan() : "N/A";
            String auctionId = auction.getId();
            String startTime = auction.getThoiGianBatDau().format(DATE_FORMATTER);
            
            // For now, we send to the auction creator
            if (auction.getNguoiTao() != null && auction.getNguoiTao().getEmail() != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(auction.getNguoiTao().getEmail());
                message.setSubject("[" + auctionName + "] Phiên đấu giá của bạn đã bắt đầu");
                message.setText(buildAuctionStartedEmailBody(productName, auctionId, startTime));
                
                mailSender.send(message);
                logger.info("Auction started notification sent to {}", auction.getNguoiTao().getEmail());
            }
        } catch (Exception e) {
            logger.error("Error sending auction started notification: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Send notification to the auction winner
     * @param auction The auction
     * @param winner The winner user
     * @param finalPrice The final winning price
     */
    public void sendAuctionWinnerNotification(PhienDauGia auction, NguoiDung winner, Long finalPrice) {
        try {
            if (winner == null || winner.getEmail() == null) {
                logger.warn("Cannot send winner notification: winner email is null");
                return;
            }
            
            String productName = auction.getTaiSan() != null ? auction.getTaiSan().getTenTaiSan() : "N/A";
            String auctionId = auction.getId();
            String endTime = auction.getThoiGianBatDau().plusSeconds(auction.getThoiLuong()).format(DATE_FORMATTER);
            String formattedPrice = String.format("%,d", finalPrice);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(winner.getEmail());
            message.setSubject("[" + auctionName + "] Xin chúc mừng! Bạn đã thắng cuộc đấu giá");
            message.setText(buildWinnerNotificationEmailBody(productName, auctionId, endTime, formattedPrice));
            
            mailSender.send(message);
            logger.info("Winner notification sent to {}", winner.getEmail());
        } catch (Exception e) {
            logger.error("Error sending winner notification: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Send notification when an auction has ended
     * @param auction The auction
     */
    public void sendAuctionEndedNotification(PhienDauGia auction) {
        try {
            if (auction.getNguoiTao() == null || auction.getNguoiTao().getEmail() == null) {
                logger.warn("Cannot send auction ended notification: creator email is null");
                return;
            }
            
            String productName = auction.getTaiSan() != null ? auction.getTaiSan().getTenTaiSan() : "N/A";
            String auctionId = auction.getId();
            String endTime = auction.getThoiGianBatDau().plusSeconds(auction.getThoiLuong()).format(DATE_FORMATTER);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(auction.getNguoiTao().getEmail());
            message.setSubject("[" + auctionName + "] Phiên đấu giá của bạn đã kết thúc");
            message.setText(buildAuctionEndedEmailBody(productName, auctionId, endTime));
            
            mailSender.send(message);
            logger.info("Auction ended notification sent to {}", auction.getNguoiTao().getEmail());
        } catch (Exception e) {
            logger.error("Error sending auction ended notification: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Send payment request notification to the winner
     * @param auction The auction
     * @param winner The winner
     * @param amount The amount to pay
     */
    public void sendPaymentRequestNotification(PhienDauGia auction, NguoiDung winner, Long amount) {
        try {
            if (winner == null || winner.getEmail() == null) {
                logger.warn("Cannot send payment request notification: winner email is null");
                return;
            }
            
            String productName = auction.getTaiSan() != null ? auction.getTaiSan().getTenTaiSan() : "N/A";
            String formattedPrice = String.format("%,d", amount);
            String auctionId = auction.getId();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(winner.getEmail());
            message.setSubject("[" + auctionName + "] Yêu cầu thanh toán cho sản phẩm đã thắng");
            message.setText(buildPaymentRequestEmailBody(productName, formattedPrice, auctionId));
            
            mailSender.send(message);
            logger.info("Payment request notification sent to {}", winner.getEmail());
        } catch (Exception e) {
            logger.error("Error sending payment request notification: {}", e.getMessage(), e);
        }
    }
    
    // ==================== Email Body Templates ====================
    
    private String buildAuctionStartedEmailBody(String productName, String auctionId, String startTime) {
        return "Xin chào,\n\n" +
               "Phiên đấu giá của bạn vừa bắt đầu!\n\n" +
               "Sản phẩm: " + productName + "\n" +
               "ID Phiên: " + auctionId + "\n" +
               "Thời gian bắt đầu: " + startTime + "\n\n" +
               "Người mua sẽ có thể đặt giá cho sản phẩm của bạn ngay bây giờ.\n\n" +
               "Cảm ơn bạn đã sử dụng " + auctionName + "!\n\n" +
               "Trân trọng,\n" +
               auctionName + " Team";
    }
    
    private String buildWinnerNotificationEmailBody(String productName, String auctionId, String endTime, String price) {
        return "Xin chào,\n\n" +
               "Chúc mừng! Bạn đã thắng cuộc đấu giá!\n\n" +
               "Sản phẩm: " + productName + "\n" +
               "ID Phiên: " + auctionId + "\n" +
               "Giá thắng: " + price + " VND\n" +
               "Thời gian kết thúc: " + endTime + "\n\n" +
               "Vui lòng tiến hành thanh toán trong vòng 3 ngày.\n\n" +
               "Cảm ơn bạn đã sử dụng " + auctionName + "!\n\n" +
               "Trân trọng,\n" +
               auctionName + " Team";
    }
    
    private String buildAuctionEndedEmailBody(String productName, String auctionId, String endTime) {
        return "Xin chào,\n\n" +
               "Phiên đấu giá của bạn đã kết thúc.\n\n" +
               "Sản phẩm: " + productName + "\n" +
               "ID Phiên: " + auctionId + "\n" +
               "Thời gian kết thúc: " + endTime + "\n\n" +
               "Hãy kiểm tra chi tiết kết quả trên hệ thống của chúng tôi.\n\n" +
               "Cảm ơn bạn đã sử dụng " + auctionName + "!\n\n" +
               "Trân trọng,\n" +
               auctionName + " Team";
    }
    
    private String buildPaymentRequestEmailBody(String productName, String price, String auctionId) {
        return "Xin chào,\n\n" +
               "Bạn cần thanh toán cho sản phẩm mà bạn đã thắng cuộc.\n\n" +
               "Sản phẩm: " + productName + "\n" +
               "Số tiền cần thanh toán: " + price + " VND\n" +
               "ID Phiên: " + auctionId + "\n\n" +
               "Vui lòng thanh toán trong vòng 3 ngày để hoàn thành giao dịch.\n\n" +
               "Cảm ơn bạn đã sử dụng " + auctionName + "!\n\n" +
               "Trân trọng,\n" +
               auctionName + " Team";
    }
}
