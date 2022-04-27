package com.example.mob2041_ps19319_levanchung_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilTenDangNhap,tilMatKhau;
    TextInputEditText edtTenDangNhap,edtMatKhau;
    TextView txtDangKyNgay;
    CheckBox chkLuuMatKhau;
    Button btnDangNhap;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Đăng nhập thông tin");
        setContentView(R.layout.activity_login);
        tilTenDangNhap = findViewById(R.id.tilTenDangNhap);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkLuuMatKhau = findViewById(R.id.chkLuuMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtDangKyNgay = findViewById(R.id.txtDangKyNgay);

        thuThuDAO = new ThuThuDAO(this);

        //lưu mk SharedPreferences
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edtTenDangNhap.setText(preferences.getString("USERNAME",""));
        edtMatKhau.setText(preferences.getString("PASSWORD",""));
        chkLuuMatKhau.setChecked(preferences.getBoolean("REMEMBER",false));

        edtMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtMatKhau.setSelectAllOnFocus(true);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        txtDangKyNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void checkLogin(){
        String _user = edtTenDangNhap.getText().toString();
        String _pass = edtMatKhau.getText().toString();
        if(_user.isEmpty() || _pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không được rỗng!", Toast.LENGTH_SHORT).show();
        }else if(thuThuDAO.checkLogin(_user,_pass)>0){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            rememberUser(_user,_pass,chkLuuMatKhau.isChecked());

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user",_user);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

    public void rememberUser(String user, String pass, boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!status){
            editor.clear();
        }else{
            editor.putString("USERNAME",user);
            editor.putString("PASSWORD",pass);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }

}