package model;

import java.time.LocalDate;

public class BaoCaoSuCo {
    private int maBC;
    private String tenDangNhap;
    private String maTB;
    private String tenTB; // dùng khi join để hiển thị
    private String maPhong;
    private String moTa;
    private String mucDoNghiemTrong;
    private LocalDate ngayBaoCao;
    private String trangThai;
    private String nguoiXuLy;
    private LocalDate ngayXuLy;
    private String ketQuaXuLy;

    // Constructor dùng khi gửi báo cáo
    public BaoCaoSuCo(String tenDangNhap, String maTB, String maPhong, String moTa, String mucDoNghiemTrong) {
        this.tenDangNhap = tenDangNhap;
        this.maTB = maTB;
        this.maPhong = maPhong;
        this.moTa = moTa;
        this.mucDoNghiemTrong = mucDoNghiemTrong;
    }

    public BaoCaoSuCo() {} // constructor rỗng để dùng với DAO

    // Getter & Setter

    public int getMaBC() {
        return maBC;
    }

    public void setMaBC(int maBC) {
        this.maBC = maBC;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMucDoNghiemTrong() {
        return mucDoNghiemTrong;
    }

    public void setMucDoNghiemTrong(String mucDoNghiemTrong) {
        this.mucDoNghiemTrong = mucDoNghiemTrong;
    }

    public LocalDate getNgayBaoCao() {
        return ngayBaoCao;
    }

    public void setNgayBaoCao(LocalDate ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNguoiXuLy() {
        return nguoiXuLy;
    }

    public void setNguoiXuLy(String nguoiXuLy) {
        this.nguoiXuLy = nguoiXuLy;
    }

    public LocalDate getNgayXuLy() {
        return ngayXuLy;
    }

    public void setNgayXuLy(LocalDate ngayXuLy) {
        this.ngayXuLy = ngayXuLy;
    }

    public String getKetQuaXuLy() {
        return ketQuaXuLy;
    }

    public void setKetQuaXuLy(String ketQuaXuLy) {
        this.ketQuaXuLy = ketQuaXuLy;
    }
}
