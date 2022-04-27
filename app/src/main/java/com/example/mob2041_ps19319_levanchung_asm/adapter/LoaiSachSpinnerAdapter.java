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

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {

    Context context;
    ArrayList<LoaiSach> arr;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(Context context, ArrayList<LoaiSach> arr) {
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
        LoaiSach ls = arr.get(position);
        if(ls != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaLoaiSach.setText(ls.getMaLoai()+". ");
            tvTenLoaiSach.setText(ls.getTenLoai());
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
        LoaiSach ls = arr.get(position);
        if(ls != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaLoaiSach.setText(ls.getMaLoai()+". ");
            tvTenLoaiSach.setText(ls.getTenLoai());
        }
        return v;
    }
}
