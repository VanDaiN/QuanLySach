package com.example.AssignmentDuAnMau;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AssignmentDuAnMau.dao.TheLoaiDAO;
import com.example.AssignmentDuAnMau.model.TheLoai;


public class TheLoaiActivity extends AppCompatActivity {
    EditText edMaTheLoai, edTenTheLoai, edMoTa, edViTri;
    Button btnAdd, btnCancel, btnShow;
    TheLoaiDAO theLoaiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("THỂ LOẠI");
        setContentView(R.layout.activity_the_loai);
        edMaTheLoai = (EditText) findViewById(R.id.edtMaTheLoai);
        edTenTheLoai = (EditText) findViewById(R.id.edtTenTheLoai);
        edMoTa = (EditText) findViewById(R.id.edtMoTa);
        edViTri = (EditText) findViewById(R.id.edtViTri);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edMaTheLoai.setText(b.getString("MATHELOAI"));
            edTenTheLoai.setText(b.getString("TENTHELOAI"));
            edMoTa.setText(b.getString("MOTA"));
            edViTri.setText(b.getString("VITRI"));
        }
    }

    public void showTheLoai(View view) {
        finish();
    }

    public void addTheLoai(View view) {
        theLoaiDAO = new TheLoaiDAO(TheLoaiActivity.this);

        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                TheLoai theLoai = new TheLoai(edMaTheLoai.getText().toString(), edTenTheLoai.getText().toString(),
                        edMoTa.getText().toString(), Integer.parseInt(edViTri.getText().toString()));
                if (theLoaiDAO.inserTheLoai(theLoai) > 0) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int validation() {
        int check = 1;
        if (edMaTheLoai.getText().length() == 0 || edTenTheLoai.getText().length() == 0
                || edViTri.getText().length() == 0 || edMoTa.getText().length() == 0) {
            check = -1;
        }
        return check;
    }

}
