package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.PhieuMuonDAO;
import com.example.mob2041_ps19319_levanchung_asm.fragment.ThanhVienFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {

    Context context;
    ThanhVienFragment fragment;
    ArrayList<ThanhVien> arr;
    TextView tvMaThanhVien, tvTenThanhVien, tvNamSinh;
    ImageView ivAvatar_ThanhVien, ivDelete_ThanhVien;
    Bitmap bitmap;

    PhieuMuonDAO phieuMuonDAO;

    public ThanhVienAdapter(Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> arr) {
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
            v = inflater.inflate(R.layout.item_thanh_vien,null);
        }
        ThanhVien tv = arr.get(position);
        if(tv != null){
            ivAvatar_ThanhVien = v.findViewById(R.id.ivAvatar_ThanhVien);
            tvMaThanhVien = v.findViewById(R.id.tvMaThanhVien);
            tvTenThanhVien = v.findViewById(R.id.tvTenThanhVien);
            tvNamSinh = v.findViewById(R.id.tvNamSinh);
            ivDelete_ThanhVien = v.findViewById(R.id.ivDelete_ThanhVien);

            String urlImage = tv.getHinh();
            bitmap = fragment.StringToBitMap(urlImage);

            Intent intent = new Intent();
            if(bitmap != null){
                Uri uri = fragment.getImage(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                ivAvatar_ThanhVien.setImageURI(uri);
            }else{
                intent.setType("text/plain");
            }

            tvMaThanhVien.setText("Mã TV: "+tv.getMaTV());
            tvTenThanhVien.setText(tv.getHoTen());
            tvNamSinh.setText("Năm sinh: "+tv.getNamSinh());
        }

        ivDelete_ThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ktra maTV có tồn tại bên PM hay k
                phieuMuonDAO = new PhieuMuonDAO(context);
                if(phieuMuonDAO.getIdThanhVien(tv.getMaTV()+"")){
                    Toast.makeText(context, "Không thể xoá mã thành viên " + tv.getMaTV() + " vì còn tồn tại ở phiếu mượn", Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment.xoa(tv.getMaTV());
            }
        });
        return v;
    }
}
