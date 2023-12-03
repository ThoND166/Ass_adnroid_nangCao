package com.example.thondph162447_ass_adrnc.DTO;

import java.util.Date;

public class CongViecDTO {
    private int id;
    private String name;
    private String content;
    private String status;
    private Date start;
    private Date ends;



    public CongViecDTO(String name, String content, String status, Date start, Date ends) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.status = status;
        this.start = start;
        this.ends = ends;
    }


    public CongViecDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }
}
