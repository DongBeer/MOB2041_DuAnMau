package dongnvph30597.fpoly.ass_demo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DB_ThuVien dbHelper = new DB_ThuVien(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("hoTen",tv.hoTen);
        values.put("namSinh",tv.namSinh);
        values.put("soTK",tv.soTK);
        return db.insert("ThanhVien",null,values);
    }

    public long update(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("hoTen",tv.hoTen);
        values.put("namSinh",tv.namSinh);
        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(tv.maTV)});
    }

    public int delete(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE maTV=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int checkTV = db.delete("ThanhVien","maTV=?",new String[]{id});
        if (checkTV == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public ArrayList<ThanhVien> getAllTV(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    public ArrayList<ThanhVien> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<ThanhVien> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                ThanhVien tv = new ThanhVien();
                tv.setMaTV(cursor.getInt(0));
                tv.setHoTen(cursor.getString(1));
                tv.setSoTK(cursor.getInt(2));
                tv.setNamSinh(cursor.getString(3));
                arr.add(tv);
                cursor.moveToNext();
            }
        }
        return arr;
    }
    public ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }
}
