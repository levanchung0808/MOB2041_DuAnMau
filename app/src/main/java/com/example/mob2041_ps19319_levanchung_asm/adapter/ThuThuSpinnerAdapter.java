package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;

import java.util.ArrayList;

public class ThuThuSpinnerAdapter extends ArrayAdapter<ThuThu> {

    Context context;
    ArrayList<ThuThu> arr;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public ThuThuSpinnerAdapter(Context context, ArrayList<ThuThu> arr) {
        super(context, 0,arr);
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_loai_sach,null);
        }
        ThuThu tt = arr.get(position);
        if(tt != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaLoaiSach.setText(tt.getMaTT()+". ");
            tvTenLoaiSach.setText(tt.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_loai_sach,null);
        }
        ThuThu tt = arr.get(position);
        if(tt != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaLoaiSach.setText(tt.getMaTT()+". ");
            tvTenLoaiSach.setText(tt.getHoTen());
        }
        return v;
    }
}
