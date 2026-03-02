package io.github.guennhatking.libra_auction.models;

public class DanhMuc {
    private String id; // string(10)
    private String tenDanhMuc;
    private String hinhAnh; // url

    public String getId() { return id; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public String getHinhAnh() { return hinhAnh; }

    //setter
    public void setId(String id) { this.id = id; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    
}
