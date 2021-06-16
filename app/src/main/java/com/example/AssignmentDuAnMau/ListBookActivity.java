package com.example.AssignmentDuAnMau;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.AssignmentDuAnMau.adapter.BookAdapter;
import com.example.AssignmentDuAnMau.dao.SachDAO;
import com.example.AssignmentDuAnMau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class ListBookActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
   RecyclerView lvBook;
   BookAdapter adapter = null;
   SachDAO sachDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("QUẢN LÝ SÁCH");
        setContentView(R.layout.activity_list_book);
       lvBook = (RecyclerView) findViewById(R.id.lvBook);

       sachDAO = new SachDAO(ListBookActivity.this);
       dsSach = sachDAO.getAllSach();
       adapter = new BookAdapter(this);
        lvBook.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        lvBook.setHasFixedSize(true);
        lvBook.setAdapter(adapter);

        EditText edSeach = (EditText) findViewById(R.id.edSearchBook);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        dsSach.clear();
        dsSach = sachDAO.getAllSach();
        adapter.changeDataset(dsSach);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ListBookActivity.this,ThemSachActivity.class);
                startActivity(intent);
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

}
