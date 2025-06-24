package model;

import java.time.LocalDate;

public class YeuCauMuon {
    private int maYeuCau;
    private String tenDangNhap;
    private String maTB;
    private int soLuongMuon;
    private LocalDate ngayMuon;
    private LocalDate ngayTraDuKien;
    private String mucDichSuDung;
    private String trangThai;
    private String tenTB;
    private LocalDate ngayTraThucTe;
    private String nguoiDuyet;
    private String lyDoTuChoi;


    public YeuCauMuon(String tenDangNhap, String maTB, int soLuongMuon,
                      LocalDate ngayMuon, LocalDate ngayTraDuKien, String mucDichSuDung) {
        this.tenDangNhap = tenDangNhap;
        this.maTB = maTB;
        this.soLuongMuon = soLuongMuon;
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.mucDichSuDung = mucDichSuDung;
        this.trangThai = "Chờ duyệt";
    }
    public String getTenTB() { return tenTB; }
    public void setTenTB(String tenTB) { this.tenTB = tenTB; }

    public LocalDate getNgayTraThucTe() { return ngayTraThucTe; }
    public void setNgayTraThucTe(LocalDate ngayTraThucTe) { this.ngayTraThucTe = ngayTraThucTe; }

    public String getNguoiDuyet() { return nguoiDuyet; }
    public void setNguoiDuyet(String nguoiDuyet) { this.nguoiDuyet = nguoiDuyet; }

    public String getLyDoTuChoi() { return lyDoTuChoi; }
    public void setLyDoTuChoi(String lyDoTuChoi) { this.lyDoTuChoi = lyDoTuChoi; }

    public int getMaYeuCau() { return maYeuCau; }

	public void setMaYeuCau(int maYeuCau) { this.maYeuCau = maYeuCau; }

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public void setMaTB(String maTB) {
		this.maTB = maTB;
	}

	public void setSoLuongMuon(int soLuongMuon) {
		this.soLuongMuon = soLuongMuon;
	}

	public void setNgayMuon(LocalDate ngayMuon) {
		this.ngayMuon = ngayMuon;
	}

	public void setNgayTraDuKien(LocalDate ngayTraDuKien) {
		this.ngayTraDuKien = ngayTraDuKien;
	}

	public void setMucDichSuDung(String mucDichSuDung) {
		this.mucDichSuDung = mucDichSuDung;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	// Getters
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMaTB() { return maTB; }
    public int getSoLuongMuon() { return soLuongMuon; }
    public LocalDate getNgayMuon() { return ngayMuon; }
    public LocalDate getNgayTraDuKien() { return ngayTraDuKien; }
    public String getMucDichSuDung() { return mucDichSuDung; }
    public String getTrangThai() { return trangThai; }
}
