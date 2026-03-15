package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;

@Entity
public class ThongBaoUngDung extends ThongBao {
    protected ThongBaoUngDung() {
        // Constructor mặc định cho JPA
    }
    
    public ThongBaoUngDung(NguoiDung nguoiNhan, String noiDung) {
        super(nguoiNhan, noiDung);
    }

    @Override
    public void guiThongBao() {
        // Logic to send application notification
        System.out.println("Gửi thông báo ứng dụng đến " + nguoiNhan.getHoVaTen() + ": " + noiDung);
    }
    
}
