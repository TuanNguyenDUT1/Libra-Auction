package io.github.guennhatking.libra_auction.models;

import java.time.LocalDateTime;

public class BanGhiPhienDauGia {
    private String id; // string(10)
    private PhienDauGia phienDauGia;
    private LocalDateTime thoiGian;
    private long mucGia;
    private NguoiDung nguoiDatGia;

    public String getId() { return id; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public long getMucGia() { return mucGia; }
    public NguoiDung getNguoiDatGia() { return nguoiDatGia; }
    public LocalDateTime getThoiGian() { return thoiGian; }

    //id
    public void setId(String id) { this.id = id;}
    //phienDauGia
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    //mucGia
    public void setMucGia(long mucGia) { this.mucGia = mucGia; }
    //nguoiDatGia   
    public void setNguoiDatGia(NguoiDung nguoiDatGia) { this.nguoiDatGia = nguoiDatGia; }
    //thoiGian
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
}
