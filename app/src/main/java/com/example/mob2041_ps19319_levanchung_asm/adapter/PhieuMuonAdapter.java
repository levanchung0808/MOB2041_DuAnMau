package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.LoaiSachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThanhVienDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.fragment.PhieuMuonFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.SachFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.PhieuMuon;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {

    Context context;
    PhieuMuonFragment fragment;
    ArrayList<PhieuMuon> arr;
    TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvTenThuThu, tvTrangThai, tvNgayThue;
    ImageView ivDelete_PM;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    ThuThuDAO thuThuDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonAdapter(Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> arr) {
        super(context, 0, arr);
        this.context = context;
        this.arr = arr;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_phieu_muon,null);
        }
        PhieuMuon pm = arr.get(position);
        if(pm != null){
            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(pm.getMaSach());
            thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien tv = thanhVienDAO.getID(pm.getMaTV());
            thuThuDAO = new ThuThuDAO(context);
            ThuThu thuThu = thuThuDAO.getID(pm.getMaTT());

            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTienThue = v.findViewById(R.id.tvTienThue);
            tvTenThuThu = v.findViewById(R.id.tvTenThuThu);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvNgayThue = v.findViewById(R.id.tvNgayThue);
            ivDelete_PM = v.findViewById(R.id.ivDelete_PM);

            tvMaPM.setText("Mã PM: "+pm.getMaPM());
            tvTenTV.setText("Tên TV: "+tv.getHoTen());
            tvTenSach.setText("Sách: "+sach.getTenSach());
            tvTienThue.setText("Tiền thuê: "+pm.getTienThue());
            tvTenThuThu.setText("Tên TT: "+thuThu.getHoTen());
            //0: chưa trả sách | 1: đã trả sách
            if(pm.getTraSach() == 0){
                tvTrangThai.setTextColor(Color.RED);
                tvTrangThai.setText("Chưa trả sách");
            }else{
                tvTrangThai.setTextColor(Color.BLUE);
                tvTrangThai.setText("Đã trả sách");

            }
            tvNgayThue.setText("Ngày thuê: "+sdf.format(pm.getNgay()));
        }

        ivDelete_PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(pm.getMaPM());
            }
        });
        return v;
    }
}
