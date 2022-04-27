package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThongKeDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoanhThuFragment extends Fragment {

    TextInputLayout tilTuNgay, tilDenNgay;
    TextInputEditText edtTuNgay, edtDenNgay;
    ImageView ivTuNgay, ivDenNgay;
    TextView tvDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu,container,false);
        tilTuNgay = view.findViewById(R.id.tilTuNgay);
        tilDenNgay = view.findViewById(R.id.tilDenNgay);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        ivTuNgay = view.findViewById(R.id.ivTuNgay);
        ivDenNgay = view.findViewById(R.id.ivDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);

        edtTuNgay.setEnabled(false);
        edtDenNgay.setEnabled(false);

        ivTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateTuNgay,mYear,mMonth,mDay);
                d.show();
            }
        });

        ivDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateDenNgay,mYear,mMonth,mDay);
                d.show();
            }
        });

        edtTuNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                thongKeDoanhThu();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtDenNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                thongKeDoanhThu();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void thongKeDoanhThu(){
        String tuNgay = edtTuNgay.getText().toString();
        String denNgay = edtDenNgay.getText().toString();
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        tvDoanhThu.setText(thongKeDAO.getDoanhThu(tuNgay,denNgay)+" đồng");
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtTuNgay.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtDenNgay.setText(sdf.format(c.getTime()));
        }
    };

}
