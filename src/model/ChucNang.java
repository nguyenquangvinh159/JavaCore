package model;

public class ChucNang {
    private String ma;
    private String ten;  

    public ChucNang(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public String getMa() { return ma; }
    public String getTen() { return ten; }

    @Override
    public String toString() {
        return ten;
    }
}

