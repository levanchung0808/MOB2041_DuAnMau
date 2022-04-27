package com.example.mob2041_ps19319_levanchung_asm.fragment;

import static java.time.MonthDay.now;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.adapter.LoaiSachSpinnerAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.PhieuMuonAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.SachAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.SachSpinnerAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.ThanhVienSpinnerAdapter;
import com.example.mob2041_ps19319_levanchung_asm.adapter.ThuThuSpinnerAdapter;
import com.example.mob2041_ps19319_levanchung_asm.dao.LoaiSachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.PhieuMuonDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.SachDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThanhVienDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.LoaiSach;
import com.example.mob2041_ps19319_levanchung_asm.model.PhieuMuon;
import com.example.mob2041_ps19319_levanchung_asm.model.Sach;
import com.example.mob2041_ps19319_levanchung_asm.model.ThanhVien;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PhieuMuonFragment extends Fragment {

    ListView lvPM;
    ArrayList<PhieuMuon> arr;
    FloatingActionButton fab;
    Dialog dialog;
    TextInputLayout tilMaPM, tilNgay, tilTienThue, tilMaTT_PM;
    TextInputEditText edtMaPM, edtNgay, edtTienThue, edtMaTT_PM;
    ImageView ivDate;
    Spinner spTenThanhVien, spTenSach, spThuThu;
    CheckBox chkTraSach;
    Button btnTaoPM, btnHuyPM;
    PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon pm;

    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> arrThanhVien;
    ThanhVienDAO thanhVienDAO;

    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> arrSach;
    SachDAO sachDAO;

    ThuThuSpinnerAdapter thuThuSpinnerAdapter;
    ArrayList<ThuThu> arrThuThu;
    ThuThuDAO thuThuDAO;

    int maSach, maTV;
    String maTT;
    int positionTV, positionSach, positionTT;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lvPM = v.findViewById(R.id.lvPM);
        fab = v.findViewById(R.id.fabAdd_PM);
        dao = new PhieuMuonDAO(getActivity());
        refreshList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //type: 0 - INSERT | 1 - UPDATE
                openDialog(getActivity(), 0);
            }
        });

        lvPM.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pm = arr.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });

        return v;
    }

    public void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_phieu_muon);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        tilMaPM = dialog.findViewById(R.id.tilMaPM);
        edtMaPM = dialog.findViewById(R.id.edtMaPM);
        edtTienThue = dialog.findViewById(R.id.edtTienThue);
        tilTienThue = dialog.findViewById(R.id.tilTienThue);
        ivDate = dialog.findViewById(R.id.ivDate);
        tilNgay = dialog.findViewById(R.id.tilNgay);
        edtNgay = dialog.findViewById(R.id.edtNgay);
        spTenThanhVien = dialog.findViewById(R.id.spTenThanhVien);
        spTenSach = dialog.findViewById(R.id.spTenSach);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnTaoPM = dialog.findViewById(R.id.btnTaoPM);
        btnHuyPM = dialog.findViewById(R.id.btnHuyPM);
        spThuThu = dialog.findViewById(R.id.spThuThu);

        edtMaPM.setVisibility(View.GONE);
        tilMaPM.setVisibility(View.GONE);

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String _month = null;
                        if (monthOfYear < 10) {
                            _month = "0" + (monthOfYear + 1);
                        }
                        edtNgay.setText(year + "-" + _month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //spinner thành viên
        thanhVienDAO = new ThanhVienDAO(context);
        arrThanhVien = new ArrayList<>();
        arrThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, arrThanhVien);
        spTenThanhVien.setAdapter(thanhVienSpinnerAdapter);
        spTenThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = arrThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner sách
        sachDAO = new SachDAO(context);
        arrSach = new ArrayList<>();
        arrSach = (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context, arrSach);
        spTenSach.setAdapter(sachSpinnerAdapter);
        spTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = arrSach.get(position).getMaSach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner Thủ thư
        thuThuDAO = new ThuThuDAO(context);
        arrThuThu = new ArrayList<>();
        arrThuThu = (ArrayList<ThuThu>) thuThuDAO.getAll();
        thuThuSpinnerAdapter = new ThuThuSpinnerAdapter(context, arrThuThu);
        spThuThu.setAdapter(thuThuSpinnerAdapter);
        spThuThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTT = arrThuThu.get(position).getMaTT();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (spTenThanhVien.getCount() == 0 || spTenSach.getCount() == 0) {
            btnTaoPM.setEnabled(false);
        }
        edtMaPM.setEnabled(false);
        edtNgay.setEnabled(false);
        //ktra type insert(0) hay update(1)
        if (type != 0) {
            btnTaoPM.setText("Cập nhật");
            edtMaPM.setText(String.valueOf(pm.getMaPM()));
            edtNgay.setText(sdf.format(pm.getNgay()));
            edtTienThue.setText(pm.getTienThue() + "");
            if (pm.getTraSach() == 0) {
                chkTraSach.setChecked(false);
            } else {
                chkTraSach.setChecked(true);
            }
            //sp tên thành viên
            for (int i = 0; i < arrThanhVien.size(); i++) {
                if (pm.getMaTV() == (arrThanhVien.get(i).getMaTV())) {
                    positionTV = i;
                }
            }
            spTenThanhVien.setSelection(positionTV);

            //sp tên sách
            for (int i = 0; i < arrSach.size(); i++) {
                if (pm.getMaSach() == (arrSach.get(i).getMaSach())) {
                    positionSach = i;
                }
            }
            spTenSach.setSelection(positionSach);

            //sp thủ thư
            for (int i = 0; i < arrThuThu.size(); i++) {
                if (pm.getMaTT().equals(arrThuThu.get(i).getMaTT())) {
                    positionTT = i;
                }
            }
            spThuThu.setSelection(positionTT);
        }

        btnHuyPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoPM.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilNgay, edtNgay) | !validateEditText(tilTienThue, edtTienThue)) {
                    return;
                }

                if(!edtTienThue.getText().toString().matches("[0-9]+")){
                    tilTienThue.setError("Vui lòng chỉ nhập số");
                    return;
                }else{
                    tilTienThue.setError("");
                    tilTienThue.setErrorEnabled(false);
                }

                pm = new PhieuMuon();
                pm.setMaTT(maTT);
                pm.setMaTV(maTV);
                pm.setMaSach(maSach);
                try {
                    pm.setNgay(sdf.parse(edtNgay.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (chkTraSach.isChecked()) {
                    pm.setTraSach(1);
                } else {
                    pm.setTraSach(0);
                }
                pm.setTienThue(Integer.parseInt(edtTienThue.getText().toString()));
                if (type == 0) {
                    //0 INSERT
                    if (dao.insert(pm)) {
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //try catch để bắt lỗi nhập vào bắt buộc phải là số
                    pm.setMaPM(Integer.parseInt(edtMaPM.getText().toString()));
                    if (dao.update(pm)) {
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
        arr = (ArrayList<PhieuMuon>) dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this, arr);
        lvPM.setAdapter(adapter);
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
