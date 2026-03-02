package io.github.guennhatking.libra_auction.models;

public class TaiSan {
    private ThongTinPhienDauGia thongTinPhienDauGia;
    private DanhMuc danhMuc;
    private String tenTaiSan;
    private int soLuong;
    private String moTa;

    public ThongTinPhienDauGia getThongTinPhienDauGia() { return null; }
    public DanhMuc getDanhMuc() { return null; }
    public String getTenTaiSan() { return null; }
    public int getSoLuong() { return soLuong; }
    public String getMoTa() { return moTa; }
    public void themHinhAnh(String url) {}

    //setter
    public void setThongTinPhienDauGia(ThongTinPhienDauGia thongTinPhienDauGia) { this.thongTinPhienDauGia = thongTinPhienDauGia; }
    public void setDanhMuc(DanhMuc danhMuc) { this.danhMuc = danhMuc; }
    public void setTenTaiSan(String tenTaiSan) { this.tenTaiSan = tenTaiSan; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; } 
    public void setMoTa(String moTa) { this.moTa = moTa; }



}
