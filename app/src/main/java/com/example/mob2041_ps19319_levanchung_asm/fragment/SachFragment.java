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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.adapter.LoaiSachAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.LoaiSachSpinnerAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.SachAdapter;
import com.example.mob2041_ps19319_levanchung_asm.dao.LoaiSachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class SachFragment extends Fragment {

    ListView lvSach;
    ArrayList<Sach> arr;
    FloatingActionButton fab;
    Dialog dialog;
    TextInputLayout tilMaSach, tilTenSach, tilGiaThue;
    TextInputEditText edtMaSach, edtTenSach, edtGiaThue;
    Spinner spTenLoaiSach;
    Button btnTaoSach, btnHuySach;
    ImageView ivImage;
    Bitmap bitmap;

    SachDAO dao;
    SachAdapter adapter;
    Sach sach;
    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> arrLoaiSach;
    LoaiSachDAO loaiSachDAO;
    int maLoaiSach, position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        lvSach = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fabAdd_Sach);
        dao = new SachDAO(getActivity());
        refreshList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //type: 0 - INSERT | 1 - UPDATE
                openDialog(getActivity(), 0);
            }
        });

        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sach = arr.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });

        return v;
    }

    public void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_sach);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        tilMaSach = dialog.findViewById(R.id.tilMaSach);
        tilTenSach = dialog.findViewById(R.id.tilTenSach);
        tilGiaThue = dialog.findViewById(R.id.tilGiaThue);
        edtMaSach = dialog.findViewById(R.id.edtMaSach);
        edtTenSach = dialog.findViewById(R.id.edtTenSach);
        edtGiaThue = dialog.findViewById(R.id.edtGiaThue);
        spTenLoaiSach = dialog.findViewById(R.id.spTenLoaiSach);
        btnTaoSach = dialog.findViewById(R.id.btnTaoSach);
        btnHuySach = dialog.findViewById(R.id.btnHuySach);
        ivImage = dialog.findViewById(R.id.ivImage);

        tilMaSach.setVisibility(View.GONE);
        edtMaSach.setVisibility(View.GONE);

        //spinner
        arrLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        arrLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        spinnerAdapter = new LoaiSachSpinnerAdapter(context, arrLoaiSach);
        spTenLoaiSach.setAdapter(spinnerAdapter);

        spTenLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = arrLoaiSach.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (spTenLoaiSach.getCount() == 0) {
            btnTaoSach.setEnabled(false);
        }
        edtMaSach.setEnabled(false);
        //ktra type insert(0) hay delete(1)
        if (type != 0) {

            String urlImage = sach.getHinh();
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

            btnTaoSach.setText("Cập nhật loại sách");
            edtMaSach.setText(String.valueOf(sach.getMaSach()));
            edtTenSach.setText(sach.getTenSach());
            edtGiaThue.setText(sach.getGiaThue() + "");
            for (int i = 0; i < arrLoaiSach.size(); i++) {
                if (sach.getMaLoai() == (arrLoaiSach.get(i).getMaLoai())) {
                    position = i;
                }
            }
            spTenLoaiSach.setSelection(position);
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

        btnHuySach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilTenSach, edtTenSach) | !validateEditText(tilGiaThue, edtGiaThue)) {
                    return;
                }

                String urlImage = "";
                if(bitmap != null){
                    urlImage = BitMapToString(bitmap);
                }

                sach = new Sach();
                sach.setHinh(urlImage);
                sach.setTenSach(edtTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edtGiaThue.getText().toString()));
                sach.setMaLoai(maLoaiSach);
                if (type == 0) {
                    //0 INSERT
                    if (dao.insert(sach)) {
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sach.setMaSach(Integer.parseInt(edtMaSach.getText().toString()));
                    if (dao.update(sach)) {
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

    public void xoa(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xoá?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                refreshList();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    void refreshList() {
        arr = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getActivity(), this, arr);
        lvSach.setAdapter(adapter);
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
