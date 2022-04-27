package com.example.mob2041_ps19319_levanchung_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO{

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<LoaiSach> getData(String sql, String...selectionArgs){
        db = dbHelper.getWritableDatabase();
        List<LoaiSach> arr = new ArrayList<>();
        Cursor cs = db.rawQuery(sql, selectionArgs);
        while (cs.moveToNext()){
            int _maLoai = cs.getInt(0);
            String _tenLoai = cs.getString(1);
            LoaiSach ls = new LoaiSach(_maLoai,_tenLoai);
            arr.add(ls);
        }
        return arr;
    }

    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LOAISACH";

        return getData(sql);
    }

    public LoaiSach getID(int maLoai){
        String sql = "SELECT * FROM LOAISACH WHERE maLoai=?";
        List<LoaiSach> list = getData(sql, String.valueOf(maLoai));
        return list.get(0);
    }

    public boolean insert(LoaiSach ls) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai",ls.getTenLoai());
        long row = db.insert("LOAISACH",null,values);
        return (row>0);
    }

    public boolean update(LoaiSach ls) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai",ls.getTenLoai());
        int row = db.update("LOAISACH",values,"maLoai=?",new String[]{String.valueOf(ls.getMaLoai())});
        return (row>0);
    }

    public boolean delete(int maLoai) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("LOAISACH","maLoai=?",new String[]{String.valueOf(maLoai)});
        return (row>0);
    }




//    public List<LoaiSach> viewAll(Context context) {
//        List<LoaiSach> arr = new ArrayList<>();
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cs = db.rawQuery("SELECT * FROM LOAISACH",null);
//        cs.moveToFirst();
//        while (!cs.isAfterLast()){
//            int _maLoai = cs.getInt(0);
//            String _tenLoai = cs.getString(1);
//            LoaiSach ls = new LoaiSach(_maLoai,_tenLoai);
//            arr.add(ls);
//        }
//        cs.close();
//        db.close();
//        return arr;
//    }
//
//    public boolean insert(Context context, String tenLoai) {
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("tenLoai",tenLoai);
//        long row = db.insert("LOAISACH",null,values);
//        return (row>0);
//    }
//
//    public boolean update(Context context, LoaiSach ls) {
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("tenLoai",ls.getTenLoai());
//        int row = db.update("LOAISACH",values,"maLoai=?",new String[]{String.valueOf(ls.getMaLoai())});
//        return (row>0);
//    }
//
//    public boolean delete(Context context, int maLoai) {
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int row = db.delete("LOAISACH","maLoai=?",new String[]{String.valueOf(maLoai)});
//        return (row>0);
//    }
}
