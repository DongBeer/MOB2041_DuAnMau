package dongnvph30597.fpoly.ass_demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import dongnvph30597.fpoly.ass_demo.R;

public class DB_ThuVien extends SQLiteOpenHelper {
    public static final String DB = "DB_QLTV";
    public static final int Version = 1;
    public DB_ThuVien(@Nullable Context context) {
        super(context, DB, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableThuThu = "create table ThuThu (" +
                "maTT text PRIMARY KEY, " +
                "hoTen text NOT NULL, " +
                "matKhau text NOT NULL)";
        db.execSQL(createTableThuThu);

        String createTableThanhVien = "create table ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen text NOT NULL, " +
                "soTK INTEGER NOT NULL,"+
                "namSinh text NOT NULL)";
        db.execSQL(createTableThanhVien);

        String createTableLoaiSach = "create table LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai text NOT NULL, " +
                "nhaSX text NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String createTableSach = "create table Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach text NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "soluong INTEGER NOT NULL," +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        String createTablePhieuMuon = "create table PhieuMuon (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT text REFERENCES ThuThu(maTT), " +
                "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngay DATE NOT NULL, " +
                "traSach INTEGER NOT NULL )";
        db.execSQL(createTablePhieuMuon);

//        String addloaiSach = "INSERT INTO LoaiSach (maLoai, tenLoai, nhaSX) VALUES "
//                + "(1, 'Truyện Tranh', 'Kim Đồng'), "
//                + "(2, 'Sách giáo khoa', 'Trí tuệ'), "
//                + "(3, 'Sách khoa học', 'Khoa học'), "
//                + "(4, 'Truyện Cười ', 'NXB1'), "
//                + "(5, 'Tiểu thuyết', 'NXB2')";
//
//        db.execSQL(addloaiSach);
//
//        String addBook = "INSERT INTO Sach (maSach, tenSach,giaThue, soluongTrangsach, maLoai) VALUES "
//                + "(1, 'Doremon',20000,300, 1), "
//                + "(2, 'Conan', 20000,200, 1), "
//                + "(3, 'Tiếng Việt 1', 20000,150, 2), "
//                + "(4, 'Tiếng Anh ',20000,150, 2), "
//                + "(5, 'Tiếng Pháp',20000,350, 2)";
//        db.execSQL(addBook);
//
//        String addTV = "INSERT INTO ThanhVien (maTV, hoTen,tienTV, namSinh) VALUES "
//                + "(1, 'Nguyễn Đông',50000,2000), "
//                + "(2, 'Nguyễn Khải', 20000,2001), "
//                + "(3, 'Trương Đức', 20000,2001), "
//                + "(4, 'Thanh Sơn ',50000,1999), "
//                + "(5, 'Duy Tiến',40000,2003)";
//        db.execSQL(addTV);
//
//        String addPM = "INSERT INTO PhieuMuon (maPM, maTT,maTV, maSach, tienThue, ngay, traSach) VALUES "
//                + "(1, 'admin',1,1,20000,'2023-06-15',0), "
//                + "(2, 'admin',2,3,20000,'2023-06-17',0), "
//                + "(3, 'admin',3,4,20000,'2023-06-18',0), "
//                + "(4, 'admin',3,2,20000,'2023-06-20',0), "
//                + "(5, 'admin',5,5,20000,'2023-06-21',1)";
//        db.execSQL(addPM);
//
//        String addTT = "INSERT INTO ThuThu (maTT, hoTen, matKhau) VALUES "
//                + "('admin', 'ADMIN', 'admin'), "
//                + "('tt2', 'Hà Huân', 'tt2'), "
//                + "('tt3', 'DongBeer', 'tt3'), "
//                + "('tt4', 'Mắm', 'tt4'), "
//                + "('tt5', 'Bông', 'tt5')";
//
//        db.execSQL(addTT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);

        onCreate(db);
    }
}
