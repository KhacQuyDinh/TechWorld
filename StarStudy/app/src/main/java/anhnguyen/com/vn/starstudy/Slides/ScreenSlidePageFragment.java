package anhnguyen.com.vn.starstudy.Slides;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.StringTokenizer;

import anhnguyen.com.vn.starstudy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenSlidePageFragment extends Fragment {
    private TextView tvCau;
    private TextView tvCauHoi;
    private TextView tvLoiGiai;
    private RadioButton rdA;
    private RadioButton rdB;
    private RadioButton rdC;
    private RadioButton rdD;
    private RadioGroup rdgroup;
    private static String[] deThi;
    private static String[] dsCauTraLoi;
    private static String[] dsDapAnVaHuongDan;
    private int mPageNumber; //Vi tri của trang hien tai;
    public static String ARG_PAGE = "page";
    public static String ARG_CHECK_ANSWER = "checkanswer";
    private int checkAnswer;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        System.out.println("Run onActivity Created............................");
        ganDeThiVaoViewPager(deThi);
        if (checkAnswer != 0) {
            rdA.setClickable(false);
            rdB.setClickable(false);
            rdC.setClickable(false);
            rdD.setClickable(false);
            getCheckAnswer(dsDapAnVaHuongDan[mPageNumber]);
        }
    }

    private void ganDeThiVaoViewPager(final String[] deThi) {
//        System.out.println("Run ganDeThiVaoViewPager...........................");
        StringTokenizer duLieu;
        duLieu = new StringTokenizer(deThi[mPageNumber], "|");
        try {
            duLieu.nextToken();//bo di id
            tvCau.setText("Câu hỏi số " + (mPageNumber + 1) + ":");
            tvCauHoi.setText(duLieu.nextToken());
            rdA.setText(duLieu.nextToken());
            rdB.setText(duLieu.nextToken());
            rdC.setText(duLieu.nextToken());
            rdD.setText(duLieu.nextToken());

            rdgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
//                    System.out.println("Run change Radio..............");
                    dsCauTraLoi[mPageNumber] = getChoiceFromID(id);
                }
            });
        } catch (Exception e) {
            Log.e("Init", "Error....................");
        }
    }

    private String getChoiceFromID(int id) {
        switch (id) {
            case R.id.rdA:
                return "A";
            case R.id.rdB:
                return "B";
            case R.id.rdC:
                return "C";
            case R.id.rdD:
                return "D";
            default:
                return "";
        }
    }

    private void getCheckAnswer(String dapAn_huongDan) {
        char dapAn = dapAn_huongDan.charAt(0);
        switch (dapAn) {
            case 'A':
                rdA.setBackgroundColor(Color.RED);
                break;
            case 'B':
                rdB.setBackgroundColor(Color.RED);
                break;
            case 'C':
                rdC.setBackgroundColor(Color.RED);
                break;
            case 'D':
                rdD.setBackgroundColor(Color.RED);
                break;
            default:
                break;
        }
        tvLoiGiai.setText("Đáp án: " + dapAn_huongDan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        System.out.println("Run oncreate View............................");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        tvCau = (TextView) rootView.findViewById(R.id.tvCau);
        tvCauHoi = (TextView) rootView.findViewById(R.id.tvCauHoi);
        tvLoiGiai = (TextView) rootView.findViewById(R.id.tvLoiGiai);
        rdA = (RadioButton) rootView.findViewById(R.id.rdA);
        rdB = (RadioButton) rootView.findViewById(R.id.rdB);
        rdC = (RadioButton) rootView.findViewById(R.id.rdC);
        rdD = (RadioButton) rootView.findViewById(R.id.rdD);
        rdgroup = (RadioGroup) rootView.findViewById(R.id.rdgroup);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        System.out.println("Run oncreate...........................");
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAnswer = getArguments().getInt(ARG_CHECK_ANSWER);
//        if (confirm == 0) {
        ScreenSlideActivity screenSlideActivity = new ScreenSlideActivity();
            deThi = screenSlideActivity.getDeThi();
            dsDapAnVaHuongDan = screenSlideActivity.getdsDapAnVaHuongDan();
            dsCauTraLoi = screenSlideActivity.getdsCauTraLoi();
//            confirm = 1;
//        }
//        ScreenSlideActivity screenSlideActivity = new ScreenSlideActivity();
//        mPageNumber = getArguments().getInt();

    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer) {
//        System.out.println("Run ScreenSlidePageFragment...........................");
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CHECK_ANSWER, checkAnswer);
        arguments.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(arguments);

        return fragment;
    }
}
