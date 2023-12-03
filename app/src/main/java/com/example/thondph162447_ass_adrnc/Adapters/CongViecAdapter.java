package com.example.thondph162447_ass_adrnc.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thondph162447_ass_adrnc.Activitys.ActivityDangNhap;
import com.example.thondph162447_ass_adrnc.DAO.CongViecDAO;
import com.example.thondph162447_ass_adrnc.DTO.CongViecDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;
import com.example.thondph162447_ass_adrnc.Fragments.CongViec_Frag;
import com.example.thondph162447_ass_adrnc.MainActivity;
import com.example.thondph162447_ass_adrnc.Notifis.MessageActivity;
import com.example.thondph162447_ass_adrnc.Notifis.NotifyConfig;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<CongViecDTO> listcv;

    public void updateData(ArrayList<CongViecDTO> newData) {
        listcv.clear();
        listcv.addAll(newData);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }


    public CongViecAdapter(ArrayList<CongViecDTO> listcv, Context context) {
        this.listcv = listcv;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_congviec, parent, false);
        return new CongViecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CongViecDTO congViecDTO = listcv.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

//        holder.txt_id.setText(congViecDTO.getId());
        holder.txt_content.setText(congViecDTO.getContent());
        if (congViecDTO.getStart() != null && congViecDTO.getEnds() != null) {
            String startDate = dateFormat.format(congViecDTO.getStart());
            String endDate = dateFormat.format(congViecDTO.getEnds());
            holder.txt_start.setText(startDate);
            holder.txt_end.setText(endDate);
        } else {
            holder.txt_start.setText("Start Date is Null");
            holder.txt_end.setText("End Date is Null");
        }
        holder.txt_status.setText(congViecDTO.getStatus());
        holder.txt_name.setText(congViecDTO.getName());

        //xóa
        holder.txt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                builder.setMessage("Bạn có muốn xóa "+congViecDTO.getName()+" không?").setTitle("CẢNH BÁO !!!!!")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CongViecDAO congViecDAO = new CongViecDAO(mContext);
                                int kq = congViecDAO.XoaCongViec(congViecDTO);
                                if (kq == 1) {
                                    listcv.clear();
                                    listcv.addAll(congViecDAO.getList());
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext.getApplicationContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                }

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

        //Sửa
        holder.txt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_update_job);
                TextView update_txt_start = dialog.findViewById(R.id.update_txt_start);
                TextView update_txt_end = dialog.findViewById(R.id.update_txt_end);
                ImageView update_img_end = dialog.findViewById(R.id.update_img_end);
                ImageView update_img_start = dialog.findViewById(R.id.update_img_start);
                Spinner update_sprint_cv = dialog.findViewById(R.id.update_sprint_cv);
                TextInputEditText edt_name = dialog.findViewById(R.id.update_nameCV);
                TextInputEditText edt_content = dialog.findViewById(R.id.update_contentCV);
                Button btnSua = dialog.findViewById(R.id.btn_update_cv);
                Button btnHuy = dialog.findViewById(R.id.update_btn_huy_cv);


                // Gán dữ liệu từ selectedCongViec vào các thành phần trong Dialog
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String startDate = dateFormat.format(congViecDTO.getStart());
                String endDate = dateFormat.format(congViecDTO.getEnds());
                update_txt_start.setText(startDate);
                update_txt_end.setText(endDate);
                edt_name.setText(congViecDTO.getName());
                edt_content.setText(congViecDTO.getContent());

                DbHelper dbHelper = new DbHelper(mContext);
                List<String> StatusList = dbHelper.getStatusList();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, StatusList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                update_sprint_cv.setAdapter(adapter);

                String viTriStatus = congViecDTO.getStatus();
                int vitri = StatusList.indexOf(viTriStatus);

                if (vitri != -1) {
                    update_sprint_cv.setSelection(vitri);
                }

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        update_txt_end.setText(selectedDate);
                    }
                };
                DatePickerDialog startDatePickerDialog = new DatePickerDialog(dialog.getContext(), startDateSetListener, year, month, day);

                DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        update_txt_start.setText(selectedDate);
                    }
                };

                DatePickerDialog endDatePickerDialog = new DatePickerDialog(dialog.getContext(), endDateSetListener, year, month, day);
                update_img_start.setOnClickListener(v1 -> startDatePickerDialog.show());
                update_img_end.setOnClickListener(v12 -> endDatePickerDialog.show());
                //hủy
                btnHuy.setOnClickListener(v13 -> dialog.cancel());

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newStartDate = update_txt_start.getText().toString();
                        String newEndDate = update_txt_end.getText().toString();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date startDate = null, endDate = null;

                        try {
                            startDate = dateFormat.parse(newStartDate);
                            endDate = dateFormat.parse(newEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        congViecDTO.setStart(startDate);
                        congViecDTO.setEnds(endDate);


                        String newName = edt_name.getText().toString();
                        String newContent = edt_content.getText().toString();
                        String statusNew = update_sprint_cv.getSelectedItem().toString();

                        congViecDTO.setName(newName);
                        congViecDTO.setContent(newContent);
                        congViecDTO.setStatus(statusNew);
                        CongViecDAO congViecDAO = new CongViecDAO(mContext);

                        int kq = congViecDAO.SuaCongViec(congViecDTO);
                        if (kq > 0) {
                            notifyDataSetChanged();
                            listcv.clear();
                            listcv.addAll(congViecDAO.getList());
                            GuiThongBao();
                            Toast.makeText(mContext, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else
                            Toast.makeText(mContext, "Sửa thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });


    }

    private void GuiThongBao() {
        try {
            Intent intentDemo = new Intent(mContext, MessageActivity.class);
            intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDemo.putExtra("duLieu", "Nội dung gửi từ Notify của main vào activity msg .... ");

            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    mContext,
                    0,
                    intentDemo,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

//            Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Tạo NotificationChannel nếu chưa tồn tại
                NotificationChannel channel = new NotificationChannel(
                        NotifyConfig.CHANEL_ID,
                        "Sửa công việc",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(mContext, NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo khẩn cấp")
                    .setContentText("Bạn đã sửa công việc thành công ^^")
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
            Toast.makeText(mContext, "Có lỗi xảy ra khi tạo thông báo!" + e, Toast.LENGTH_SHORT).show();
            Log.d("zzzzzzzzzzz", "GuiThongBao: lỗi" + e);
        }
    }

    @Override
    public int getItemCount() {
        return listcv.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_content, txt_status, txt_start, txt_end, txt_sua, txt_xoa;
        Spinner update_sprint_cv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_content = itemView.findViewById(R.id.txt_content);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_start = itemView.findViewById(R.id.txt_start);
            txt_end = itemView.findViewById(R.id.txt_end);
            txt_sua = itemView.findViewById(R.id.txt_sua);
            txt_xoa = itemView.findViewById(R.id.txtXoa);
            update_sprint_cv = itemView.findViewById(R.id.update_sprint_cv);
        }
    }
}
