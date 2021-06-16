package com.example.AssignmentDuAnMau;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.AssignmentDuAnMau.adapter.NguoiDungAdapter;
import com.example.AssignmentDuAnMau.dao.NguoiDungDAO;
import com.example.AssignmentDuAnMau.dao.QuanTriDAO;
import com.example.AssignmentDuAnMau.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ListNguoiDungActivity extends AppCompatActivity {
    public static List<NguoiDung> dsNguoiDung = new ArrayList<>();
    RecyclerView lvNguoiDung;
    NguoiDungAdapter adapter;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("NGƯỜI DÙNG");
        setContentView(R.layout.activity_list_nguoi_dung);
        lvNguoiDung = (RecyclerView) findViewById(R.id.lvNguoiDung);

        nguoiDungDAO = new NguoiDungDAO(ListNguoiDungActivity.this);
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter = new NguoiDungAdapter(ListNguoiDungActivity.this, dsNguoiDung);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvNguoiDung.setLayoutManager(linearLayoutManager);
        lvNguoiDung.setAdapter(adapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        dsNguoiDung.clear();
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter.changeDataset(nguoiDungDAO.getAllNguoiDung());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.add:
                intent = new Intent(ListNguoiDungActivity.this,NguoiDungActivity.class);
                startActivity(intent);
                return(true);
            case R.id.changePass:
                 showLoginDialogFragment();
                return(true);
            case R.id.logOut:
                SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                //xoa tinh trang luu tru truoc do
                edit.clear();
                edit.commit();
                intent = new Intent(ListNguoiDungActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoginDialogFragment() {
        DialogFragment newFragment = new LoginDialogFragment();
        newFragment.show(getSupportFragmentManager(), " LoginDialog");
    }
    public static class LoginDialogFragment extends DialogFragment {
        QuanTriDAO qtDao;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            qtDao = new QuanTriDAO(getContext());
            final View layout = inflater.inflate(R.layout.dialog_dmk, null);
            final EditText etOldPwd = layout.findViewById(R.id.username);
            final EditText etNewPwd = layout.findViewById(R.id.password);
            final EditText etReNewPwd = layout.findViewById(R.id.passcheck);

            builder.setView(layout)


                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                            String old = etOldPwd.getText().toString();
                            String nPwd = etNewPwd.getText().toString();
                            String rePwd = etReNewPwd.getText().toString();
                            String ud = LoginActivity.USER.getUserName();
                            if (!qtDao.checkOldPwd(old)){
                                Toast.makeText(getContext(), "Mật Khẩu Cũ Không Đúng !!", Toast.LENGTH_SHORT).show();

                            }else {
                                if (!nPwd.equals(rePwd)){
                                    Toast.makeText(getContext(), "Không Giống Với Mật Khẩu Trên", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (qtDao.updatePwd(new NguoiDung(ud,nPwd))){
                                        Toast.makeText(getContext(), "Cập Nhật Thành Công!", Toast.LENGTH_SHORT).show();
                                        LoginActivity.USER.setPassword(nPwd);
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(getContext(), "Cập Nhật Không Thành Công!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoginDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }
}
