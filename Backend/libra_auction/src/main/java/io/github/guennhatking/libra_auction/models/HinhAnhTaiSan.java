package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HinhAnhTaiSan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tai_san_id")
    private TaiSan taiSan;

    private int thuTuHienThi;
    private String hinhAnh; // url

    protected HinhAnhTaiSan() {
        // Constructor mặc định cho JPA
    }

    public TaiSan getTaiSan() { return null; }
    public int getThuTuHienThi() { return 0; }
    public String getHinhAnh() { return null; }

    //setter
    public void setTaiSan(TaiSan taiSan) { this.taiSan = taiSan; }
    public void setThuTuHienThi(int thuTuHienThi) { this.thuTuHienThi = thuTuHienThi; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
