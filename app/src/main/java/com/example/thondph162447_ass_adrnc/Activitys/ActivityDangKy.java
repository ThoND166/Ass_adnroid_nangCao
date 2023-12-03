package com.example.thondph162447_ass_adrnc.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thondph162447_ass_adrnc.DAO.TaiKhoanDAO;
import com.example.thondph162447_ass_adrnc.DTO.TaiKhoanDTO;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

public class ActivityDangKy extends AppCompatActivity {
    Button btnDangKy;
    TextInputEditText edt_tenDangNhap_DK, edt_matKhau_DK, edt_NhaplaiMk;
    TextInputEditText editText_email;
    TaiKhoanDAO taiKhoanDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        btnDangKy = findViewById(R.id.btnDangKy);;
        edt_tenDangNhap_DK = findViewById(R.id.edt_DK_TenDangNhap);
        edt_matKhau_DK = findViewById(R.id.edt_DK_MatKhau);
        edt_NhaplaiMk = findViewById(R.id.edt_DK_NhapLaiMK);
        editText_email = findViewById(R.id.edt_DK_email);
        taiKhoanDAO = new TaiKhoanDAO(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mk = edt_matKhau_DK.getText().toString();
                String nhaplaiMK = edt_NhaplaiMk.getText().toString();
                String tenDN = edt_tenDangNhap_DK.getText().toString();
                String email = editText_email.getText().toString();

                // Kiểm tra định dạng email
                if(tenDN.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Không để trống tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Định dạng email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;

                } else if (mk.isEmpty() || nhaplaiMK.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!mk.equals(nhaplaiMK)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDangKy.this);
                    builder.setTitle("Thông báo").setMessage("Mật khẩu không khớp");
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                } else {
                    TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
                    taiKhoanDTO.setTenDN(edt_tenDangNhap_DK.getText().toString());
                    taiKhoanDTO.setEmail(editText_email.getText().toString());
                    taiKhoanDTO.setMatkhau(edt_matKhau_DK.getText().toString());
                    long kq = taiKhoanDAO.AddTaiKhoan(taiKhoanDTO);
                    if ( kq >0 ){
                        Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityDangKy.this,ActivityDangNhap.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();

                    }
                    // Chuyển đến Activity khác hoặc thực hiện hành động mong muốn
                    // startActivity(new Intent(ActivityDangKy.this, OtherActivity.class));
                }
            }
            private boolean isValidEmail(CharSequence target) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
        });




    }
}