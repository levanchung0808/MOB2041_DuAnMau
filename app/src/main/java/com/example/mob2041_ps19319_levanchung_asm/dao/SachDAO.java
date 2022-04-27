package com.example.mob2041_ps19319_levanchung_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public SachDAO(Context context){
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<Sach> getData(String sql, String...selectionArgs){
        db = dbHelper.getWritableDatabase();
        List<Sach> arr = new ArrayList<>();
        Cursor cs = db.rawQuery(sql, selectionArgs);
        while (cs.moveToNext()){
            int _maSach = cs.getInt(0);
            String _tenSach = cs.getString(1);
            String _hinh = cs.getString(2);
            int _maLoai = cs.getInt(3);
            int _giaThue = cs.getInt(4);
            Sach sach = new Sach(_maSach,_tenSach,_hinh,_maLoai,_giaThue);
            arr.add(sach);
        }
        return arr;
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM SACH";

        return getData(sql);
    }

    public Sach getID(int maSach){
        String sql = "SELECT * FROM SACH WHERE maSach=?";
        List<Sach> list = getData(sql, String.valueOf(maSach));
        return list.get(0);
    }

    public boolean insert(Sach sach) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("hinh",sach.getHinh());
        values.put("maLoai",sach.getMaLoai());
        values.put("giaThue",sach.getGiaThue());
        long row = db.insert("SACH",null,values);
        return (row>0);
    }

    public boolean update(Sach sach) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("hinh",sach.getHinh());
        values.put("maLoai",sach.getMaLoai());
        values.put("giaThue",sach.getGiaThue());
        int row = db.update("SACH",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        return (row>0);
    }

    public boolean delete(int maSach) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("SACH","maSach=?",new String[]{String.valueOf(maSach)});
        return (row>0);
    }

    public boolean getMaLoaiSach(String id) {
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM SACH WHERE maLoai=?";
        Cursor cs = db.rawQuery(sql,new String[]{id});
        return (cs.getCount() > 0);
    }
}
