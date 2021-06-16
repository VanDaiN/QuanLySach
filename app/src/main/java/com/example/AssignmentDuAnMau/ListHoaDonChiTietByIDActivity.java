package com.example.AssignmentDuAnMau;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.AssignmentDuAnMau.adapter.CartAdapter;
import com.example.AssignmentDuAnMau.dao.HoaDonChiTietDAO;
import com.example.AssignmentDuAnMau.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class ListHoaDonChiTietByIDActivity extends AppCompatActivity {
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("HOÁ ĐƠN CHI TIẾT");
        setContentView(R.layout.activity_list_hoa_don_chi_tiet_by_id);
        lvCart = (ListView) findViewById(R.id.lvHoaDonChiTiet);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ListHoaDonChiTietByIDActivity.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
        }

        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);

    }
}
