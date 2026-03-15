package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;

@Entity
public class ChuSoHuu extends NguoiDung {
    protected ChuSoHuu() {
        // Constructor mặc định cho JPA
    }
    
    public ChuSoHuu(String hoVaTen, String soDienThoai, String CCCD) {
        super(hoVaTen, soDienThoai, CCCD);
    }
    
}
