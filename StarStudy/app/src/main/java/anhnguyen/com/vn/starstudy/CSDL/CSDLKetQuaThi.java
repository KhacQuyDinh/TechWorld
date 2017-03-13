package anhnguyen.com.vn.starstudy.CSDL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by TECHWORLD on 02/03/2017.
 */
public class CSDLKetQuaThi extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KetQua.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "KetQua";
    private static final String TONGDIEM = "TongDiem";
    private static final String MONHOC = "MonHoc";
    private static final String THOIGIAN = "ThoiGian";

    public CSDLKetQuaThi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + TONGDIEM + " TEXT, " + MONHOC + " TEXT, " + THOIGIAN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_NAME + " IF EXIST");
        onCreate(db);
    }

    public ArrayList<Entry> getDanhSachKetQua(String monHoc) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Entry> danhSachKetQuaThi = new ArrayList<>();
        Cursor cursor =
                db.query(TABLE_NAME, new String[]{TONGDIEM}, MONHOC + " =? ", new String[]{monHoc},
                        null, null, THOIGIAN + " ASC");

        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                danhSachKetQuaThi.add(
                        new Entry(Float.parseFloat(cursor.getString(0).replace(",", ".")), index++));
                System.out.println("DATABASE KET QUA " + cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return danhSachKetQuaThi;
    }

    public ArrayList<String> getDanhSachNgayThi(String monHoc) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> danhSachNgayThi = new ArrayList<>();
        Cursor cursor =
                db.rawQuery("SELECT " + THOIGIAN + " FROM " + TABLE_NAME + " WHERE " + MONHOC + " = ? ",
                        new String[]{monHoc});

        if (cursor.moveToFirst()) {
            do {
                danhSachNgayThi.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return danhSachNgayThi;
    }

    public void themVaoKetQuaThi(String ketQua, String monHoc, String thoiGian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TONGDIEM, ketQua);
        contentValues.put(MONHOC, monHoc);
        contentValues.put(THOIGIAN, thoiGian);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void xoaKetQuaLuuTru(String monHoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, MONHOC + "=?", new String[]{monHoc});
    }

    public void xoaHetKetQuaLuuTru() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
