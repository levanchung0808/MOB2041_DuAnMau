package com.example.mob2041_ps19319_levanchung_asm.model;

public class Sach {
    private int maSach;
    private String tenSach;
    private String hinh;
    private int maLoai;
    private int giaThue;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, String hinh, int maLoai, int giaThue) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.hinh = hinh;
        this.maLoai = maLoai;
        this.giaThue = giaThue;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }
}

