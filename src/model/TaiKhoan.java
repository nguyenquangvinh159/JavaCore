package model;

import java.sql.Timestamp;

public class TaiKhoan {
    private String tenDangNhap, matKhau, vaiTro, hoTen, avatar;
    private String email, soDienThoai;
    private Timestamp ngayTao;
    private int trangThai;

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro, String hoTen, String avatar) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.hoTen = hoTen;
        this.avatar = avatar;
    }

    // Getters
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public String getVaiTro() { return vaiTro; }
    public String getHoTen() { return hoTen; }
    public String getAvatar() { return avatar; }
    public String getEmail() { return email; }
    public String getSoDienThoai() { return soDienThoai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public int getTrangThai() { return trangThai; }

    // Setters
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public void setEmail(String email) { this.email = email; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}
