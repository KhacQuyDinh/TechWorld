package anhnguyen.com.vn.starstudy.chuongtrinh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anhnguyen.com.vn.starstudy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class monhoc extends Fragment {


    public monhoc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monhoc, container, false);
    }

}
