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
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {

    Context context;
    ArrayList<Sach> arr;
    TextView tvMaSach, tvTenSach;

    public SachSpinnerAdapter(Context context, ArrayList<Sach> arr) {
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
        Sach sach = arr.get(position);
        if(sach != null){
            tvMaSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaSach.setText(sach.getMaSach()+". ");
            tvTenSach.setText(sach.getTenSach());
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
        Sach sach = arr.get(position);
        if(sach != null){
            tvMaSach = v.findViewById(R.id.tvMaLoaiSach);
            tvTenSach = v.findViewById(R.id.tvTenLoaiSach);
            tvMaSach.setText(sach.getMaSach()+". ");
            tvTenSach.setText(sach.getTenSach());
        }
        return v;
    }
}
