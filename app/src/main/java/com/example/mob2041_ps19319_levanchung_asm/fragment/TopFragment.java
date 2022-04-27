package com.example.mob2041_ps19319_levanchung_asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_ps19319_levanchung_asm.R;
import com.example.mob2041_ps19319_levanchung_asm.adapter.TopAdapter;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThongKeDAO;
import com.example.mob2041_ps19319_levanchung_asm.dao.ThuThuDAO;
import com.example.mob2041_ps19319_levanchung_asm.model.ThuThu;
import com.example.mob2041_ps19319_levanchung_asm.model.Top;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    ListView lv;
    ArrayList<Top> arr;
    TopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top,container,false);
        lv = view.findViewById(R.id.lvTop);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        arr = (ArrayList<Top>) thongKeDAO.getTop();
        adapter = new TopAdapter(getActivity(),arr);
        lv.setAdapter(adapter);

        return view;
    }
}
