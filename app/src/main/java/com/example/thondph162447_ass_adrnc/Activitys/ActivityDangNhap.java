package com.example.thondph162447_ass_adrnc.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thondph162447_ass_adrnc.DAO.TaiKhoanDAO;
import com.example.thondph162447_ass_adrnc.MainActivity;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityDangNhap extends AppCompatActivity {
    Button btnDangNhap;
    TextView txt_QuenMK;
    TextInputEditText edt_tenDangNhap, edt_matKhau,edt_email_dangNhap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txt_QuenMK = findViewById(R.id.txt_quenMK);
        edt_tenDangNhap = findViewById(R.id.edt_TenDangNhap);
        edt_matKhau = findViewById(R.id.edt_MatKhau);
        edt_email_dangNhap = findViewById(R.id.edt_email_dangNhap);

        txt_QuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDangNhap.this, ActivityQuenMK.class));
            }
        });



        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edt_tenDangNhap.getText().toString();
                String mk = edt_matKhau.getText().toString();
                String email = edt_email_dangNhap.getText().toString();

                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(getApplicationContext());
                boolean loggedIn = taiKhoanDAO.checkLogin(tenDN, mk, email);

                if (loggedIn) {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityDangNhap.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Sai thông tin, Kiểm tra lại đi !!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}