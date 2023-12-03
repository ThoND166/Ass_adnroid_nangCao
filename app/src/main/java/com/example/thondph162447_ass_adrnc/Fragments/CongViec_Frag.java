package com.example.thondph162447_ass_adrnc.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thondph162447_ass_adrnc.Adapters.CongViecAdapter;
import com.example.thondph162447_ass_adrnc.DAO.CongViecDAO;
import com.example.thondph162447_ass_adrnc.DTO.CongViecDTO;
import com.example.thondph162447_ass_adrnc.Notifis.MessageActivity;
import com.example.thondph162447_ass_adrnc.Notifis.NotifyConfig;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CongViec_Frag extends Fragment {
    private RecyclerView rcv_cv;
    private CongViecAdapter adapter;
    private CongViecDAO congViecDAO;
    ArrayList<CongViecDTO> listCv;

    EditText edt_search_cv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.congviec_activity, container, false);

        rcv_cv = view.findViewById(R.id.rcv_congviec);
        edt_search_cv = view.findViewById(R.id.edt_search_cv);
        congViecDAO = new CongViecDAO(getContext());
        listCv = congViecDAO.getList();
        adapter = new CongViecAdapter(listCv, getContext());
        rcv_cv.setAdapter(adapter);
        rcv_cv.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView img_add_cv = view.findViewById(R.id.img_add_cv);


        edt_search_cv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<CongViecDTO> ds = new ArrayList<>();

                if (searchText.isEmpty()) {
                    adapter.updateData(congViecDAO.getList()); // Hiển thị lại toàn bộ danh sách khi ô EditText trống
                    return;
                }

                for (CongViecDTO cv : congViecDAO.getList()) {
                    if (cv.getName().toLowerCase().contains(searchText)) {
                        ds.add(cv);
                    }
                }

                adapter.updateData(ds); // Cập nhật dữ liệu hiển thị trong RecyclerView
            }
        });


        img_add_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });
        return view;
    }

    private void showAdd() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_job);

        // Khai báo các View trong dialog ở đây
        TextInputEditText edt_nameCV = dialog.findViewById(R.id.edt_nameCV);
        TextInputEditText edt_contentCV = dialog.findViewById(R.id.edt_contentCV);
        TextView txt_start = dialog.findViewById(R.id.txt_start);
        TextView txt_end = dialog.findViewById(R.id.txt_end);
        ImageView img_start = dialog.findViewById(R.id.img_start);
        ImageView img_end = dialog.findViewById(R.id.img_end);
        Button btn_AddCv = dialog.findViewById(R.id.btn_add_cv);
        Button btn_HuyCv = dialog.findViewById(R.id.btn_huy_cv);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                txt_start.setText(selectedDate);
            }
        };

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(getActivity(), startDateSetListener, year, month, day);

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                txt_end.setText(selectedDate);
            }
        };

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(getActivity(), endDateSetListener, year, month, day);
        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });

        img_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePickerDialog.show();
            }
        });
        btn_HuyCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_AddCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_nameCV.getText().toString();
                String content = edt_contentCV.getText().toString();
                String start = txt_start.getText().toString();
                String end = txt_end.getText().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date startDate, endDate;
                if (!name.isEmpty() && !content.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                    try {
                        startDate = dateFormat.parse(start);
                        endDate = dateFormat.parse(end);
                        CongViecDTO newCongViec = new CongViecDTO(name, content, "", startDate, endDate);
                        long result = congViecDAO.themCongViec(newCongViec);
                        if (result != -1) {
                            listCv.clear();
                            listCv.addAll(congViecDAO.getList());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Thêm công việc thành công", Toast.LENGTH_SHORT).show();
                            GuiThongBao();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm công việc thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
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
                        "Thêm công việc",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(requireContext(), NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo khẩn cấp")
                    .setContentText("Bạn đã thêm công việc thành công ^^")
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






}
