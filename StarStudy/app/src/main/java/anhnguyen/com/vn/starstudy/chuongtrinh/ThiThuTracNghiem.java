package anhnguyen.com.vn.starstudy.chuongtrinh;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import anhnguyen.com.vn.starstudy.MaMonHoc;
import anhnguyen.com.vn.starstudy.Main2Activity;
import anhnguyen.com.vn.starstudy.R;
import anhnguyen.com.vn.starstudy.Slides.ScreenSlideActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThiThuTracNghiem extends Fragment {

    private static LinearLayout thiThuMonToan;
    private static LinearLayout thiThuMonLy;
    private static LinearLayout thiThuMonHoa;

    public ThiThuTracNghiem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((Main2Activity)getActivity()).getSupportActionBar().setTitle("Thi Thử Trắc Nghiệm");
        return inflater.inflate(R.layout.fragment_thi_thu_trac_nghiem, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MonToan();
        MonLy();
        MonHoa();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        MonToan();
//        MonLy();
//        MonHoa();
//    }

    private void MonToan() {
//        System.out.println("Mon Toan");
        thiThuMonToan = (LinearLayout)getActivity().findViewById(R.id.thithu_monToan);
        thiThuMonToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScreenSlideActivity.class);
                i.putExtra("KeyTest", MaMonHoc.TOANHOC);
                startActivity(i);
            }
        });
    }

    private void MonLy() {
//        System.out.println("Mon Ly");
        thiThuMonLy = (LinearLayout)getActivity().findViewById(R.id.thithu_monLy);
        thiThuMonLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScreenSlideActivity.class);
                i.putExtra("KeyTest", MaMonHoc.VATLY);
                startActivity(i);
            }
        });
    }

    private void MonHoa() {
//        System.out.println("Mon Hoa");
        thiThuMonHoa = (LinearLayout)getActivity().findViewById(R.id.thithu_monHoa);
        thiThuMonHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScreenSlideActivity.class);
                i.putExtra("KeyTest", MaMonHoc.HOAHOC);
                startActivity(i);
            }
        });
    }
}
