package com.example.thondph162447_ass_adrnc.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thondph162447_ass_adrnc.DTO.CongViecDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CongViecDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public CongViecDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }



    public ArrayList<CongViecDTO> getList() {
        ArrayList<CongViecDTO> listcv = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_congViec ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CongViecDTO congViecDTO = new CongViecDTO();
                congViecDTO.setId(cursor.getInt(0));
                congViecDTO.setName(cursor.getString(1));
                congViecDTO.setContent(cursor.getString(2));
                congViecDTO.setStatus(cursor.getString(3));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = dateFormat.parse(cursor.getString(4));
                    Date endDate = dateFormat.parse(cursor.getString(5));
                    congViecDTO.setStart(startDate);
                    congViecDTO.setEnds(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                listcv.add(congViecDTO);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listcv;
    }
    public long themCongViec(CongViecDTO congViec) {

        if (database == null || congViec == null) {
            return -1; // Hoặc giá trị khác để biểu thị lỗi
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("name", congViec.getName());
        values.put("content", congViec.getContent()); // Nếu muốn content là "Mới tạo"
        values.put("status", "Mới tạo");
        values.put("start", dateFormat.format(congViec.getStart()));
        values.put("ends", dateFormat.format(congViec.getEnds()));

        long kq = database.insert("tb_congViec", null, values);


        return kq; // Trả về giá trị kết quả của hàm insert()
    }

    public int XoaCongViec(CongViecDTO congViecDTO){
        int kq = database.delete("tb_congViec","id=" +congViecDTO.getId(),null);
        return kq;
    }

    public int SuaCongViec(CongViecDTO congViec){
        if (database == null || congViec == null) {
            return -1; // Hoặc giá trị khác để biểu thị lỗi
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("name", congViec.getName());
        values.put("content", congViec.getContent()); // Nếu muốn content là "Mới tạo"
        values.put("status", congViec.getStatus());
        values.put("start", dateFormat.format(congViec.getStart()));
        values.put("ends", dateFormat.format(congViec.getEnds()));
        int kq = database.update("tb_congViec",values,"id="+congViec.getId(),null);
        return  kq;
    }



}
