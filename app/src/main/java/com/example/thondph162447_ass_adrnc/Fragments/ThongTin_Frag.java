package com.example.thondph162447_ass_adrnc.Fragments;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thondph162447_ass_adrnc.Adapters.CongViecAdapter;
import com.example.thondph162447_ass_adrnc.Adapters.ThongTinAdapter;
import com.example.thondph162447_ass_adrnc.DAO.CongViecDAO;
import com.example.thondph162447_ass_adrnc.DAO.ThongTinDAO;
import com.example.thondph162447_ass_adrnc.DTO.CongViecDTO;
import com.example.thondph162447_ass_adrnc.DTO.ThongTinDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;
import com.example.thondph162447_ass_adrnc.Notifis.MessageActivity;
import com.example.thondph162447_ass_adrnc.Notifis.NotifyConfig;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongTin_Frag extends Fragment {
    RecyclerView rcv_tt;
    ImageView img_add_tt;

    ThongTinAdapter adapter;
    ThongTinDAO thongTinDAO;
    ArrayList<ThongTinDTO> listTT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtin_activity, container, false);
        img_add_tt = view.findViewById(R.id.img_add_tt);
        rcv_tt = view.findViewById(R.id.rcv_tt);
        thongTinDAO = new ThongTinDAO(getContext());
        listTT = thongTinDAO.getList();
        adapter = new ThongTinAdapter(getContext(), listTT);
        rcv_tt.setAdapter(adapter);
        rcv_tt.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_tt.setLayoutManager(new LinearLayoutManager(getContext()));
        EditText edt_search_tt = view.findViewById(R.id.edt_search_tt);

        edt_search_tt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<ThongTinDTO> ds = new ArrayList<>();

                if (searchText.isEmpty()) {
                    adapter.updateData(thongTinDAO.getList()); // Hiển thị lại toàn bộ danh sách khi ô EditText trống
                    return;
                }

                for (ThongTinDTO cv : thongTinDAO.getList()) {
                    if (cv.getMasv().toLowerCase().contains(searchText)) {
                        ds.add(cv);
                    }
                }

                adapter.updateData(ds);
            }
        });


        img_add_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();

            }
        });


        return view;

    }

    private void showAdd() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_thongtin);
        Spinner spMon = dialog.findViewById(R.id.sp_tt);
        TextInputEditText edt_masv = dialog.findViewById(R.id.edt_maSv);
        TextInputEditText edt_ten = dialog.findViewById(R.id.edt_ten);
        TextInputEditText edt_lop = dialog.findViewById(R.id.edt_lop);
        Button btn_add = dialog.findViewById(R.id.btn_add_tt);
        Button btn_huy = dialog.findViewById(R.id.btn_huy_tt);
        DbHelper dbHelper = new DbHelper(getContext());
        List<String> monList = dbHelper.getMonList();

        // Tạo ArrayAdapter cho Spinner
        ArrayAdapter<String> madapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, monList);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        spMon.setAdapter(madapter);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masv = edt_masv.getText().toString();
                String ten = edt_ten.getText().toString();
                String lop = edt_lop.getText().toString();
                String mon = spMon.getSelectedItem().toString();

                if (!masv.isEmpty() && !ten.isEmpty() && !lop.isEmpty() && !mon.isEmpty()) {
                    ThongTinDTO thongTinDTO = new ThongTinDTO();
                    thongTinDTO.setMasv(masv);
                    thongTinDTO.setTen(ten);
                    thongTinDTO.setLop(lop);
                    thongTinDTO.setMon(mon);

                    long kq = thongTinDAO.AddThongTin(thongTinDTO);
                    if (kq != -1) {
                        ArrayList<ThongTinDTO> updateList = thongTinDAO.getList();
                        adapter.updateData(updateList);
                        adapter.notifyDataSetChanged();
                        GuiThongBao();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });



        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void GuiThongBao() {
        try {
            Intent intentDemo = new Intent(getContext(), MessageActivity.class);
            intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDemo.putExtra("duLieu", "Nội dung gửi từ Notify của main vào activity msg .... ");

            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    getContext(),
                    0,
                    intentDemo,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

//            Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireContext());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Tạo NotificationChannel nếu chưa tồn tại
                NotificationChannel channel = new NotificationChannel(
                        NotifyConfig.CHANEL_ID,
                        "Thêm thông tin",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(requireContext(), NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo khẩn cấp")
                    .setContentText("Bạn đã thêm thông tin thành công ^^")
                    .setContentIntent(resultPendingIntent)
//                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(anh))
//                    .setLargeIcon(anh)
//                    .setColor(Color.RED)
//                    .setChannelId(NotifyConfig.CHANEL_ID) // Chỉ định NotificationChannel cho thông báo
                    .build();

            int id_notify = (int) new Date().getTime();
            notificationManagerCompat.notify(id_notify, customNotification);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Có lỗi xảy ra khi tạo thông báo!" + e, Toast.LENGTH_SHORT).show();
            Log.d("zzzzzzzzzzz", "GuiThongBao: lỗi" + e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
