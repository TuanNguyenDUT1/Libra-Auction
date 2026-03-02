package io.github.guennhatking.libra_auction.models;

public class DauGiaLen implements ChienLuocDatGia {
    private long giaKinTamThoi; // Giá kín tạm thời
    private long giaMoiThapNhat; // Giá mở kín tối thiểu
    
    public DauGiaLen(long giaMoiThapNhat) {
        this.giaMoiThapNhat = giaMoiThapNhat;
        this.giaKinTamThoi = 0;
    }
    
    @Override
    public boolean kiemTraHopLe(long giaHienTai, long giaMoi) { 
        // Kiểm tra giá mới có hợp lệ cho đấu giá kín không
        if (giaHienTai == 0) {
            // Lần đặt giá đầu tiên, phải >= giá mở kín tối thiểu
            return giaMoi >= giaMoiThapNhat;
        }
        // Giá mới phải cao hơn giá hiện tại
        return giaMoi > giaHienTai;
    }
    
    // Cập nhật giá kín tạm thời
    public void capNhatGiaKin(long giaMoi) {
        this.giaKinTamThoi = giaMoi;
    }
    
    // Lấy giá kín (không công khai)
    public long layGiaKin() {
        return this.giaKinTamThoi;
    }
    
    // Lấy giá mở kín tối thiểu
    public long layGiaMoiThapNhat() {
        return this.giaMoiThapNhat;
    }
}
