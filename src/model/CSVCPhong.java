package model;

import java.util.Date;

public class CSVCPhong {
    private String maPhong, maTB, tenTB, tinhTrang;
    private int soLuong;
    private Date ngayLapDat;

    public CSVCPhong(String maPhong, String maTB, String tenTB, int soLuong, Date ngayLapDat, String tinhTrang) {
        this.maPhong = maPhong;
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.soLuong = soLuong;
        this.ngayLapDat = ngayLapDat;
        this.tinhTrang = tinhTrang;
    }

    public String getMaPhong() { return maPhong; }
    public String getMaTB() { return maTB; }
    public String getTenTB() { return tenTB; }
    public int getSoLuong() { return soLuong; }
    public Date getNgayLapDat() { return ngayLapDat; }
    public String getTinhTrang() { return tinhTrang; }
}