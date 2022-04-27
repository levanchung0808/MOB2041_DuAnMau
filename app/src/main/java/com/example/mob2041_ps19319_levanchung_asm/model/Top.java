package com.example.mob2041_ps19319_levanchung_asm.model;

public class Top {
    private String tenSach;
    private int soLuong;
    private String hinh;

    public Top(String tenSach, int soLuong, String hinh) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.hinh = hinh;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Top() {
    }
}
