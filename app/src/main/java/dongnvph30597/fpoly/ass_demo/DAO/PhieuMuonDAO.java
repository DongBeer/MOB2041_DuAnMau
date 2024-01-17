package dongnvph30597.fpoly.ass_demo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.PhieuMuon;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class PhieuMuonDAO {
    private SQLiteDatabase db;

    public PhieuMuonDAO(Context context) {
        DB_ThuVien dbHelper = new DB_ThuVien(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon pm){
        ContentValues values = new ContentValues();
        values.put("maTT",pm.maTT);
        values.put("maTV",pm.maTV);
        values.put("maSach",pm.maSach);
        values.put("ngay",String.valueOf(pm.ngay));
        values.put("tienThue",pm.tienThue);
        values.put("traSach",pm.traSach);
        return db.insert("PhieuMuon",null,values);
    }

    public long update(PhieuMuon pm){
        ContentValues values = new ContentValues();
        values.put("maTT",pm.maTT);
        values.put("maTV",pm.maTV);
        values.put("maSach",pm.maSach);
        values.put("ngay",String.valueOf(pm.ngay));
        values.put("tienThue",pm.tienThue);
        values.put("traSach",pm.traSach);
        return db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(pm.maPm)});
    }

    public int delete(String id){
        return db.delete("PhieuMuon","maPM=?",new String[]{id});
    }

    public ArrayList<PhieuMuon> getAllPM(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql,id);
        return list.get(0);
    }

    public ArrayList<PhieuMuon> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPm(cursor.getInt(0));
                pm.setMaTT(cursor.getString(1));
                pm.setMaTV(cursor.getInt(2));
                pm.setMaSach(cursor.getInt(3));
                pm.setTienThue(cursor.getInt(4));
                pm.setNgay(Date.valueOf(cursor.getString(5)));
                pm.setTraSach(cursor.getInt(6));
                arr.add(pm);
                cursor.moveToNext();
            }
        }
        return arr;
    }
}
