package com.example.thondph162447_ass_adrnc.Adapters;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thondph162447_ass_adrnc.DAO.CongViecDAO;
import com.example.thondph162447_ass_adrnc.DAO.ThongTinDAO;
import com.example.thondph162447_ass_adrnc.DTO.ThongTinDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;
import com.example.thondph162447_ass_adrnc.Notifis.MessageActivity;
import com.example.thondph162447_ass_adrnc.Notifis.NotifyConfig;
import com.example.thondph162447_ass_adrnc.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongTinAdapter extends RecyclerView.Adapter<ThongTinAdapter.ViewHolder>{
    Context context;
    ArrayList<ThongTinDTO> listTT;

    public ThongTinAdapter(Context context, ArrayList<ThongTinDTO> listTT) {
        this.context = context;
        this.listTT = listTT;
    }
    public void updateData(ArrayList<ThongTinDTO> updatedList) {
        listTT.clear();
        listTT.addAll(updatedList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thongtin,parent,false);
        return new ThongTinAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ThongTinDTO thongTinDTO = listTT.get(position);
        holder.txt_maSV.setText(thongTinDTO.getMasv());
        holder.txt_ten.setText(thongTinDTO.getTen());
        holder.txt_lop.setText(thongTinDTO.getLop());
        holder.txt_mon.setText(thongTinDTO.getMon());

        holder.txt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_thongtin);
                DbHelper dbHelper = new DbHelper(context);


                TextInputEditText update_masv = dialog.findViewById(R.id.edt_update_masv);
                TextInputEditText update_ten = dialog.findViewById(R.id.edt_update_ten);
                TextInputEditText update_lop = dialog.findViewById(R.id.edt_update_lop);
                Spinner sp_update_tt = dialog.findViewById(R.id.sp_update_tt);
                Button btn_update_huy_tt = dialog.findViewById(R.id.btn_update_huy_tt);
                Button btn_update_tt = dialog.findViewById(R.id.btn_update_tt);

                List<String> monList = dbHelper.getMonList();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, monList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_update_tt.setAdapter(adapter);

                String monHocDuocChon  = thongTinDTO.getMon();
                int vitri = monList.indexOf(monHocDuocChon );

                if (vitri != -1) {
                    sp_update_tt.setSelection(vitri);
                }

                update_masv.setText(thongTinDTO.getMasv());
                update_ten.setText(thongTinDTO.getTen());
                update_lop.setText(thongTinDTO.getLop());

                btn_update_huy_tt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_update_tt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String masvMoi = update_masv.getText().toString();
                        String tenMoi = update_ten.getText().toString();
                        String lopMoi = update_lop.getText().toString();
                        String monHocMoi = sp_update_tt.getSelectedItem().toString();

                        thongTinDTO.setMasv(masvMoi);
                        thongTinDTO.setTen(tenMoi);
                        thongTinDTO.setLop(lopMoi);
                        thongTinDTO.setMon(monHocMoi);

                        ThongTinDAO thongTinDAO = new ThongTinDAO(context);
                        int ketQua = thongTinDAO.SuaThongTin(thongTinDTO);
                        if (ketQua > 0) {
                            notifyDataSetChanged();
                            listTT.clear();
                            listTT.addAll(thongTinDAO.getList());
                            GuiThongBao();
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();

                        }

                        dialog.dismiss();

                    }
                });



                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });


        holder.txt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa "+thongTinDTO.getMasv()+" không?").setTitle("CẢNH BÁO !!!!!")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ThongTinDAO thongTinDAO = new ThongTinDAO(context);
                                int kq = thongTinDAO.DeleteThongTin(thongTinDTO);
                                if (kq == 1){
                                    listTT.clear();
                                    listTT.addAll(thongTinDAO.getList());
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Xóa Thành Công",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context.getApplicationContext(), "Xóa Thất Bại",Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return listTT.size();
    }
    private void GuiThongBao() {
        try {
            Intent intentDemo = new Intent(context, MessageActivity.class);
            intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDemo.putExtra("duLieu", "Nội dung gửi từ Notify của main vào activity msg .... ");

            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intentDemo,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

//            Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Tạo NotificationChannel nếu chưa tồn tại
                NotificationChannel channel = new NotificationChannel(
                        NotifyConfig.CHANEL_ID,
                        "Sửa Thông Tin",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(context, NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo khẩn cấp")
                    .setContentText("Bạn đã sửa thông tin thành công ^^")
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
            Toast.makeText(context, "Có lỗi xảy ra khi tạo thông báo!" + e, Toast.LENGTH_SHORT).show();
            Log.d("zzzzzzzzzzz", "GuiThongBao: lỗi" + e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_maSV,txt_ten,txt_lop,txt_xoa,txt_sua;
        TextView txt_mon;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maSV = itemView.findViewById(R.id.txt_maSV);
            txt_ten = itemView.findViewById(R.id.txt_ten);
            txt_lop = itemView.findViewById(R.id.txt_lop);
            txt_mon = itemView.findViewById(R.id.txt_mon);
            txt_xoa = itemView.findViewById(R.id.txt_xoa_tt);
            txt_sua = itemView.findViewById(R.id.txt_sua_tt);


        }
    }
}
