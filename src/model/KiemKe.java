package model;

import java.time.LocalDate;

public class KiemKe {
    private int maKiemKe;
    private String tenDangNhap;
    private String maPhong;
    private LocalDate ngayKiemKe;
    private String loaiKiemKe;
    private String trangThai;
    private String ghiChu;

    public KiemKe(String tenDangNhap, String maPhong, LocalDate ngayKiemKe, String loaiKiemKe, String trangThai, String ghiChu) {
        this.tenDangNhap = tenDangNhap;
        this.maPhong = maPhong;
        this.ngayKiemKe = ngayKiemKe;
        this.loaiKiemKe = loaiKiemKe;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public KiemKe(int maKiemKe, String tenDangNhap, String maPhong, LocalDate ngayKiemKe, String loaiKiemKe, String trangThai, String ghiChu) {
        this(tenDangNhap, maPhong, ngayKiemKe, loaiKiemKe, trangThai, ghiChu);
        this.maKiemKe = maKiemKe;
    }

    // Getters và Setters
    public int getMaKiemKe() { return maKiemKe; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMaPhong() { return maPhong; }
    public LocalDate getNgayKiemKe() { return ngayKiemKe; }
    public String getLoaiKiemKe() { return loaiKiemKe; }
    public String getTrangThai() { return trangThai; }
    public String getGhiChu() { return ghiChu; }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
