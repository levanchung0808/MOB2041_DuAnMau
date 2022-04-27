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
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {

    Context context;
    ArrayList<ThanhVien> arr;
    TextView tvMaTV, tvTenTV;

    public ThanhVienSpinnerAdapter(Context context, ArrayList<ThanhVien> arr) {
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
        ThanhVien tv = arr.get(position);
        if(tv != null){
            tvMaTV = v.findViewById(R.id.tvMaLoaiSach);
            tvTenTV = v.findViewById(R.id.tvTenLoaiSach);
            tvMaTV.setText(tv.getMaTV()+". ");
            tvTenTV.setText(tv.getHoTen());
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
        ThanhVien tv = arr.get(position);
        if(tv != null){
            tvMaTV = v.findViewById(R.id.tvMaLoaiSach);
            tvTenTV = v.findViewById(R.id.tvTenLoaiSach);
            tvMaTV.setText(tv.getMaTV()+". ");
            tvTenTV.setText(tv.getHoTen());
        }
        return v;
    }
}
