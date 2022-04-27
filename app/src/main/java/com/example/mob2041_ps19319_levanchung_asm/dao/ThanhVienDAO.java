package com.example.mob2041_ps19319_levanchung_asm.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<ThanhVien> getData(String sql, String...selectionArgs){
        db = dbHelper.getWritableDatabase();
        List<ThanhVien> arr = new ArrayList<>();
        Cursor cs = db.rawQuery(sql, selectionArgs);
        while (cs.moveToNext()){
            int _maTV = cs.getInt(0);
            String _hoTen = cs.getString(1);
            String _namSinh = cs.getString(2);
            String _hinh = cs.getString(3);
            ThanhVien tv = new ThanhVien(_maTV,_hoTen,_namSinh, _hinh);
            arr.add(tv);
        }
        return arr;
    }

    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM THANHVIEN";

        return getData(sql);
    }

    public ThanhVien getID(int maTV){
        String sql = "SELECT * FROM THANHVIEN WHERE maTV=?";
        List<ThanhVien> list = getData(sql, String.valueOf(maTV));
        return list.get(0);
    }

    public boolean insert(ThanhVien tv) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen",tv.getHoTen());
        values.put("namSinh",tv.getNamSinh());
        values.put("hinh",tv.getHinh());

        long row = db.insert("THANHVIEN",null,values);
        return (row>0);
    }

    public boolean update(ThanhVien tv) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen",tv.getHoTen());
        values.put("namSinh",tv.getNamSinh());
        values.put("hinh",tv.getHinh());
        int row = db.update("THANHVIEN",values,"maTV=?",new String[]{String.valueOf(tv.getMaTV())});
        return (row>0);
    }

    public boolean delete(int maTV) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("THANHVIEN","maTV=?",new String[]{String.valueOf(maTV)});
        return (row>0);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
