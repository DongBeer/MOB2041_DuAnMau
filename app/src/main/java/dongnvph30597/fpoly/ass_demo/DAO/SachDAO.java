package dongnvph30597.fpoly.ass_demo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;
import dongnvph30597.fpoly.ass_demo.model.Sach;

public class SachDAO {

    private SQLiteDatabase db;

    public SachDAO(Context context) {
        DB_ThuVien dbHelper = new DB_ThuVien(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insert(Sach odj){
        ContentValues values = new ContentValues();
        values.put("tenSach",odj.tenSach);
        values.put("giaThue",odj.giaThue);
        values.put("soluong",odj.getSoluong());
        values.put("maLoai",odj.maLoai);
        return db.insert("Sach",null,values);
    }

    public long update(Sach odj){
        ContentValues values = new ContentValues();
        values.put("tenSach",odj.tenSach);
        values.put("giaThue",odj.giaThue);
        values.put("soluong",odj.getSoluong());
        values.put("maLoai",odj.maLoai);
        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(odj.maSach)});
    }

    public int delete(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE maSach=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
         int checkmaSach = db.delete("Sach","maSach=?",new String[]{id});
        if (checkmaSach == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public ArrayList<Sach> getAllSach(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    public ArrayList<Sach> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<Sach> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setSoluong(cursor.getInt(3));
                sach.setMaLoai(cursor.getInt(4));
                arr.add(sach);
                cursor.moveToNext();
            }
        }
        return arr;
    }
    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }

    public ArrayList<Sach> timkiemSach(int soluong){
        String sqlTim = "SELECT * FROM Sach WHERE soluong <= ?";
        ArrayList<Sach> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlTim,new String[]{String.valueOf(soluong)});
        while (cursor.moveToNext()){
            Sach s = new Sach();
            s.setMaSach(cursor.getInt(0));
            s.setTenSach(cursor.getString(1));
            s.setGiaThue(cursor.getInt(2));
            s.setSoluong(cursor.getInt(3));
            s.setMaLoai(cursor.getInt(4));
            arr.add(s);
        }
        return arr;
    }
}
