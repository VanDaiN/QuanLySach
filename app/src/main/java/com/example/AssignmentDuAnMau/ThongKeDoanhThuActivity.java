package com.example.AssignmentDuAnMau;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AssignmentDuAnMau.dao.HoaDonChiTietDAO;


public class ThongKeDoanhThuActivity extends AppCompatActivity {
    TextView tvNgay, tvThang, tvNam;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("DOANH THU");
        //setContentView(R.layout.activity_thong_ke_doanh_thu);
        setContentView(R.layout.activity_thong_ke_doanh_thu);
        tvNgay = (TextView) findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView) findViewById(R.id.tvThongKeThang);
        tvNam = (TextView) findViewById(R.id.tvThongKeNam);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
        tvNgay.setText("Today:   "+hoaDonChiTietDAO.getDoanhThuTheoNgay());
        tvThang.setText("Month: "+hoaDonChiTietDAO.getDoanhThuTheoThang());
        tvNam.setText("Year:   "+hoaDonChiTietDAO.getDoanhThuTheoNam());
    }

}
