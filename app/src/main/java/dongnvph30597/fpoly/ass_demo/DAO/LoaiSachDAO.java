package dongnvph30597.fpoly.ass_demo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;

public class LoaiSachDAO {

    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DB_ThuVien dbHelper = new DB_ThuVien(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insert(LoaiSach odj){
        ContentValues values = new ContentValues();
        values.put("nhaSX",odj.nhaSX);
        values.put("tenLoai",odj.tenLoai);
        return db.insert("LoaiSach",null,values);
    }

    public long update(LoaiSach odj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",odj.tenLoai);
        values.put("nhaSX",odj.nhaSX);
        return db.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(odj.maLoai)});
    }

    public int delete(String id){

        Cursor cursor = db.rawQuery("select * from Sach where maloai=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int checkLoaisach = db.delete("LoaiSach","maLoai=?",new String[]{id});
        if (checkLoaisach == -1)
            return 0;
        return 1;
    }

    public ArrayList<LoaiSach> getAllLoaiSach() {
        Cursor cursor = db.rawQuery("SELECT * FROM LoaiSach", null);
        ArrayList<LoaiSach> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                loaiSach.setNhaSX(cursor.getString(2));
                arr.add(loaiSach);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public int xoaSach(int id) {
        Cursor cursor = db.rawQuery("select * from Sach where maloai=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        } else {
            int check = db.delete("LoaiSach", "maLoai=?", new String[]{String.valueOf(id)});
            if (check == -1)
                return 0;
            return 1;
        }
    }
}
