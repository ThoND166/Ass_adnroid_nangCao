package com.example.thondph162447_ass_adrnc.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thondph162447_ass_adrnc.DAO.TaiKhoanDAO;
import com.example.thondph162447_ass_adrnc.DTO.TaiKhoanDTO;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

public class ActivityQuenMK extends AppCompatActivity {
    Button btnHuy, btnKiemtra;
    TextInputEditText edt_nameKT, edt_emailKT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mk);
        btnHuy = findViewById(R.id.btnHuyTK);
        btnKiemtra = findViewById(R.id.btnKiemtra);
        edt_nameKT = findViewById(R.id.edt_nameQuenTK);
        edt_emailKT = findViewById(R.id.edt_emailQuenTK);

        btnKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edt_nameKT.getText().toString();
                String email = edt_emailKT.getText().toString();

                // Gọi phương thức getTaiKhoanByTenEmail từ TaiKhoanDAO để kiểm tra thông tin đăng nhập
                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(ActivityQuenMK.this);
                TaiKhoanDTO taiKhoan = taiKhoanDAO.getTaiKhoanByTenEmail(tenDN, email);

                if (taiKhoan != null) {
                    Dialog dialog = new Dialog(ActivityQuenMK.this);
                    dialog.setContentView(R.layout.dialog_tao_matkhau);
                    TextInputEditText edt_tao_mk = dialog.findViewById(R.id.edt_tao_matKhau);
                    Button btn_addMK = dialog.findViewById(R.id.btn_add_mk);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                    btn_addMK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String passNew = edt_tao_mk.getText().toString();

                            taiKhoan.setMatkhau(passNew);

                            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(ActivityQuenMK.this);
                            int kq = taiKhoanDAO.capNhatMatKhau(taiKhoan); // Cập nhật mật khẩu trong cơ sở dữ liệu

                            if (kq > 0) {
                                Toast.makeText(ActivityQuenMK.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(new Intent(ActivityQuenMK.this,ActivityDangNhap.class));
                            } else {
                                Toast.makeText(ActivityQuenMK.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(ActivityQuenMK.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityQuenMK.this,ActivityDangNhap.class));
            }
        });
    }
}