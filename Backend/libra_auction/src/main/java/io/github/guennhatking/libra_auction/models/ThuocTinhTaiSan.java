
package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;

@Entity
public class ThuocTinhTaiSan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tai_san_id")
    private TaiSan taiSan;

    private String tenThuocTinh;
    private String giaTri;

    public ThuocTinhTaiSan() {
        // Constructor mặc định
    }

    public String getId() { return id; }
    public TaiSan getTaiSan() { return taiSan; }
    public String getTenThuocTinh() { return tenThuocTinh; }
    public String getGiaTri() { return giaTri; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTaiSan(TaiSan taiSan) { this.taiSan = taiSan; }
    public void setTenThuocTinh(String tenThuocTinh) { this.tenThuocTinh = tenThuocTinh; }
    public void setGiaTri(String giaTri) { this.giaTri = giaTri;}
}