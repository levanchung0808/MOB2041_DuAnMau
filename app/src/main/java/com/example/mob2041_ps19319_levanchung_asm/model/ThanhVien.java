package com.example.mob2041_ps19319_levanchung_asm.model;

import android.graphics.Bitmap;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private String namSinh;
    private String hinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTV, String hoTen, String namSinh, String hinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.hinh = hinh;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
