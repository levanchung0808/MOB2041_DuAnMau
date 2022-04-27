package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.LoaiSachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.PhieuMuonDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.fragment.LoaiSachFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.SachFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {

    Context context;
    SachFragment fragment;
    ArrayList<Sach> arr;
    TextView tvMaSach, tvTenSach, tvGiaThue, tvTenLoaiSach_Sach;
    ImageView ivAvatar_Sach, ivDelete_Sach;

    Bitmap bitmap;

    PhieuMuonDAO phieuMuonDAO;

    public SachAdapter(Context context, SachFragment fragment, ArrayList<Sach> arr) {
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
            v = inflater.inflate(R.layout.item_sach,null);
        }
        Sach sach = arr.get(position);
        if(sach != null){
            ivAvatar_Sach = v.findViewById(R.id.ivAvatar_Sach);
            tvMaSach = v.findViewById(R.id.tvMaSach);
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvGiaThue = v.findViewById(R.id.tvGiaThue);
            tvTenLoaiSach_Sach = v.findViewById(R.id.tvTenLoaiSach_Sach);
            ivDelete_Sach = v.findViewById(R.id.ivDelete_Sach);

            String urlImage = sach.getHinh();
            bitmap = fragment.StringToBitMap(urlImage);

            Intent intent = new Intent();
            if(bitmap != null){
                Uri uri = fragment.getImage(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                ivAvatar_Sach.setImageURI(uri);
            }else{
                intent.setType("text/plain");
            }

            tvMaSach.setText("Mã sách: "+sach.getMaSach());
            tvTenSach.setText("Loại: "+sach.getTenSach());
            tvGiaThue.setText("Giá thuê: "+sach.getGiaThue());

            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(sach.getMaLoai());
            tvTenLoaiSach_Sach.setText("Loại sách: " + loaiSach.getTenLoai());
        }

        ivDelete_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuonDAO = new PhieuMuonDAO(context);
                if(phieuMuonDAO.getIdMaSach(sach.getMaSach()+"")){
                    Toast.makeText(context, "Không thể xoá mã sách " + sach.getMaSach() + " vì còn tồn tại ở phiếu mượn", Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment.xoa(sach.getMaSach());
            }
        });
        return v;
    }
}
