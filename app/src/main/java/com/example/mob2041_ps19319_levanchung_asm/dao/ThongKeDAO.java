package com.example.mob2041_ps19319_levanchung_asm.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    SQLiteDatabase db;
    Context context;
    DbHelper dbHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDAO(Context context){
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    //thong ke top 10
    public List<Top> getTop(){
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT maSach, count(maSach) as soLuong FROM PHIEUMUON GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cs = db.rawQuery(sql,null);
        while (cs.moveToNext()){
            Top  top = new Top();
            Sach sach = sachDAO.getID(cs.getInt(0));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cs.getString(1)));
            top.setHinh(sach.getHinh());
            list.add(top);
        }
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay){
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(tienThue) as doanhthu FROM PHIEUMUON WHERE ngay between ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cs = db.rawQuery(sql,new String[]{tuNgay,denNgay});
        while(cs.moveToNext()){
            try{
                list.add(Integer.parseInt(cs.getString(0)));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
