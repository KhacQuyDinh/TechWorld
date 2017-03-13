package anhnguyen.com.vn.starstudy.Slides;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import anhnguyen.com.vn.starstudy.CSDL.CSDLKetQuaThi;
import anhnguyen.com.vn.starstudy.MonHocHienTai;
import anhnguyen.com.vn.starstudy.R;

public class TestDoneActivity extends AppCompatActivity {

    private String[] dapAn;
    private String[] cauTraLoi;
    public static String keyDapAn = "keydapan";
    public static String keyCauTraLoi = "keycautraloi";
    private static int socaudung = 0;
    private static int socausai = 1;
    private static int socauchuatraloi = 2;
    private TextView tvSoCauDung, tvSoCauSai, tvSoCauTrong, tvSoTongDiem, tvVietLoiKhuyen;
    private int soLuongCauHoi;
    private static int diemToiDa = 10;
    private static int diemDat = 8;
    private static boolean confirmToSave = false;

    public static String KEY_THOAT = "keythoat";
    public static int CONFIRM_THOAT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KẾT QUẢ");

        tvSoCauDung = (TextView) findViewById(R.id.tvSoCauDung);
        tvSoCauSai = (TextView) findViewById(R.id.tvSoCauSai);
        tvSoCauTrong = (TextView) findViewById(R.id.tvSoCauChuaTraLoi);
        tvSoTongDiem = (TextView) findViewById(R.id.tvSoTongDiem);
        tvVietLoiKhuyen = (TextView) findViewById(R.id.tvVietLoiKhuyen);

        Intent intent = getIntent();
        dapAn = intent.getStringArrayExtra(keyDapAn);
        cauTraLoi = intent.getStringArrayExtra(keyCauTraLoi);

        String[] arrKetQuaXuLy = new String[3];
        xuLyDuLieu(cauTraLoi, dapAn, arrKetQuaXuLy);

        tvSoCauDung.setText(arrKetQuaXuLy[socaudung]);
        tvSoCauSai.setText(arrKetQuaXuLy[socausai]);
        tvSoCauTrong.setText(arrKetQuaXuLy[socauchuatraloi]);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.soLuongCauHoi = cauTraLoi.length;
        String tongDiem = decimalFormat.format((float)diemToiDa/soLuongCauHoi*Integer.parseInt(arrKetQuaXuLy[socaudung]));
        tvSoTongDiem.setText(tongDiem);

        tvVietLoiKhuyen.setText(layLoiKhuyenDuaTren(arrKetQuaXuLy));

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String monHoc = intent.getStringExtra(MonHocHienTai.TUKHOA);
        if (!confirmToSave) {
            luuKetQuaThi(tongDiem, monHoc, date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.thongbaoketqua, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemThoat:
                thoat();
                break;
            case android.R.id.home:
                finish();
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void thoat() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("THÔNG BÁO");
        builder.setMessage("Bạn có muốn thoát thi trắc nghiệm?");
        builder.setIcon(R.drawable.exit);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmToSave = false; // Vì biến static sẽ không khởi dônng lại.
                Intent intent = getIntent();
                intent.putExtra(KEY_THOAT, CONFIRM_THOAT);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();

    }

    private void xuLyDuLieu(String[] cauTraLoi, String[]dapAn, String[] arrKetQua) {
        int soCauDung = 0;
        int soCauSai = 0;
        int soCauChuaTraLoi = 0;
        int length = cauTraLoi.length;
        for (int i = 0; i < length; i++) {
            if (cauTraLoi[i].equals("")) {
                soCauChuaTraLoi += 1;
            } else if (cauTraLoi[i].equals(dapAn[i].charAt(0)+"")) {
                soCauDung += 1;
            } else {
                soCauSai += 1;
            }
        }
        arrKetQua[socaudung] = soCauDung+"";
        arrKetQua[socausai] = soCauSai+"";
        arrKetQua[socauchuatraloi] = soCauChuaTraLoi+"";
    }

    private void luuKetQuaThi(String tongDiem, String monHoc, String thoiGian) {
        new CSDLKetQuaThi(TestDoneActivity.this).themVaoKetQuaThi(tongDiem, monHoc, thoiGian);
        Toast.makeText(TestDoneActivity.this, "Đã tự động lưu trữ kết quả thi thành công", Toast.LENGTH_SHORT).show();
        confirmToSave = true;
    }

    private String layLoiKhuyenDuaTren(String[] arrKetQuaXuLy) {
        String loiNhanXet = "";
        int soCauDung = Integer.parseInt(arrKetQuaXuLy[socaudung]);
        int soCauSai = Integer.parseInt(arrKetQuaXuLy[socausai]);
        int soCauChuaTraLoi = Integer.parseInt(arrKetQuaXuLy[socauchuatraloi]);
        int soLuongCauCanDat = soLuongCauHoi*diemDat/diemToiDa;

        if (soCauDung < soLuongCauCanDat) {
            loiNhanXet += "Bài làm chưa đạt! Bạn cần cẩn thận hơn khi làm bài tập và nên trau dồi thêm kiến thức, " +
                    "nên chăm chỉ hơn.\n\n";
        } else if (soCauDung >= soLuongCauCanDat) {
            loiNhanXet += "Chúc mừng bạn, bài làm đã đạt yêu cầu! Hãy cố gắng để duy trì và phát triển kết quả.\n\n";
        }

        if (soCauSai >= soLuongCauHoi/2) {
            loiNhanXet += "Bạn còn sai khá nhiều câu. Bạn cần thật sự cẩn thận và chuyên tâm khi làm bài tập, cũng nên trau " +
                    "dồi thêm kiến thức cho vững vàng.\n\n";
        } else if (soCauSai > 0) {
            loiNhanXet += "Lượng câu trả lời sai tạm chấp nhận được nhưng cần cố gắng hơn để không sai câu nào.\n\n";
        }

        if (soCauChuaTraLoi >= soLuongCauHoi/2) {
            loiNhanXet += "Còn rất nhiều câu chưa trả lời. " +
                    "Bạn hãy coi trọng từng giây phút ôn luyện. Bạn cần thật sự chăm chỉ làm bài tập mới giỏi được.\n\n";
        } else if (soCauChuaTraLoi > 0) {
            loiNhanXet +=  "Còn một số câu bạn chưa trả lời, đừng nên lãng phí, chọn bừa cũng là một phương pháp hay.\n\n";
        }

        return loiNhanXet;
    }
}
