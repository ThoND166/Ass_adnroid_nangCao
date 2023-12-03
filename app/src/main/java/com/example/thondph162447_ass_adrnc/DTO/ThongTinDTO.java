package com.example.thondph162447_ass_adrnc.DTO;

public class ThongTinDTO {
    int id;
    String  masv, ten, lop, mon;

    public ThongTinDTO() {
    }

    public ThongTinDTO(int id, String masv, String ten, String lop, String mon) {
        this.id = id;
        this.masv = masv;
        this.ten = ten;
        this.lop = lop;
        this.mon = mon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }
}
