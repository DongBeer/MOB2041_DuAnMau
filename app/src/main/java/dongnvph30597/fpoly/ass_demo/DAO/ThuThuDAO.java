package dongnvph30597.fpoly.ass_demo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;

public class ThuThuDAO {

    SQLiteDatabase db;
    public ThuThuDAO(Context context) {
        DB_ThuVien dbThuVien = new DB_ThuVien(context);
        db = dbThuVien.getWritableDatabase();
    }

    public long insert(ThuThu tt){
        ContentValues values = new ContentValues();
        values.put("maTT",tt.maTT);
        values.put("hoTen",tt.hoTen);
        values.put("matKhau",tt.matKhau);
        return db.insert("ThuThu",null,values);
    }
    public long insertadmin(){
        ContentValues values = new ContentValues();
        values.put("maTT","admin");
        values.put("hoTen","ADMIN");
        values.put("matKhau","admin");
        return db.insert("ThuThu",null,values);
    }

    public long update(ThuThu tt){
        ContentValues values = new ContentValues();
        values.put("maTT",tt.maTT);
        values.put("hoTen",tt.hoTen);
        values.put("matKhau",tt.matKhau);
        return db.update("ThuThu",values,"maTT=?",new String[]{String.valueOf(tt.maTT)});
    }

    public int delete(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE maTT=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }

        int checkTT = db.delete("ThuThu","maTT=?",new String[]{id});
        if (checkTT == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public ArrayList<ThuThu> getALLTT(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql,id);
        return list.get(0);
    }

    public ArrayList<ThuThu> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<ThuThu> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                ThuThu tt = new ThuThu();
                tt.setMaTT(cursor.getString(0));
                tt.setHoTen(cursor.getString(1));
                tt.setMatKhau(cursor.getString(2));
                arr.add(tt);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql,id,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }
}
