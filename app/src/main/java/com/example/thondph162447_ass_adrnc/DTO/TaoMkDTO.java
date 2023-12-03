package com.example.thondph162447_ass_adrnc.DTO;

public class TaoMkDTO {
    int id;
    String mkNew;

    public TaoMkDTO(int id, String mkNew) {
        this.id = id;
        this.mkNew = mkNew;
    }
    public TaoMkDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMkNew() {
        return mkNew;
    }

    public void setMkNew(String mkNew) {
        this.mkNew = mkNew;
    }
}
