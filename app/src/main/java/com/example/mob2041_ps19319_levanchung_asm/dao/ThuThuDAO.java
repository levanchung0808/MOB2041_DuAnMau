package com.example.mob2041_ps19319_levanchung_asm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO{

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public ThuThuDAO(Context context){
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<ThuThu> getData(String sql, String...selectionArgs){
        db = dbHelper.getWritableDatabase();
        List<ThuThu> arr = new ArrayList<>();
        Cursor cs = db.rawQuery(sql, selectionArgs);
        while (cs.moveToNext()){
            String _maTT = cs.getString(0);
            String _hoTen = cs.getString(1);
            String _matKhau = cs.getString(2);
            ThuThu tt = new ThuThu(_maTT,_hoTen,_matKhau);
            arr.add(tt);
        }
        return arr;
    }

    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM THUTHU";

        return getData(sql);
    }

    public ThuThu getID(String maTT){
        String sql = "SELECT * FROM THUTHU WHERE maTT=?";
        List<ThuThu> list = getData(sql, maTT);
        return list.get(0);
    }

    public int checkLogin(String maTT, String matKhau){
        String sql = "SELECT * FROM THUTHU WHERE maTT=? AND matKhau=?";
        List<ThuThu> arr = getData(sql, maTT, matKhau);
        if(arr.size()==0)
            return -1;
        return 1;
    }

//    public List<ThuThu> viewAll() {
//        List<ThuThu> arr = new ArrayList<>();
//        db = dbHelper.getReadableDatabase();
//        Cursor cs = db.rawQuery("SELECT * FROM THUTHU",null);
//        cs.moveToFirst();
//        while (!cs.isAfterLast()){
//            String _maTT = cs.getString(0);
//            String _hoTen = cs.getString(1);
//            String _matKhau = cs.getString(2);
//            ThuThu tt = new ThuThu(_maTT,_hoTen,_matKhau);
//            arr.add(tt);
//        }
//        cs.close();
//        db.close();
//        return arr;
//    }

    public boolean insert(ThuThu thuThu) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT",thuThu.getMaTT());
        values.put("hoTen",thuThu.getHoTen());
        values.put("matKhau",thuThu.getMatKhau());

        long row = db.insert("THUTHU",null,values);
        return (row>0);
    }

    public boolean update(ThuThu tt) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen",tt.getHoTen());
        values.put("matKhau",tt.getMatKhau());
        int row = db.update("THUTHU",values,"maTT=?",new String[]{tt.getMaTT()});
        return (row>0);
    }

    public boolean delete(String maTT) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("THUTHU","maTT=?",new String[]{maTT});
        return (row>0);
    }
}
