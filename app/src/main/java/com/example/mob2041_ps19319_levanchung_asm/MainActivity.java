package com.example.mob2041_ps19319_levanchung_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.database.DbHelper;
import com.example.mob2041_ps19319_levanchung_asm.fragment.DoanhThuFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.DoiMatKhauFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.LoaiSachFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.PhieuMuonFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.SachFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.ThanhVienFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.ThemNguoiDungFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.TopFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    ThuThuDAO thuThuDAO;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        drawer = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        setTitle("ASM PS19319");
        FragmentManager manager = getSupportFragmentManager();
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        manager.beginTransaction().replace(R.id.frameLayout, phieuMuonFragment).commit();

        NavigationView nv = findViewById(R.id.navigationView);
        //show user in header
        mHeaderView = nv.getHeaderView(0);
        tvWelcome = mHeaderView.findViewById(R.id.tvWelcome);
        Intent i = getIntent();
        String _maTT = i.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getID(_maTT);
        Log.i("THUTHU", thuThu.getHoTen());
        String _hoten = thuThu.getHoTen();
        tvWelcome.setText("Chào " + _hoten + " !");

        //admin có quyền add user
        if (_maTT.equalsIgnoreCase("admin")) {
            nv.getMenu().findItem(R.id.ThemNguoiDung).setVisible(true);
        }
        //Navigation view
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.QL_PhieuMuon:
                        setTitle("Quản lý phiếu mượn");
                        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, phieuMuonFragment).commit();
                        break;
                    case R.id.QL_LoaiSach:
                        setTitle("Quản lý loại sách");
                        LoaiSachFragment loaiSachFragment = new LoaiSachFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, loaiSachFragment).commit();
                        break;
                    case R.id.QL_Sach:
                        setTitle("Quản lý sách");
                        SachFragment sachFragment = new SachFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, sachFragment).commit();
                        break;
                    case R.id.QL_ThanhVien:
                        setTitle("Quản lý thành viên");
                        ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, thanhVienFragment).commit();
                        break;
                    case R.id.Top10SachMuonNhieuNhat:
                        setTitle("Top 10 sách mượn nhiều nhất");
                        TopFragment topFragment = new TopFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, topFragment).commit();
                        break;
                    case R.id.DoanhThu:
                        setTitle("Doanh thu");
                        DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, doanhThuFragment).commit();
                        break;
                    case R.id.ThemNguoiDung:
                        setTitle("Thêm người dùng");
                        ThemNguoiDungFragment themNguoiDungFragment = new ThemNguoiDungFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, themNguoiDungFragment).commit();
                        break;
                    case R.id.DoiMatKhau:
                        setTitle("Đổi mật khẩu");
                        DoiMatKhauFragment doiMatKhauFragment1 = new DoiMatKhauFragment();
                        manager.beginTransaction().replace(R.id.frameLayout, doiMatKhauFragment1).commit();
                        break;
                    case R.id.DangXuat:
                        setTitle("Đăng xuất");
                        onBackPressed();
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn muốn đăng xuất?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("huỷ bỏ", null)
                .show();
    }
}