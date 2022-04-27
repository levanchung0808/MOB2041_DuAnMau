package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.adapter.ThanhVienAdapter;
import com.example.mob2041_ps19319_levanchung_asm.dao.PhieuMuonDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThanhVienDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.PhieuMuon;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ThanhVienFragment extends Fragment {

    ListView lvThanhVien;
    ArrayList<ThanhVien> arr;
    FloatingActionButton fab;
    Dialog dialog;
    TextInputLayout tilMaThanhVien, tilTenThanhVien, tilNamSinh;
    TextInputEditText edtMaThanhVien, edtTenThanhVien, edtNamSinh;
    Button btnTaoThanhVien, btnHuyThanhVien;
    ImageView ivImage;
    Bitmap bitmap;
    ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien tv;

    PhieuMuonDAO phieuMuonDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thanh_vien,container,false);
        lvThanhVien = v.findViewById(R.id.lvThanhVien);
        fab = v.findViewById(R.id.fabAdd_ThanhVien);
        dao = new ThanhVienDAO(getActivity());
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        refreshList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //type: 0 - INSERT | 1 - UPDATE
                openDialog(getActivity(),0);
            }
        });

        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tv = arr.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });

        return v;
    }

    public void openDialog(Context context, int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_thanh_vien);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        tilMaThanhVien = dialog.findViewById(R.id.tilMaThanhVien);
        tilTenThanhVien = dialog.findViewById(R.id.tilTenThanhVien);
        tilNamSinh = dialog.findViewById(R.id.tilNamSinh);
        edtTenThanhVien = dialog.findViewById(R.id.edtTenThanhVien);
        edtMaThanhVien = dialog.findViewById(R.id.edtMaThanhVien);
        edtNamSinh = dialog.findViewById(R.id.edtNamSinh);
        btnTaoThanhVien = dialog.findViewById(R.id.btnTaoThanhVien);
        btnHuyThanhVien = dialog.findViewById(R.id.btnHuyThanhVien);
        ivImage = dialog.findViewById(R.id.ivImage);

        tilMaThanhVien.setVisibility(View.GONE);
        edtMaThanhVien.setVisibility(View.GONE);
        edtMaThanhVien.setEnabled(false);

        //ktra type insert(0) hay delete(1)
        if(type != 0){

            if(tv.getHinh()==null){
                ivImage.setImageResource(R.drawable.avatar);
            }

            String urlImage = tv.getHinh();
            bitmap = StringToBitMap(urlImage);

            Intent intent = new Intent();
            if(bitmap != null){
                Uri uri = getImage(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                ivImage.setImageURI(uri);
            }else{
                intent.setType("text/plain");
            }

            btnTaoThanhVien.setText("Cập nhật thành viên");
            edtMaThanhVien.setText(String.valueOf(tv.getMaTV()));
            edtTenThanhVien.setText(tv.getHoTen());
            edtNamSinh.setText(tv.getNamSinh());
        }

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                chooseImage.launch(i);
            }
        });

        btnHuyThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilTenThanhVien,edtTenThanhVien) | !validateEditText(tilNamSinh,edtNamSinh)) {
                    return;
                }

                if(!edtNamSinh.getText().toString().matches("\\d{4}")){
                    tilNamSinh.setError("Năm sinh có 4 số");
                    return;
                }else{
                    tilNamSinh.setError("");
                    tilNamSinh.setErrorEnabled(false);
                }

                String urlImage = "";
                if(bitmap != null){
                    urlImage = BitMapToString(bitmap);
                }

                tv = new ThanhVien();
                tv.setHinh(urlImage);
                tv.setHoTen(edtTenThanhVien.getText().toString());
                tv.setNamSinh(edtNamSinh.getText().toString());

                if (type == 0) {
                    //0 INSERT
                    if (dao.insert(tv)) {
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Ma!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //try catch để bắt lỗi nhập vào bắt buộc phải là số
                    tv.setMaTV(Integer.parseInt(edtMaThanhVien.getText().toString()));
                    if (dao.update(tv)) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
                refreshList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void xoa(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xoá");
        builder.setMessage("Bạn có chắc chắn muốn xoá?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                refreshList();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    void refreshList(){
        arr = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(),this,arr);
        lvThanhVien.setAdapter(adapter);
    }

    private boolean validateEditText(TextInputLayout til, TextInputEditText edt) {
        String _str = edt.getText().toString().trim();
        if (_str.isEmpty()) {
            til.setError("Vui lòng không bỏ trống");
            return false;
        } else {
            til.setError("");
            til.setErrorEnabled(false);
            return true;
        }
    }

    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            ivImage.setImageURI(selectedImageUri);

                            BitmapDrawable bitmapDrawable = (BitmapDrawable) ivImage.getDrawable();
                            bitmap = bitmapDrawable.getBitmap();
                        }
                    }
                }
            });

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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
