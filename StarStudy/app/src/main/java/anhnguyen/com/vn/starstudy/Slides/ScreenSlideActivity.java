package anhnguyen.com.vn.starstudy.Slides;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import anhnguyen.com.vn.starstudy.CSDL.FactoryCSDLMonHoc;
import anhnguyen.com.vn.starstudy.MaMonHoc;
import anhnguyen.com.vn.starstudy.MonHocHienTai;
import anhnguyen.com.vn.starstudy.R;

public class ScreenSlideActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 50;
//    private static int NUM_PAGES = 24;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private Button btnListExam;
    private Button btnKetQua;

    private TextView tvClock;
    public static long TIMEINFUTURE = 90*60*1000;
    public static long COUNTDOWNINTERVAL = 1000;
    private CounterClass timer;

    private int checkAnswer = 0;

    private static String[] deThi;
    private static String[] dsDapAnVaHuongDan;
    private static String[] dsCauTraLoi;

    private static int REQUEST_RESULT = 1000;
    private static MonHocHienTai monHocHienTai = new MonHocHienTai();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        layCSDLDeThiCacMon(); //Sản xuất và lưu trữ csdl đề thi tương ứng môn đã chọn và cài đặt thời gian.

        btnListExam = (Button) findViewById(R.id.btnListExam);
        btnListExam.setOnClickListener(this);
        tvClock = (TextView) findViewById(R.id.tvClock);
//        tcClock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        btnKetQua = (Button) findViewById(R.id.btnKetQua);
        btnKetQua.setOnClickListener(this);
    }

    private void layCSDLDeThiCacMon() {
//        System.out.println("Run layCSDLDeThiCacMon............................");
        FactoryCSDLMonHoc factoryCSDLMonHoc = new FactoryCSDLMonHoc(this);
        int key = getIntent().getIntExtra("KeyTest", -100);
        switch (key) {
            case MaMonHoc.TOANHOC:
                deThi = factoryCSDLMonHoc.layDeThi(MaMonHoc.TOANHOC, NUM_PAGES); //NUM_PAGES == NUM_QUESTION
                dsDapAnVaHuongDan = factoryCSDLMonHoc.layDapAn(MaMonHoc.TOANHOC, NUM_PAGES);
                monHocHienTai.setTenMonHoc(monHocHienTai.MONTOAN);
                break;
            case MaMonHoc.VATLY:
                deThi = factoryCSDLMonHoc.layDeThi(MaMonHoc.VATLY, NUM_PAGES); //NUM_PAGES == NUM_QUESTION
                dsDapAnVaHuongDan = factoryCSDLMonHoc.layDapAn(MaMonHoc.VATLY, NUM_PAGES);
                monHocHienTai.setTenMonHoc(monHocHienTai.MONLY);
                break;
            case MaMonHoc.HOAHOC:
                deThi = factoryCSDLMonHoc.layDeThi(MaMonHoc.HOAHOC, NUM_PAGES); //NUM_PAGES == NUM_QUESTION
                dsDapAnVaHuongDan = factoryCSDLMonHoc.layDapAn(MaMonHoc.HOAHOC, NUM_PAGES);
                monHocHienTai.setTenMonHoc(monHocHienTai.MONHOA);
                break;
            default:
                Log.e("ERROR.........", "ERROR.............."); break;
        }
        dsCauTraLoi = new String[NUM_PAGES];
        timer = new CounterClass(TIMEINFUTURE, COUNTDOWNINTERVAL);
        timer.start();//Bắt đầu tính giờ.
    }

    public String[] getDeThi() {
        return deThi;
    }

    public String[] getdsDapAnVaHuongDan() {return dsDapAnVaHuongDan;}

    public String[] getdsCauTraLoi() {return dsCauTraLoi;}

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
//            System.out.println("Run onBackPressed");
            finish();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnListExam:
                checkListExam(); break;
            case R.id.btnKetQua:
                xemKetQua(); break;
            default: break;
        }
    }

    private void checkListExam() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_check_list_exam);
//        String[] dapAn = locDapAn(deThi);
        CheckAnswerAdapter checkAnswerAdapter = new CheckAnswerAdapter(dsCauTraLoi, this);
        GridView gvCauHoi = (GridView) dialog.findViewById(R.id.gvListExam);
        gvCauHoi.setAdapter(checkAnswerAdapter);
        gvCauHoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mPager.setCurrentItem(position); //Chuyển tới trang cần chuyển.
                dialog.dismiss();
            }
        });
        Button btnQuayLai = (Button) dialog.findViewById(R.id.btnQuayLai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnNopBai = (Button) dialog.findViewById(R.id.btnNopBai);
        btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                result();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void xemKetQua() {
        Intent intent = new Intent(ScreenSlideActivity.this, TestDoneActivity.class);
        for (int i = 0; i < NUM_PAGES; i++) {
            if (dsCauTraLoi[i] == null) {
                dsCauTraLoi[i] = "";
            }
        }
        intent.putExtra(TestDoneActivity.keyDapAn, dsDapAnVaHuongDan);
        intent.putExtra(TestDoneActivity.keyCauTraLoi, dsCauTraLoi);
        intent.putExtra(MonHocHienTai.TUKHOA, monHocHienTai.getTenMonHoc());
        startActivityForResult(intent, REQUEST_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("Run ActivityResult for TestDone.");
        if (requestCode == REQUEST_RESULT && resultCode == Activity.RESULT_OK) {
//            System.out.println("Run RequestCode " + getIntent().getIntExtra(TestDoneActivity.KEY_THOAT, 0));
                if (data.getIntExtra(TestDoneActivity.KEY_THOAT, 0) == TestDoneActivity.CONFIRM_THOAT) {
//                System.out.println("Run finish in ScreenSlideActivity");
                finish();
            };
        }
    }

    public void result() {
        checkAnswer = 1;
        btnKetQua.setVisibility(View.VISIBLE);
        btnListExam.setVisibility(View.GONE);
        if (mPager.getCurrentItem() >= 4) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        } else  {
            mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position, checkAnswer);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    private class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * Xác định thời gian hiện tại còn lại.
         * @param presentTime thời gian hiện tại.
         */
        @Override
        public void onTick(long presentTime) {
            String countTimer = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(presentTime),
                    TimeUnit.MILLISECONDS.toSeconds(presentTime)
                    - TimeUnit.MILLISECONDS.toMinutes(presentTime)*60);
            tvClock.setText(countTimer);
        }

        /**
         * Khi kết thúc thời gian đếm ngược.
         */
        @Override
        public void onFinish() {
            tvClock.setText("00:00");
            result();
        }
    }
}
