package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;

/**
 * Created by Dell on 11/11/2016.
 */
public class DatabaseHistoryVE extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HISTORY_VE_DATABASE";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "HISTORY_VE_TABLE";
    private static final String VIETNAMESE = "VIETNAMESE";

    public DatabaseHistoryVE(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "( " + VIETNAMESE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
    }

    public boolean addNewWordToHisToryVE(String VietnameseWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VIETNAMESE, VietnameseWord);
        return (db.insert(TABLE_NAME, null, values) != -1);
    }

    public boolean deleteHistoryVE() {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(TABLE_NAME, null, null) != 1);
    }

    public boolean deleteAWordOfHistoryVE(String keyWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(TABLE_NAME, VIETNAMESE+" = ?", new String[] {keyWord}) != 1);
    }

    public HashSet<String> getListHistoryVE() {
        HashSet<String> listEV = new HashSet<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                listEV.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return  listEV;
    }
}
