package dongnvph30597.fpoly.ass_demo.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.PhieuMuon;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;
import dongnvph30597.fpoly.ass_demo.model.TimSach;
import dongnvph30597.fpoly.ass_demo.model.Top;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        DB_ThuVien dbHelper = new DB_ThuVien(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Top> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop,null);
        while (c.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("maSach")));
            top.tenSach = sach.tenSach;
            top.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public ArrayList<Sach> getSach( int soluong){
        String sqlTim = "SELECT * FROM Sach WHERE soluong <= ?";
        ArrayList<Sach> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlTim,new String[]{String.valueOf(soluong)});
        while (cursor.moveToNext()){
           Sach sach = new Sach();
            sach.setMaSach(cursor.getInt(0));
            sach.setTenSach(cursor.getString(1));
            sach.setGiaThue(cursor.getInt(2));
            sach.setSoluong(cursor.getInt(3));
            sach.setMaLoai(cursor.getInt(4));
            arr.add(sach);
        }
        return arr;
    }

    public ArrayList<PhieuMuon> getPM(String tenTV){
        String sqlPM = "SELECT * FROM PhieuMuon " +
                "JOIN ThanhVien ON PhieuMuon.maTV = ThanhVien.maTV " +
                "WHERE ThanhVien.hoTen LIKE '%" + tenTV + "%'";
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlPM,null);
        while (cursor.moveToNext()){
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPm(cursor.getInt(0));
            pm.setMaTT(cursor.getString(1));
            pm.setMaTV(cursor.getInt(2));
            pm.setMaSach(cursor.getInt(3));
            pm.setTienThue(cursor.getInt(4));
            pm.setNgay(Date.valueOf(cursor.getString(5)));
            pm.setTraSach(cursor.getInt(6));
            arr.add(pm);
        }
        return arr;
    }
    public ArrayList<PhieuMuon> getPMtenSach(String tenSach){
        String sqlPM = "SELECT * FROM PhieuMuon " +
                "JOIN Sach ON PhieuMuon.maSach = Sach.maSach " +
                "WHERE Sach.tenSach LIKE '%" + tenSach + "%'";
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlPM,null);
        while (cursor.moveToNext()){
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPm(cursor.getInt(0));
            pm.setMaTT(cursor.getString(1));
            pm.setMaTV(cursor.getInt(2));
            pm.setMaSach(cursor.getInt(3));
            pm.setTienThue(cursor.getInt(4));
            pm.setNgay(Date.valueOf(cursor.getString(5)));
            pm.setTraSach(cursor.getInt(6));

            arr.add(pm);
        }
        return arr;
    }

    public ArrayList<PhieuMuon> getPMchuatra(){
        String sqlPMtrangthai = "SELECT * FROM PhieuMuon WHERE traSach = 0 ORDER BY ngay ASC";   //DESC
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlPMtrangthai,null);
        while (cursor.moveToNext()){
            PhieuMuon pmm = new PhieuMuon();
            pmm.setMaPm(cursor.getInt(0));
            pmm.setMaTT(cursor.getString(1));
            pmm.setMaTV(cursor.getInt(2));
            pmm.setMaSach(cursor.getInt(3));
            pmm.setTienThue(cursor.getInt(4));
            pmm.setNgay(Date.valueOf(cursor.getString(5)));
            pmm.setTraSach(cursor.getInt(6));

            arr.add(pmm);
        }
        return arr;
    }

    public ArrayList<PhieuMuon> timPMtheoSachdatra(){
        String query = "SELECT * FROM PhieuMuon " +
                "JOIN Sach ON PhieuMuon.maSach = Sach.maSach " +
                "WHERE Sach.tenSach LIKE '%Tiếng Anh%' " +
                "AND PhieuMuon.traSach = 1 " +
                "AND PhieuMuon.ngay > '2023-06-15'";
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            PhieuMuon p = new PhieuMuon();
            p.setMaPm(cursor.getInt(0));
            p.setMaTT(cursor.getString(1));
            p.setMaTV(cursor.getInt(2));
            p.setMaSach(cursor.getInt(3));
            p.setTienThue(cursor.getInt(4));
            p.setNgay(Date.valueOf(cursor.getString(5)));
            p.setTraSach(cursor.getInt(6));

            arr.add(p);

        }
        return arr;
    }
    public ArrayList<ThanhVien> getThanhvientheonam(int nam){
        String sqlTV = "SELECT * FROM ThanhVien WHERE namSinh = ?";
        ArrayList<ThanhVien> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlTV, new String[]{String.valueOf(nam)});
        while (cursor.moveToNext()){
            ThanhVien tv = new ThanhVien();
            tv.setMaTV(cursor.getInt(0));
            tv.setHoTen(cursor.getString(1));
            tv.setSoTK(cursor.getInt(2));
            tv.setNamSinh(cursor.getString(3));
            arr.add(tv);
        }
        return arr;
    }
}
