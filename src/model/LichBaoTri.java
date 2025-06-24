package model;

import java.time.LocalDate;

public class LichBaoTri {
    private int maBaoTri;
    private String maTB, loaiBaoTri, noiDung, nguoiThucHien, trangThai;
    private LocalDate ngayDuKien, ngayThucTe;
    private double chiPhi;

    // Getters & Setters
    public int getMaBaoTri() { return maBaoTri; }
    public void setMaBaoTri(int maBaoTri) { this.maBaoTri = maBaoTri; }

    public String getMaTB() { return maTB; }
    public void setMaTB(String maTB) { this.maTB = maTB; }

    public String getLoaiBaoTri() { return loaiBaoTri; }
    public void setLoaiBaoTri(String loaiBaoTri) { this.loaiBaoTri = loaiBaoTri; }

    public LocalDate getNgayDuKien() { return ngayDuKien; }
    public void setNgayDuKien(LocalDate ngayDuKien) { this.ngayDuKien = ngayDuKien; }

    public LocalDate getNgayThucTe() { return ngayThucTe; }
    public void setNgayThucTe(LocalDate ngayThucTe) { this.ngayThucTe = ngayThucTe; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getNguoiThucHien() { return nguoiThucHien; }
    public void setNguoiThucHien(String nguoiThucHien) { this.nguoiThucHien = nguoiThucHien; }

    public double getChiPhi() { return chiPhi; }
    public void setChiPhi(double chiPhi) { this.chiPhi = chiPhi; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
