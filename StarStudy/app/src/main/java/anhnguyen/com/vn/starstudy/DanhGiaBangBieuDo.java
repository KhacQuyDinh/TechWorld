package anhnguyen.com.vn.starstudy;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import anhnguyen.com.vn.starstudy.CSDL.CSDLKetQuaThi;

public class DanhGiaBangBieuDo extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {
    private LineChart mChart;
    private MonHocHienTai monHocHienTai = new MonHocHienTai(MonHocHienTai.MONTOAN);
    private CSDLKetQuaThi csdlKetQuaThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia_bang_bieu_do);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBieuDo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đánh Giá");

        csdlKetQuaThi = new CSDLKetQuaThi(DanhGiaBangBieuDo.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mChart = (LineChart) findViewById(R.id.linechart);

        setData(monHocHienTai.getTenMonHoc());
        mChart.setDescription("Thống kê kết quả thi trắc nghiệm " + monHocHienTai.getTenMonHoc());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bieudo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
            case R.id.item_monHoa:
                setData(MonHocHienTai.MONHOA);
                monHocHienTai.setTenMonHoc(MonHocHienTai.MONHOA);
                break;
            case R.id.item_monLy:
                setData(MonHocHienTai.MONLY);
                monHocHienTai.setTenMonHoc(MonHocHienTai.MONLY);
                break;
            case R.id.item_monToan:
                setData(MonHocHienTai.MONTOAN);
                monHocHienTai.setTenMonHoc(MonHocHienTai.MONTOAN);
                break;
            case R.id.item_delete_chart:
                xoaBieuDo(monHocHienTai.getTenMonHoc()); break;
            case R.id.item_delete_all_chart:
                xoaHetBieuDo(); break;
            default:
                break;
        }
        mChart.setDescription("Thống kê kết quả thi trắc nghiệm " + monHocHienTai.getTenMonHoc());
        return super.onOptionsItemSelected(item);
    }

    private void xoaHetBieuDo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhGiaBangBieuDo.this);
        builder.setTitle("THÔNG BÁO");
        builder.setMessage("Bạn có chắc muốn xóa toàn bộ dữ liệu kết quả tất cả các môn học Toán, Lý, Hóa?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                csdlKetQuaThi.xoaHetKetQuaLuuTru();
                setData(monHocHienTai.getTenMonHoc());
                Toast.makeText(DanhGiaBangBieuDo.this,
                        "Toàn bộ biểu đồ dữ liệu các môn học đã bị xóa!", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    @Override
    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {

    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {

    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void xoaBieuDo(final String monHoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhGiaBangBieuDo.this);
        builder.setTitle("THÔNG BÁO");
        builder.setMessage("Bạn có chắc muốn xóa toàn bộ dữ liệu kết quả " + monHoc + "?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                csdlKetQuaThi.xoaKetQuaLuuTru(monHoc);
                setData(monHoc);
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void setData(String monHoc) {
        LineDataSet chart;

        //Tạo 2 trục tọa độ từ cơ sở dữ liệu vào ArrayList.
        ArrayList<String> xVals = csdlKetQuaThi.getDanhSachNgayThi(monHoc);
        ArrayList<Entry> yVals = csdlKetQuaThi.getDanhSachKetQua(monHoc);
        chart = new LineDataSet(yVals, "Kết Quả Thi " + monHoc);

//        set1.setFillAlpha(110);
//        set1.setFillColor(Color.BLUE);

        //tạo listDataSet
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(custom(chart));

        // Tạo đối tượng LineData
        LineData data = new LineData(xVals, dataSets);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);

        // set data
        mChart.setData(data);

        customChart(mChart);
    }

    private LineDataSet custom(LineDataSet set) {
//         set the line to be drawn like this "- - - - - -"
//        set.enableDashedLine(10f, 5f, 0f);
//        set.enableDashedHighlightLine(10f, 5f, 0f);
        set.setColor(Color.RED);
        set.setCircleColor(Color.YELLOW);
        set.setLineWidth(2f);
        set.setCircleRadius(5f);
        set.setDrawCircleHole(true);
        set.setValueTextSize(10f);
        set.setDrawFilled(true);

        return set;
    }

    private void customChart(LineChart mChart) {
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.animateX(10, Easing.EasingOption.EaseInOutQuart);
    }
}
