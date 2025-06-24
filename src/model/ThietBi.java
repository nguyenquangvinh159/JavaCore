package model;

import java.util.Date;

public class ThietBi {
    private String maTB, tenTB, loaiTB, tinhTrang, donViTinh, hinhAnh, nhaCungCap, ghiChu;
    private int soLuong;
    private double giaTriTaiSan;
    private Date ngayMua, hanBaoHanh;
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
	public String getLoaiTB() {
		return loaiTB;
	}
	public void setLoaiTB(String loaiTB) {
		this.loaiTB = loaiTB;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getNhaCungCap() {
		return nhaCungCap;
	}
	public void setNhaCungCap(String nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getGiaTriTaiSan() {
		return giaTriTaiSan;
	}
	public void setGiaTriTaiSan(double giaTriTaiSan) {
		this.giaTriTaiSan = giaTriTaiSan;
	}
	public Date getNgayMua() {
		return ngayMua;
	}
	public void setNgayMua(Date ngayMua) {
		this.ngayMua = ngayMua;
	}
	public Date getHanBaoHanh() {
		return hanBaoHanh;
	}
	public void setHanBaoHanh(Date hanBaoHanh) {
		this.hanBaoHanh = hanBaoHanh;
	}
	public ThietBi(String maTB, String tenTB, String loaiTB, String tinhTrang, String donViTinh, String hinhAnh,
			String nhaCungCap, String ghiChu, int soLuong, double giaTriTaiSan, Date ngayMua, Date hanBaoHanh) {
		super();
		this.maTB = maTB;
		this.tenTB = tenTB;
		this.loaiTB = loaiTB;
		this.tinhTrang = tinhTrang;
		this.donViTinh = donViTinh;
		this.hinhAnh = hinhAnh;
		this.nhaCungCap = nhaCungCap;
		this.ghiChu = ghiChu;
		this.soLuong = soLuong;
		this.giaTriTaiSan = giaTriTaiSan;
		this.ngayMua = ngayMua;
		this.hanBaoHanh = hanBaoHanh;
	}
	public ThietBi() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ThietBi [maTB=" + maTB + ", tenTB=" + tenTB + ", loaiTB=" + loaiTB + ", tinhTrang=" + tinhTrang
				+ ", donViTinh=" + donViTinh + ", hinhAnh=" + hinhAnh + ", nhaCungCap=" + nhaCungCap + ", ghiChu="
				+ ghiChu + ", soLuong=" + soLuong + ", giaTriTaiSan=" + giaTriTaiSan + ", ngayMua=" + ngayMua
				+ ", hanBaoHanh=" + hanBaoHanh + "]";
	}
}
