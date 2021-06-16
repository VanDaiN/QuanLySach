package com.example.AssignmentDuAnMau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.AssignmentDuAnMau.dao.QuanTriDAO;
import com.example.AssignmentDuAnMau.model.NguoiDung;


public class LoginActivity extends AppCompatActivity {

    EditText etName, etPass;
    Button btnLog, btnDk;
    CheckBox ckRem;
    public static NguoiDung USER = null;
    QuanTriDAO qtdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        qtdao = new QuanTriDAO(LoginActivity.this);
        etName = findViewById(R.id.edUserName);
        etPass = findViewById(R.id.edPassword);
        btnLog = findViewById(R.id.btnLogin);
        ckRem = findViewById(R.id.chkRememberPass);
        loadData();
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = etName.getText().toString();
                String pwd = etPass.getText().toString();
                boolean check = ckRem.isChecked();
                NguoiDung user = new NguoiDung(Name,pwd);
                if(qtdao.checkLogin(user)){
                    luuTT(Name,pwd,check);
                    USER = user;
                    Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }else {
                    checkName(user);
                    checkPass(user);
                    Toast.makeText(LoginActivity.this, "Đăng Nhập Không Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean checkName(NguoiDung user){
        String nameCheck = etName.getText().toString().trim();

        if(nameCheck.isEmpty()){
            etName.setError("Không để trống Username");
            return false;
        }

        else if(nameCheck != user.getUserName()){
            Toast.makeText(this, "Sai Tài khoản", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            etName.setError(null);
            return true;
        }
    }
    private boolean checkPass(NguoiDung user){
        String passCheck = etPass.getText().toString().trim();

        if(passCheck.isEmpty()){
            etPass.setError("Không để trống Password");
            return false;
        }

        else if(passCheck != user.getPassword()){
            Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            etPass.setError(null);
            return true;
        }
    }

    private void luuTT(String un, String pwd,boolean check){
        SharedPreferences preferences = getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(check){
            editor.putString("Username",un);
            editor.putString("Password",pwd);
            editor.putBoolean("check", check);

        }
        else{
            editor.clear();
        }
        editor.commit();
    }

    private void loadData(){
        SharedPreferences pref = getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check",false);
        if(check){
            etName.setText(pref.getString("Username",""));
            etPass.setText(pref.getString("Password",""));
            ckRem.setChecked(check);

        }
    }
}
