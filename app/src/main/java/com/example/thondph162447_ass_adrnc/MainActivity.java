package com.example.thondph162447_ass_adrnc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thondph162447_ass_adrnc.Activitys.ActivityDangNhap;
import com.example.thondph162447_ass_adrnc.Activitys.ActivityQuenMK;
import com.example.thondph162447_ass_adrnc.DAO.TaiKhoanDAO;
import com.example.thondph162447_ass_adrnc.DTO.TaiKhoanDTO;
import com.example.thondph162447_ass_adrnc.Fragments.CongViec_Frag;
import com.example.thondph162447_ass_adrnc.Fragments.ThongTin_Frag;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout001;
    Toolbar mtoolbar001;
    NavigationView nav001;
    TaiKhoanDAO taiKhoanDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout001 = findViewById(R.id.drawerlayout001);
        mtoolbar001 = findViewById(R.id.mtoolbar001);
        nav001 = findViewById(R.id.nav001);
        taiKhoanDAO = new TaiKhoanDAO(this);
        CongViec_Frag fr = new CongViec_Frag();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container001, fr)
                .commit();
        mtoolbar001.setTitle("Công việc");
        setSupportActionBar( mtoolbar001);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout001,
                mtoolbar001,
                R.string.open,
                R.string.close // vào file values/string thêm 2 thẻ string đặt tên open và close
                //          <string name="open">Open</string>
                //           <string name="close">Close</string>
        );
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout001.addDrawerListener(drawerToggle);
        nav001.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fr ;
                if(item.getItemId() == R.id.nav_congViec){
                    // trang chính thì hiện frag111
                    fr = new CongViec_Frag();
                    mtoolbar001.setTitle("Công việc");
                }else  if(item.getItemId() ==R.id.nav_gioiThieu ){

                    fr = new ThongTin_Frag();
                    mtoolbar001.setTitle("Thông tin");
                }
                else  if(item.getItemId() ==R.id.nav_caiDat ){

                    ShowDialog();
                    return true;
                }
                else  if(item.getItemId() ==R.id.nav_dangXuat ){
                    DangXuat();
                    return true;
                }
                else{
                    fr = new CongViec_Frag(); // để tạm bằng frag1
                    mtoolbar001.setTitle( item.getTitle()  );
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container001, fr)
                        .commit();
                drawerLayout001.close();
                return true;
            }
            private void DangXuat() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có muốn thoát?").setTitle("CẢNH BÁO !!!!!")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(MainActivity.this, ActivityDangNhap.class));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });


    }

    private void ShowDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_doipass);

        TextInputEditText edtMkCu = dialog.findViewById(R.id.edt_mkCu);
        TextInputEditText edtMkMoi = dialog.findViewById(R.id.edt_mkMoi);
        TextInputEditText edtNlaiMkMoi = dialog.findViewById(R.id.edt_nlaiMkMoi);
        Button btnAddMk = dialog.findViewById(R.id.btn_add_mk);


        btnAddMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkCu = edtMkCu.getText().toString().trim();
                String mkMoi = edtMkMoi.getText().toString().trim();
                String NLmk = edtNlaiMkMoi.getText().toString().trim();

                if (!mkCu.isEmpty() && !mkMoi.isEmpty() && !NLmk.isEmpty()) {
                    if (mkMoi.equals(NLmk)) {
                        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(MainActivity.this);
                        TaiKhoanDTO taiKhoan = taiKhoanDAO.getTaiKhoanByMatKhau(mkCu);

                        if (taiKhoan != null) {
                            taiKhoan.setMatkhau(mkMoi);
                            int kq = taiKhoanDAO.capNhatMatKhau(taiKhoan);

                            if (kq > 0) {
                                Toast.makeText(MainActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(new Intent(MainActivity.this, ActivityDangNhap.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Sai thông tin!!!! ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }



}