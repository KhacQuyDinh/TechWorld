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

public class CSDLToanHoc extends SQLiteOpenHelper {
    public String DB_PATH = "data/data/anhnguyen.com.vn.starstudy/";
    private static final String DATABASE_NAME = "ToanHoc.sqlite";
//    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ToanHoc";
    private SQLiteDatabase csdlToanHoc;
    private Context context;

    public CSDLToanHoc(Context context) {
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

    public SQLiteDatabase getCsdlToanHoc()
    {
        return csdlToanHoc;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
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

        OutputStream myOutput = new FileOutputStream("data/data/anhnguyen.com.vn.starstudy/ToanHoc.sqlite");

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
        csdlToanHoc = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close()
    {
        csdlToanHoc.close();
        super.close();
    }

    public static class executeCSDLToanHoc extends CSDLMonHoc {
        private Context context;
        private int[] dsViTriCauHoi;
        private SQLiteDatabase db;

        public executeCSDLToanHoc(Context context) {
            this.context = context;
        }

        public String[] layDeThi(int number_Question) {
            CSDLToanHoc csdlToanHoc = new CSDLToanHoc(context);
            csdlToanHoc.open();
            this.db = csdlToanHoc.getCsdlToanHoc();

            String[] dsDeThi = new String[number_Question];
            String tmpDuLieu = "";

            this.dsViTriCauHoi = new int[number_Question];
            int viTriMoiCauHoi = 0;
            int soLuongThucCauHoi = 0;
            int soLuongCauHoi_CSDL = 52;
            boolean isLapCauHoi = false;
            Cursor cursor;

            //Phải tìm cho đủ number_Question câu hỏi khác nhau từ cơ sở dữ liệu có soLuongCauHoi_CSLD câu hỏi.
            while (soLuongThucCauHoi < number_Question) {
                viTriMoiCauHoi = new Random().nextInt(soLuongCauHoi_CSDL);

                //Xác định vị trí câu hỏi mới đã tồn tại hay chưa trong danh sách vị trí câu hỏi
                //Nếu tồn tại rồi thì phải random tìm vị trí mới.
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

                //cursor index out of bound exception là do cơ sở dữ liệu thiếu element có index đó.
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
