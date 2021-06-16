package com.example.AssignmentDuAnMau;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AssignmentDuAnMau.adapter.BookAdapter;
import com.example.AssignmentDuAnMau.dao.SachDAO;
import com.example.AssignmentDuAnMau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class LuotSachBanChayActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    RecyclerView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;
    EditText edThang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TOP 10 SÁCH BÁN CHẠY");
        setContentView(R.layout.activity_luot_sach_ban_chay);
        lvBook = (RecyclerView) findViewById(R.id.lvBookTop);
        edThang = (EditText) findViewById(R.id.edThang);

    }
    public void VIEW_SACH_TOP_10(View view) {
        if (Integer.parseInt(edThang.getText().toString()) > 13 || Integer.parseInt(edThang.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(), "Không đúng định dạng tháng (1-12)", Toast.LENGTH_SHORT).show();
        } else {
            sachDAO = new SachDAO(LuotSachBanChayActivity.this);
            dsSach = sachDAO.getSachTop10(edThang.getText().toString());
            adapter = new BookAdapter(this);
            adapter.swap(dsSach);
            lvBook.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            lvBook.setHasFixedSize(true);
            lvBook.setAdapter(adapter);
        }    }
}
