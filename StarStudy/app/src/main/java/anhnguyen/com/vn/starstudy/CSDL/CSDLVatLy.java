package anhnguyen.com.vn.starstudy.CSDL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by AnhNguyenAndroid on 2/22/2017.
 */

public class CSDLVatLy extends SQLiteOpenHelper {
    public String DB_PATH = "data/data/anhnguyen.com.vn.starstudy/";
    private static final String DATABASE_NAME = "VatLy.sqlite";
//    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "VatLy";
    private SQLiteDatabase csdlVatLy;
    private Context context;

    public CSDLVatLy(Context context) {
        super(context, DATABASE_NAME, null, 1);

        this.context = context;
        boolean dbexist = checkdatabase();

        if(dbexist)
        {
        }
        else
        {
            System.out.println("Database doesn't exist!");

            createDatabse();
        }
    }

    public void createDatabse() {

        this.getReadableDatabase();

        try
        {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getCsdlVatLy()
    {
        return csdlVatLy;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private boolean checkdatabase() {

        boolean checkdb = false;

        try
        {
            String myPath = DB_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        }
        catch (SQLiteException e)
        {
            System.out.println("Databse doesn't exist!");
        }

        return checkdb;
    }


    private void copyDatabase() throws IOException {

//        AssetManager dirPath = context.getAssets();

        InputStream myinput = context.getAssets().open(DATABASE_NAME);

//        String outFileName = DB_PATH + DATABASE_NAME;

        OutputStream myOutput = new FileOutputStream("data/data/anhnguyen.com.vn.starstudy/VatLy.sqlite");

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myinput.close();
    }

    public void open()
    {
        String myPath = DB_PATH + DATABASE_NAME;
        csdlVatLy = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close()
    {
        csdlVatLy.close();
        super.close();
    }

    public static class executeCSDLVatLy extends CSDLMonHoc {
        private Context context;
        private int[] dsViTriCauHoi;
        private SQLiteDatabase db;

        public executeCSDLVatLy(Context context) {
            this.context = context;
        }

        public String[] layDeThi(int number_Question) {
            CSDLVatLy csdlVatLy = new CSDLVatLy(context);
            csdlVatLy.open();
            this.db = csdlVatLy.getCsdlVatLy();

            String[] dsDeThi = new String[number_Question];
            String tmpDuLieu = "";

            this.dsViTriCauHoi = new int[number_Question];
            int viTriMoiCauHoi = 0;
            int soLuongThucCauHoi = 0;
            int soLuongCauHoi_CSDL = 50;
            boolean isLapCauHoi = false;
            Cursor cursor;

            while (soLuongThucCauHoi < number_Question) {
                viTriMoiCauHoi = new Random().nextInt(soLuongCauHoi_CSDL);
                for (int i = 0; i < soLuongThucCauHoi; i++) {
                    if (dsViTriCauHoi[i] == viTriMoiCauHoi) {
                        isLapCauHoi = true;
                        break;
                    }
                }
                if (isLapCauHoi) {
                    isLapCauHoi = false;
                    continue;
                }
                dsViTriCauHoi[soLuongThucCauHoi] = viTriMoiCauHoi;
                cursor = db.rawQuery("SELECT id,CauHoi,A,B,C,D FROM " + TABLE_NAME + " WHERE id = ?", new String[]{"" + viTriMoiCauHoi});
                cursor.moveToFirst();
                tmpDuLieu = cursor.getString(0) + "|"
                        + cursor.getString(1) + "|"
                        + cursor.getString(2) + "|"
                        + cursor.getString(3) + "|"
                        + cursor.getString(4) + "|"
                        + cursor.getString(5);
                dsDeThi[soLuongThucCauHoi] = tmpDuLieu;
                soLuongThucCauHoi += 1;
            }

            return dsDeThi;
        }

        public String[] layDapAn(int number_Question) {
            Cursor cursor;
            String[] dsDapAn = new String[number_Question];
            for (int i = 0; i < dsViTriCauHoi.length; i++) {
                cursor = db.rawQuery("SELECT DapAn FROM " + TABLE_NAME + " WHERE id = ?", new String[]{""+dsViTriCauHoi[i]});
                cursor.moveToFirst();
                dsDapAn[i] = cursor.getString(0);
            }
            return dsDapAn;
        }
    }
}
