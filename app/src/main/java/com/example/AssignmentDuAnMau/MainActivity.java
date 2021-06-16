package com.example.AssignmentDuAnMau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String striUserName, striPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("QUẢN LÝ SÁCH");
        setContentView(R.layout.activity_main);

    }



    public void viewNguoiDung(View v) {
        Intent intent = new Intent(MainActivity.this, ListNguoiDungActivity.class);
        startActivity(intent);
    }

    public void viewTheLoai(View v) {
        Intent intent = new Intent(MainActivity.this, ListTheLoaiActivity.class);
        startActivity(intent);
    }

    public void viewListBookActivity(View v) {
        Intent intent = new Intent(MainActivity.this, ListBookActivity.class);
        startActivity(intent);
    }

    public void ViewListHoaDonActivity(View v) {
        Intent intent = new Intent(MainActivity.this, ListHoaDonActivity.class);
        startActivity(intent);
    }

    public void ViewTopSach(View v) {
        Intent intent = new Intent(MainActivity.this, LuotSachBanChayActivity.class);
        startActivity(intent);
    }

    public void ViewThongKeActivity(View v) {
        Intent intent = new Intent(MainActivity.this, ThongKeDoanhThuActivity.class);
        startActivity(intent);
    }
}

