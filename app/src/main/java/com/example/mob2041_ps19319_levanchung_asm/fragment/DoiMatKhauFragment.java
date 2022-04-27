package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DoiMatKhauFragment extends Fragment {

    TextInputLayout tilMatKhauCu,tilMatKhauMoi,tilNhapLaiMatKhauMoi;
    TextInputEditText edtMatKhauCu,edtMatKhauMoi,edtNhapLaiMatKhauMoi;
    Button btnDoiMatKhau, btnHuyBo;
    ThuThuDAO thuThuDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau,container,false);
        tilMatKhauCu = view.findViewById(R.id.tilMatKhauCu);
        tilMatKhauMoi = view.findViewById(R.id.tilMatKhauMoi);
        tilNhapLaiMatKhauMoi = view.findViewById(R.id.tilNhapLaiMatKhauMoi);
        edtMatKhauCu = view.findViewById(R.id.edtMatKhauCu);
        edtMatKhauMoi = view.findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = view.findViewById(R.id.edtNhapLaiMatKhauMoi);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnHuyBo = view.findViewById(R.id.btnHuyBo);

        thuThuDAO = new ThuThuDAO(getActivity());

        btnHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMatKhauCu.setText("");
                edtMatKhauMoi.setText("");
                edtNhapLaiMatKhauMoi.setText("");
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilMatKhauCu,edtMatKhauCu) | !validateEditText(tilMatKhauMoi,edtMatKhauMoi) | !validateEditText(tilNhapLaiMatKhauMoi,edtNhapLaiMatKhauMoi)) {
                    return;
                }

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String _user = sharedPreferences.getString("USERNAME","");

                if(edtMatKhauMoi.getText().toString().equals(edtNhapLaiMatKhauMoi.getText().toString())){
                    ThuThu thuThu = thuThuDAO.getID(_user);
                    if(edtMatKhauCu.getText().toString().equals(thuThu.getMatKhau())){
                        if(!edtMatKhauMoi.getText().toString().equals(thuThu.getMatKhau())){
                            thuThu.setMatKhau(edtMatKhauMoi.getText().toString());
                            if(thuThuDAO.update(thuThu)){
                                Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                edtMatKhauCu.setText("");
                                edtMatKhauMoi.setText("");
                                edtNhapLaiMatKhauMoi.setText("");
                            }else{
                                Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Mật khẩu mới trùng mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Mật khẩu mới và nhập lại phải giống nhau!", Toast.LENGTH_SHORT).show();
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
