package com.example.thondph162447_ass_adrnc.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.thondph162447_ass_adrnc.R;

public class ActivityManChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_chao);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivityManChao.this, Check_TK.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}