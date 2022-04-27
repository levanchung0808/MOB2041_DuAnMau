package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class ThemNguoiDungFragment extends Fragment {

    TextInputLayout tilMaTT, tilHoTen, tilMatKhauMoi_ThemNgDung, tilNhapLaiMatKhauMoi_ThemNgDung;
    TextInputEditText edtMaTT, edtHoTen, edtMatKhauMoi_ThemNgDung, edtNhapLaiMatKhauMoi_ThemNgDung;
    Button btnTaoNguoiDung, btnHuyBo;
    ThuThuDAO thuThuDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
        tilMaTT = view.findViewById(R.id.tilMaTT);
        tilHoTen = view.findViewById(R.id.tilHoTen);
        tilMatKhauMoi_ThemNgDung = view.findViewById(R.id.tilMatKhauMoi_ThemNgDung);
        tilNhapLaiMatKhauMoi_ThemNgDung = view.findViewById(R.id.tilNhapLaiMatKhauMoi_ThemNgDung);
        edtMaTT = view.findViewById(R.id.edtMaTT);
        edtHoTen = view.findViewById(R.id.edtHoTen);
        edtMatKhauMoi_ThemNgDung = view.findViewById(R.id.edtMatKhauMoi_ThemNgDung);
        edtNhapLaiMatKhauMoi_ThemNgDung = view.findViewById(R.id.edtNhapLaiMatKhauMoi_ThemNgDung);
        btnTaoNguoiDung = view.findViewById(R.id.btnTaoNguoiDung);
        btnHuyBo = view.findViewById(R.id.btnHuyBo);

        thuThuDAO = new ThuThuDAO(getActivity());

        btnHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMaTT.setText("");
                edtHoTen.setText("");
                edtMatKhauMoi_ThemNgDung.setText("");
                edtNhapLaiMatKhauMoi_ThemNgDung.setText("");
            }
        });

        btnTaoNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilMaTT,edtMaTT) | !validateEditText(tilHoTen,edtHoTen) | !validateEditText(tilMatKhauMoi_ThemNgDung,edtMatKhauMoi_ThemNgDung) | !validateEditText(tilNhapLaiMatKhauMoi_ThemNgDung,edtNhapLaiMatKhauMoi_ThemNgDung)) {
                    return;
                }

                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(edtMaTT.getText().toString());
                thuThu.setHoTen(edtHoTen.getText().toString());
                thuThu.setMatKhau(edtMatKhauMoi_ThemNgDung.getText().toString());

                if(edtMatKhauMoi_ThemNgDung.getText().toString().equals(edtNhapLaiMatKhauMoi_ThemNgDung.getText().toString())){
                    if (thuThuDAO.insert(thuThu)) {
                        Toast.makeText(getActivity(), "Tạo người dùng thành công!!", Toast.LENGTH_SHORT).show();
                        edtMaTT.setText("");
                        edtHoTen.setText("");
                        edtMatKhauMoi_ThemNgDung.setText("");
                        edtNhapLaiMatKhauMoi_ThemNgDung.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Tên tài khoản đã tồn tại!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Mật khẩu mới và nhập lại phải giống nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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
