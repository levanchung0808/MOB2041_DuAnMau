package com.example.mob2041_ps19319_levanchung_asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "PS19319_DB_PNLIB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //LOAISACH
        String sql = "CREATE TABLE LOAISACH (maLoai integer PRIMARY KEY AUTOINCREMENT, tenLoai text)";
        db.execSQL(sql);
        sql = "INSERT INTO LOAISACH VALUES (0, 'Van hoc')";
        db.execSQL(sql);
        sql = "INSERT INTO LOAISACH VALUES (1, 'Cong nghe thong tin')";
        db.execSQL(sql);
        sql = "INSERT INTO LOAISACH VALUES (2, 'Lich Su')";
        db.execSQL(sql);

        //SACH
        String sql1 = "CREATE TABLE SACH (maSach integer PRIMARY KEY AUTOINCREMENT, tenSach text, hinh text, maLoai integer references LOAISACH(maLoai), giaThue integer)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO SACH('maSach','tenSach','hinh','maLoai','giaThue') VALUES (0,'Cuon theo chieu gio','cuontheochieugio',0,15000)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO SACH('maSach','tenSach','hinh','maLoai','giaThue') VALUES (1,'HTML & CSS tutorial','html_css',1,15000)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO SACH('maSach','tenSach','hinh','maLoai','giaThue') VALUES (2,'Dac nhan tam','dacnhantam',0,15000)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO SACH('maSach','tenSach','hinh','maLoai','giaThue') VALUES (3,'React Native','react_native',1,15000)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO SACH('maSach','tenSach','hinh','maLoai','giaThue') VALUES (4,'Lịch sử 9','lichsu9',2,15000)";
        db.execSQL(sql1);

        //THUTHU
        String sql2 = "CREATE TABLE THUTHU (maTT text PRIMARY KEY, hoTen text, matKhau text)";
        db.execSQL(sql2);
        sql2 = "INSERT INTO THUTHU VALUES ('TT1','Thủ thư 1','123456789')";
        db.execSQL(sql2);
        sql2 = "INSERT INTO THUTHU VALUES ('TT2','Thủ thư 2','987654321')";
        db.execSQL(sql2);
        sql2 = "INSERT INTO THUTHU VALUES ('TT3','Thủ thư 3','456789')";
        db.execSQL(sql2);
        sql2 = "INSERT INTO THUTHU VALUES ('admin','ADMINISTATOR','admin')";
        db.execSQL(sql2);

        //THANHVIEN
        String sql3 = "CREATE TABLE THANHVIEN (maTV integer PRIMARY KEY AUTOINCREMENT, hoTen text, namSinh text, hinh text)";
        db.execSQL(sql3);
        sql3 = "INSERT INTO THANHVIEN('maTV','hoTen','namSinh') VALUES (0,'Thành viên 1','2001')";
        db.execSQL(sql3);
        sql3 = "INSERT INTO THANHVIEN('maTV','hoTen','namSinh') VALUES (1,'Thành viên 2','2002')";
        db.execSQL(sql3);
        sql3 = "INSERT INTO THANHVIEN('maTV','hoTen','namSinh') VALUES (2,'Thành viên 3','2003')";
        db.execSQL(sql3);

        //PHIEUMUON
        String sql4 = "CREATE TABLE PHIEUMUON (maPM integer PRIMARY KEY AUTOINCREMENT, " +
                "maTT text references THUTHU(maTT), " +
                "maTV integer references THANHVIEN(maTV), " +
                "maSach integer references SACH(maSach), "+
                "ngay date, traSach integer, tienThue integer)";
        db.execSQL(sql4);
        sql4 = "INSERT INTO PHIEUMUON('maPM','maTT','maTV','maSach','Ngay','traSach','tienThue') VALUES (0,'TT1',0,1,'2022-01-01',0,15000)";
        db.execSQL(sql4);
        sql4 = "INSERT INTO PHIEUMUON('maPM','maTT','maTV','maSach','Ngay','traSach','tienThue') VALUES (1,'TT2',1,0,'2022-02-02',1,15000)";
        db.execSQL(sql4);
        sql4 = "INSERT INTO PHIEUMUON('maPM','maTT','maTV','maSach','Ngay','traSach','tienThue') VALUES (2,'TT3',2,2,'2022-03-03',1,15000)";
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS LOAISACH";
        db.execSQL(sql);
        String sql1 = "DROP TABLE IF EXISTS SACH";
        db.execSQL(sql1);
        String sql2 = "DROP TABLE IF EXISTS THUTHU";
        db.execSQL(sql2);
        String sql3 = "DROP TABLE IF EXISTS THANHVIEN";
        db.execSQL(sql3);
        String sql4 = "DROP TABLE IF EXISTS PHIEUMUON";
        db.execSQL(sql4);

        onCreate(db);
    }
}
