package io.github.guennhatking.libra_auction.models;

import java.time.LocalDateTime;

public class ThongTinThamGiaDauGia {
    private NguoiDung nguoiThamGia;
    private PhienDauGia phienDauGia;
    private LocalDateTime thoiGianDangKy;

    //getter setter
    public NguoiDung getNguoiThamGia() { return nguoiThamGia; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public LocalDateTime getThoiGianDangKy() { return thoiGianDangKy; }
    public void setNguoiThamGia(NguoiDung nguoiThamGia) { this.nguoiThamGia = nguoiThamGia; }
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    public void setThoiGianDangKy(LocalDateTime thoiGianDangKy) { this.thoiGianDangKy = thoiGianDangKy; }
    
}
