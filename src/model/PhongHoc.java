package model;

public class PhongHoc {
    private String maPhong, tenPhong, toaNha, loaiPhong, trangThai;
    private int tang, sucChua;

    public PhongHoc(String maPhong, String tenPhong, String toaNha, int tang, int sucChua, String loaiPhong, String trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.toaNha = toaNha;
        this.tang = tang;
        this.sucChua = sucChua;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
    }

    public String getMaPhong() { return maPhong; }
    public String getTenPhong() { return tenPhong; }
    public String getToaNha() { return toaNha; }
    public int getTang() { return tang; }
    public int getSucChua() { return sucChua; }
    public String getLoaiPhong() { return loaiPhong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}