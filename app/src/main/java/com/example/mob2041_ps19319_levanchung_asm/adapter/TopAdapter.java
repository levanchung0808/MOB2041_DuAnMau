package com.example.mob2041_ps19319_levanchung_asm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
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
import com.example.mob2041_ps19319_levanchung_asm.fragment.SachFragment;
import com.example.mob2041_ps19319_levanchung_asm.fragment.TopFragment;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.Top;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {

    Context context;
    ArrayList<Top> arr;
    TextView tvSach, tvSL;
    ImageView ivAvatar_Sach_Top;
    Bitmap bitmap;

    public TopAdapter(Context context, ArrayList<Top> arr) {
        super(context, 0, arr);
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_top,null);
        }
        Top top = arr.get(position);
        if(top != null){
            tvSach = v.findViewById(R.id.tvTenSach_Top);
            tvSL = v.findViewById(R.id.tvSoLuong);
            ivAvatar_Sach_Top = v.findViewById(R.id.ivAvatar_Sach_Top);

            String urlImage = top.getHinh();
            bitmap = StringToBitMap(urlImage);

            Intent intent = new Intent();
            if(bitmap != null){
                Uri uri = getImage(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                ivAvatar_Sach_Top.setImageURI(uri);
            }else{
                intent.setType("text/plain");
            }

            tvSach.setText("Sách: "+top.getTenSach());
            tvSL.setText("Số lượng: "+top.getSoLuong());
        }

        return v;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Uri getImage(Bitmap bitmap) {
        File imagefolder = new File(getContext().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(getContext(), "com.chunglv.asm_androidnc", file);
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}
