package com.example.thondph162447_ass_adrnc.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper( Context context) {
        super(context, "Ass.db", null, 2);
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql_cv = "CREATE TABLE tb_congViec ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL, "
                    + "content TEXT NOT NULL, "
                    + "status TEXT NOT NULL, "
                    + "start DATETIME NOT NULL, "
                    + "ends DATETIME NOT NULL)";
            db.execSQL(sql_cv);

            String sql_tt = "CREATE TABLE tb_thongTin (" +
                    "id INTEGER PRIMARY KEY," +
                    "masv TEXT,"+
                    "ten TEXT," +
                    "lop TEXT," +
                    "mon TEXT" +
                    ")";
            db.execSQL(sql_tt);

            String insertDataQuery = "INSERT INTO tb_congViec (name, content, status, start, ends) VALUES "
                    + "('Task 1', 'Content 1', 'Pending', '2023-01-01 08:00:00', '2023-01-02 17:00:00'), "
                    + "('Task 2', 'Content 2', 'In Progress', '2023-01-03 09:00:00', '2023-01-04 18:00:00'), "
                    + "('Task 3', 'Content 3', 'Completed', '2023-01-05 10:00:00', '2023-01-06 19:00:00')";

            db.execSQL(insertDataQuery);

            String sql_taikhoan = "CREATE TABLE tb_taikhoan (id INTEGER PRIMARY KEY AUTOINCREMENT, tenDN TEXT NOT NULL, email TEXT NOT NULL, matkhau TEXT NOT NULL)";
            db.execSQL(sql_taikhoan);


        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_congViec");
        db.execSQL("DROP TABLE IF EXISTS tb_thongTin");
        onCreate(db);
    }
    public List<String> getStatusList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Mới tạo");
        statusList.add("Đang làm");
        statusList.add("Tạm dừng");
        statusList.add("Hoàn thành");

        return statusList;
    }

    public List<String> getMonList() {
        List<String> monList = new ArrayList<>();
        monList.add("Android 1");
        monList.add("Android 2");
        monList.add("Android 3");
        monList.add("Android 4");
        return monList;
    }

    public boolean coDuLieuTrongCSDL() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_taikhoan", null);

        boolean data = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        return data;
    }

}
