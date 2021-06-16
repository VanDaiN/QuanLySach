package com.example.AssignmentDuAnMau.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AssignmentDuAnMau.LoginActivity;
import com.example.AssignmentDuAnMau.database.DatabaseHelper;
import com.example.AssignmentDuAnMau.model.NguoiDung;

public class QuanTriDAO {
    DatabaseHelper dbHelper;
    public QuanTriDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public boolean checkLogin(NguoiDung user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM NguoiDung WHERE username=? AND password=?",
                new String[]{user.getUserName(),user.getPassword()});

        if(cs.getCount()<=0){
            return false ;
        }
        return true;
    }
    public boolean checkOldPwd(String oldpwd){
        String pwd = LoginActivity.USER.getPassword();
        if (!oldpwd.equals(pwd)){
            return  false;
        }
        return true;
    }



    public boolean updatePwd(NguoiDung user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",user.getPassword());

        int row = db.update("NguoiDung",values,"username=?",new String[]{user.getUserName()});
        return row>0;
    }
}
