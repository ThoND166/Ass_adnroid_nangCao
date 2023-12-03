package com.example.thondph162447_ass_adrnc.DTO;

public class TaiKhoanDTO {
    int id;
    String tenDN,email,matkhau;

    public TaiKhoanDTO(int id, String tenDN, String email, String matkhau) {
        this.id = id;
        this.tenDN = tenDN;
        this.email = email;
        this.matkhau = matkhau;
    }

    public TaiKhoanDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
