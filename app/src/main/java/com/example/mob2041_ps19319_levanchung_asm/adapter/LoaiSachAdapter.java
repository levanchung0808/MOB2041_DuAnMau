package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.PhieuMuonDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.fragment.LoaiSachFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.ThanhVienFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {

    Context context;
    LoaiSachFragment fragment;
    ArrayList<LoaiSach> arr;
    TextView tvMaLoaiSach, tvTenLoaiSach;
    ImageView ivAvatar_LoaiSach, ivDelete_LoaiSach;

    SachDAO sachDAO;

    public LoaiSachAdapter(Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> arr) {
        super(context, 0,arr);
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
            v = inflater.inflate(R.layout.item_loai_sach,null);
        }
        LoaiSach ls = arr.get(position);
        if(ls != null){
            ivAvatar_LoaiSach = v.findViewById(R.id.ivAvatar_LoaiSach);
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
            ivDelete_LoaiSach = v.findViewById(R.id.ivDelete_LoaiSach);

            Drawable drawable = context.getResources().getDrawable(R.drawable.sachcntt);
            ivAvatar_LoaiSach.setImageDrawable(drawable);
            tvMaLoaiSach.setText("Mã: "+ls.getMaLoai());
            tvTenLoaiSach.setText("Loại: "+ls.getTenLoai());
        }

        ivDelete_LoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDAO = new SachDAO(context);
                if(sachDAO.getMaLoaiSach(ls.getMaLoai()+"")){
                    Toast.makeText(context, "Không thể xoá mã loại sách " + ls.getMaLoai() + " vì còn tồn tại ở sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment.xoa(ls.getMaLoai());
            }
        });
        return v;
    }
}
