package com.example.AssignmentDuAnMau;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.AssignmentDuAnMau.adapter.TheLoaiAdapter;
import com.example.AssignmentDuAnMau.dao.TheLoaiDAO;
import com.example.AssignmentDuAnMau.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class ListTheLoaiActivity extends AppCompatActivity {
    public static List<TheLoai> dsTheLoai = new ArrayList<>();
    RecyclerView lvTheLoai;
    TheLoaiAdapter adapter = null;
    TheLoaiDAO theLoaiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("THỂ LOẠI");
        setContentView(R.layout.activity_list_the_loai);
        lvTheLoai = (RecyclerView) findViewById(R.id.lvTheLoai);
        registerForContextMenu(lvTheLoai);
        theLoaiDAO = new TheLoaiDAO(ListTheLoaiActivity.this);
        dsTheLoai = theLoaiDAO.getAllTheLoai();
        adapter = new TheLoaiAdapter(ListTheLoaiActivity.this, dsTheLoai);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTheLoai.setLayoutManager(linearLayoutManager);
        lvTheLoai.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theloai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ListTheLoaiActivity.this,TheLoaiActivity.class);
                startActivity(intent);
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        dsTheLoai.clear();
        dsTheLoai = theLoaiDAO.getAllTheLoai();
        adapter.changeDataset(dsTheLoai);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Chọn thông tin");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_ctx_edit:
                Intent intent1 = new Intent(ListTheLoaiActivity.this,TheLoaiActivity.class);
                startActivity(intent1);
                return(true);
            case R.id.menu_ctx_del:
                Intent intent2 = new Intent(ListTheLoaiActivity.this,TheLoaiActivity.class);
                startActivity(intent2);
                return(true);
        }
        return super.onContextItemSelected(item);

    }
}
