package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import model_dictionary.Word;

/**
 * Created by Dell on 11/11/2016.
 */
public class DatabaseEV extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EV_DATABASE";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "EV_TABLE";
    private static final String ENGLISH = "ENGLISH";
    private static final String VIETNAMESE = "VIETNAMESE";

    public DatabaseEV(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "( " + ENGLISH + " TEXT, "
                + VIETNAMESE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
    }

    public boolean addNewWord(Word newWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENGLISH, newWord.getKey());
        values.put(VIETNAMESE, newWord.getContent());
        return (db.insert(TABLE_NAME, null, values) != -1);
    }

    public boolean updateOldWord(Word newWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENGLISH, newWord.getKey());
        values.put(VIETNAMESE, newWord.getContent());
        return (db.update(TABLE_NAME, values, ENGLISH  + " = ?", new String[] {newWord.getKey()}) != -1);
    }

    public boolean deleteOldWord(String keyOldWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(TABLE_NAME, ENGLISH + " = ?", new String[] {keyOldWord}) != -1);
    }

    public HashMap<String, String> getListEV() {
        HashMap<String, String> tmpListWord = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                tmpListWord.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return  tmpListWord;
    }
}
