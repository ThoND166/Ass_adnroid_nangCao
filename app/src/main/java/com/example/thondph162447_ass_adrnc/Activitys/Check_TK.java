package com.example.thondph162447_ass_adrnc.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.thondph162447_ass_adrnc.Databases.DbHelper;
import com.example.thondph162447_ass_adrnc.R;

public class Check_TK extends AppCompatActivity {
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_tk);
        dbHelper = new DbHelper(this);

//        if (!dbHelper.coDuLieuTrongCSDL()) {
//            dbHelper.themDuLieuMau();
//        }

        if (dbHelper.coDuLieuTrongCSDL()) {
            startActivity(new Intent(this, ActivityDangNhap.class));
            finish();
        } else {
            startActivity(new Intent(this, ActivityDangKy.class));
            finish();
        }
    }
}