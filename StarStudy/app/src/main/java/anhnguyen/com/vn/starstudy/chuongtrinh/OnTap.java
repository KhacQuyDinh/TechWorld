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
import anhnguyen.com.vn.starstudy.RevisionKnowledge;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnTap extends Fragment {
    private static LinearLayout onTapMonToan;
    private static LinearLayout onTapMonLy;
    private static LinearLayout onTapMonHoa;

    public OnTap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((Main2Activity)getActivity()).getSupportActionBar().setTitle("Ôn tập");
        return inflater.inflate(R.layout.fragment_on_tap, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MonToan();
        MonLy();
        MonHoa();
    }

    private void MonToan() {
//        System.out.println("Mon Toan");
        onTapMonToan = (LinearLayout)getActivity().findViewById(R.id.ontap_montoan);
        onTapMonToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RevisionKnowledge.class);
                i.putExtra("KeyOnTap", MaMonHoc.TOANHOC);
                startActivity(i);
            }
        });
    }

    private void MonLy() {
//        System.out.println("Mon Ly");
        onTapMonLy = (LinearLayout)getActivity().findViewById(R.id.ontap_monly);
        onTapMonLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RevisionKnowledge.class);
                i.putExtra("KeyOnTap", MaMonHoc.VATLY);
                startActivity(i);
            }
        });
    }

    private void MonHoa() {
//        System.out.println("Mon Hoa");
        onTapMonHoa = (LinearLayout)getActivity().findViewById(R.id.ontap_monhoa);
        onTapMonHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RevisionKnowledge.class);
                i.putExtra("KeyOnTap", MaMonHoc.HOAHOC);
                startActivity(i);
            }
        });
    }
}
