package com.example.thondph162447_ass_adrnc.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thondph162447_ass_adrnc.DTO.TaiKhoanDTO;
import com.example.thondph162447_ass_adrnc.DTO.ThongTinDTO;
import com.example.thondph162447_ass_adrnc.Databases.DbHelper;

import java.util.ArrayList;

public class TaiKhoanDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public TaiKhoanDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<TaiKhoanDTO> getListTK(){
        ArrayList<TaiKhoanDTO> listTK = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_taikhoan ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
                taiKhoanDTO.setId(cursor.getInt(0));
                taiKhoanDTO.setTenDN(cursor.getString(1));
                taiKhoanDTO.setEmail(cursor.getString(2));
                taiKhoanDTO.setMatkhau(cursor.getString(3));

                listTK.add(taiKhoanDTO);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return listTK;
    }

    public boolean checkLogin(String tenDN, String mk, String email) {
        String[] columns = {"id"};


        String selection = "tenDN = ? AND matkhau = ? AND email = ?";
        String[] selectionArgs = {tenDN, mk, email};

        Cursor cursor = database.query("tb_taikhoan", columns, selection, selectionArgs, null, null, null);

        boolean loginSuccessful = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return loginSuccessful;
    }


    public TaiKhoanDTO getTaiKhoanByTenEmail(String tenDN, String email) {
        TaiKhoanDTO taiKhoan = null;

        String[] columns = {"id", "tenDN", "email", "matkhau"};
        String selection = "tenDN = ? AND email = ?";
        String[] selectionArgs = {tenDN, email};

        Cursor cursor = database.query("tb_taikhoan", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int tenDNIndex = cursor.getColumnIndex("tenDN");
                int emailIndex = cursor.getColumnIndex("email");
                int matKhauIndex = cursor.getColumnIndex("matkhau");

                if (idIndex != -1 && tenDNIndex != -1 && emailIndex != -1 && matKhauIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String retrievedTenDN = cursor.getString(tenDNIndex);
                    String retrievedEmail = cursor.getString(emailIndex);
                    String matKhau = cursor.getString(matKhauIndex);

                    taiKhoan = new TaiKhoanDTO(id, retrievedTenDN, retrievedEmail, matKhau);
                }
            }
            cursor.close();
        }

        return taiKhoan;
    }

    public TaiKhoanDTO getTaiKhoanByMatKhau(String matKhau) {
        TaiKhoanDTO taiKhoan = null;

        String[] columns = {"id", "tenDN", "email", "matkhau"};
        String selection = "matkhau = ?";
        String[] selectionArgs = {matKhau}; // Chỉ cần matKhau là đủ

        Cursor cursor = database.query("tb_taikhoan", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int tenDNIndex = cursor.getColumnIndex("tenDN");
                int emailIndex = cursor.getColumnIndex("email");
                int matKhauIndex = cursor.getColumnIndex("matkhau");

                if (idIndex != -1 && tenDNIndex != -1 && emailIndex != -1 && matKhauIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String retrievedTenDN = cursor.getString(tenDNIndex);
                    String retrievedEmail = cursor.getString(emailIndex);
                    String retrievedMatKhau = cursor.getString(matKhauIndex);

                    taiKhoan = new TaiKhoanDTO(id, retrievedTenDN, retrievedEmail, retrievedMatKhau);
                }
            }
            cursor.close();
        }

        return taiKhoan;
    }


    public int capNhatMatKhau(TaiKhoanDTO taiKhoan) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matkhau", taiKhoan.getMatkhau());

        String[] whereArgs = {String.valueOf(taiKhoan.getId())};

        int rowsAffected = database.update("tb_taikhoan", values, "id=?", whereArgs);
        database.close();

        return rowsAffected;
    }



    public  long AddTaiKhoan(TaiKhoanDTO taiKhoanDTO){
        ContentValues values = new ContentValues();
        values.put("tenDN ",taiKhoanDTO.getTenDN());
        values.put("email  ",taiKhoanDTO.getEmail());
        values.put("matkhau  ",taiKhoanDTO.getMatkhau());
        long kq = database.insert("tb_taikhoan",null,values);
        return kq;
    }

    public  int XoaTaiKhoan(TaiKhoanDTO taiKhoanDTO){

        int kq = database.delete("tb_taikhoan","id="+taiKhoanDTO.getId(),null);
        return kq;
    }
//
//
//    public boolean doiMatKhau(int userId, String mkCu, String mkMoi) {
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("matkhau", mkMoi);
//
//        String[] whereArgs = {String.valueOf(userId), mkCu}; // Sử dụng userId và mkCu để xác định người dùng và mật khẩu cũ
//
//        int rowsAffected = database.update("tb_taikhoan", values, "id=? AND matkhau=?", whereArgs);
//        database.close();
//
//        return rowsAffected > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
//    }

}
