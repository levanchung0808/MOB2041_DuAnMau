package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.adapter.LoaiSachAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.ThanhVienAdapter;
import com.example.mob2041_ps19319_levanchung_asm.dao.LoaiSachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThanhVienDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {

    ListView lvLoaiSach;
    ArrayList<LoaiSach> arr;
    FloatingActionButton fab;
    Dialog dialog;
    TextInputLayout tilMaLoaiSach, tilTenLoaiSach;
    TextInputEditText edtMaLoaiSach, edtTenLoaiSach;
    Button btnTaoLoaiSach, btnHuyLoaiSach;
    LoaiSachDAO dao;
    LoaiSachAdapter adapter;
    LoaiSach ls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_sach,container,false);
        lvLoaiSach = v.findViewById(R.id.lvLoaiSach);
        fab = v.findViewById(R.id.fabAdd_LoaiSach);
        dao = new LoaiSachDAO(getActivity());
        refreshList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //type: 0 - INSERT | 1 - UPDATE
                openDialog(getActivity(),0);
            }
        });

        lvLoaiSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ls = arr.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });

        return v;
    }

    public void openDialog(Context context, int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_loai_sach);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        tilMaLoaiSach = dialog.findViewById(R.id.tilMaLoaiSach);
        tilTenLoaiSach = dialog.findViewById(R.id.tilTenLoaiSach);
        edtMaLoaiSach = dialog.findViewById(R.id.edtMaLoaiSach);
        edtTenLoaiSach = dialog.findViewById(R.id.edtTenLoaiSach);
        btnTaoLoaiSach = dialog.findViewById(R.id.btnTaoLoaiSach);
        btnHuyLoaiSach = dialog.findViewById(R.id.btnHuyLoaiSach);

        edtMaLoaiSach.setVisibility(View.GONE);
        edtMaLoaiSach.setEnabled(false);
        tilMaLoaiSach.setVisibility(View.GONE);
        //ktra type insert(0) hay delete(1)
        if(type != 0){
            btnTaoLoaiSach.setText("Cập nhật loại sách");
            edtMaLoaiSach.setText(String.valueOf(ls.getMaLoai()));
            edtTenLoaiSach.setText(ls.getTenLoai());
        }

        btnHuyLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilTenLoaiSach,edtTenLoaiSach)) {
                    return;
                }

                ls = new LoaiSach();
                ls.setTenLoai(edtTenLoaiSach.getText().toString());

                if (type == 0) {
                    //0 INSERT
                    if (dao.insert(ls)) {
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //try catch để bắt lỗi nhập vào bắt buộc phải là số
                    ls.setMaLoai(Integer.parseInt(edtMaLoaiSach.getText().toString()));
                    if (dao.update(ls)) {
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

    void refreshList(){
        arr = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(),this,arr);
        lvLoaiSach.setAdapter(adapter);
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

}
