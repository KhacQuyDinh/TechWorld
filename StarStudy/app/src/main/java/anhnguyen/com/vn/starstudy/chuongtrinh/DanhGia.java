package anhnguyen.com.vn.starstudy.chuongtrinh;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import anhnguyen.com.vn.starstudy.DanhGiaBangBieuDo;
import anhnguyen.com.vn.starstudy.Main2Activity;
import anhnguyen.com.vn.starstudy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhGia extends Fragment {

    private static int REQUEST_CODE = 5;

    public DanhGia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Main2Activity presentActivity = (Main2Activity)getActivity();
        presentActivity.getSupportActionBar().setTitle("Đánh Giá");
        return inflater.inflate(R.layout.fragment_danh_gia, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = new Intent(getActivity(), DanhGiaBangBieuDo.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "Bạn vui lòng nhấn menu (ba dấu gạch bên trái trên cùng) để tiếp tục",
                    Toast.LENGTH_LONG).show();
        }
    }


}
