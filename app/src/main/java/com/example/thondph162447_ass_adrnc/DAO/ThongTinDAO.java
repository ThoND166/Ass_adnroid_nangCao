package com.example.thondph162447_ass_adrnc.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thondph162447_ass_adrnc.DTO.ThongTinDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;

import java.util.ArrayList;

public class ThongTinDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public ThongTinDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public ArrayList<ThongTinDTO> getList() {
        ArrayList<ThongTinDTO> listTt = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_thongTin ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ThongTinDTO thongTinDTO = new ThongTinDTO();
                thongTinDTO.setId(cursor.getInt(0));
                thongTinDTO.setMasv(cursor.getString(1));
                thongTinDTO.setTen(cursor.getString(2));
                thongTinDTO.setLop(cursor.getString(3));
                thongTinDTO.setMon(cursor.getString(4));
                listTt.add(thongTinDTO);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return listTt;
    }
    public long AddThongTin(ThongTinDTO thongTinDTO) {
        ContentValues values = new ContentValues();

        // Gán giá trị cho các cột tương ứng trong cơ sở dữ liệu từ đối tượng ThongTinDTO
        values.put("masv", thongTinDTO.getMasv());
        values.put("ten", thongTinDTO.getTen());
        values.put("lop", thongTinDTO.getLop());
        values.put("mon", thongTinDTO.getMon());
       long  kq = database.insert("tb_thongTin", null, values);
       return kq;
    }

    public int SuaThongTin(ThongTinDTO thongTinDTO) {
        ContentValues values = new ContentValues();

        // Gán giá trị cho các cột tương ứng trong cơ sở dữ liệu từ đối tượng ThongTinDTO
        values.put("masv", thongTinDTO.getMasv());
        values.put("ten", thongTinDTO.getTen());
        values.put("lop", thongTinDTO.getLop());
        values.put("mon", thongTinDTO.getMon());
        int  kq = database.update("tb_thongTin", values,"id="+thongTinDTO.getId(),null);
        return kq;
    }

    public int DeleteThongTin(ThongTinDTO thongTinDTO) {

        int  kq = database.delete("tb_thongTin", "id="+thongTinDTO.getId(),null);
        return kq;
    }


}
