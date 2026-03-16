package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ThuocTinhChuanHoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)

    private String tenThuocTinh;
    private String giaTri;

    public ThuocTinhChuanHoa() {
        // Constructor mặc định
    }

    public String getId() { return id; }
    public String getTenThuocTinh() { return tenThuocTinh; }
    public String getGiaTri() { return giaTri; }

    //getter setter
    public void setId(String id) { this.id = id; }
    public void setTenThuocTinh(String tenThuocTinh) { this.tenThuocTinh = tenThuocTinh; }
    public void setGiaTri(String giaTri) { this.giaTri = giaTri;}
    
}