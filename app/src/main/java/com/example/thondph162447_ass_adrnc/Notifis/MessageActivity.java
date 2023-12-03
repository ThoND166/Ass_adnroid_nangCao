package com.example.thondph162447_ass_adrnc.Notifis;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thondph162447_ass_adrnc.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        String dulieu = getIntent().getStringExtra("duLieu");
        Toast.makeText(this, "Du lieu:  " + dulieu, Toast.LENGTH_SHORT).show();
    }
}