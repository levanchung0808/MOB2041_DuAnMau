package com.example.mob2041_ps19319_levanchung_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.PhieuMuon;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDAO {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<PhieuMuon> getData(String sql, String... selectionArgs) {
        db = dbHelper.getWritableDatabase();
        List<PhieuMuon> arr = new ArrayList<>();
        Cursor cs = db.rawQuery(sql, selectionArgs);
        while (cs.moveToNext()) {
            int _maPM = cs.getInt(0);
            String _maTT = cs.getString(1);
            int _maTV = cs.getInt(2);
            int _maSach = cs.getInt(3);
            String _ngay = cs.getString(4);
            int _traSach = cs.getInt(5);
            int _tienThue = cs.getInt(6);
            PhieuMuon pm = null;
            try {
                pm = new PhieuMuon(_maPM, _maTT, _maTV, _maSach, sdf.parse(_ngay), _traSach, _tienThue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arr.add(pm);
        }
        return arr;
    }

    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM PHIEUMUON";

        return getData(sql);
    }

    public boolean insert(PhieuMuon pm) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("ngay", sdf.format(pm.getNgay()));
        values.put("traSach", pm.getTraSach());
        values.put("tienThue", pm.getTienThue());
        long row = db.insert("PHIEUMUON", null, values);
        return (row > 0);
    }

    public boolean update(PhieuMuon pm) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("ngay", sdf.format(pm.getNgay()));
        values.put("traSach", pm.getTraSach());
        values.put("tienThue", pm.getTienThue());
        int row = db.update("PHIEUMUON", values, "maPM=?", new String[]{String.valueOf(pm.getMaPM())});
        return (row > 0);
    }

    public boolean delete(int maPM) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("PHIEUMUON", "maPM=?", new String[]{String.valueOf(maPM)});
        return (row > 0);
    }

    //get data theo id
    public boolean getIdThanhVien(String id) {
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM PHIEUMUON WHERE maTV=?";
        Cursor cs = db.rawQuery(sql,new String[]{id});
        return (cs.getCount() > 0);
    }

    public boolean getIdMaSach(String id){
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM PHIEUMUON WHERE maSach=?";
        Cursor cs = db.rawQuery(sql,new String[]{id});
        return (cs.getCount() > 0);
    }

}
