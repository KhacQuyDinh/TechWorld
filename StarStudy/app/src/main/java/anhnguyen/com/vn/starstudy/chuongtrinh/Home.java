package anhnguyen.com.vn.starstudy.chuongtrinh;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import anhnguyen.com.vn.starstudy.Main2Activity;
import anhnguyen.com.vn.starstudy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    EditText edtTen;
    Spinner sGioiTinh;
    EditText edtThanhPho;
    Button btnSave;
    String gioiTinh;
    String tenedt ;
    String thanhPhoedt;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        anhXa();
        cauHinhSGioiTinh();
        luuDuLieu();
        super.onActivityCreated(savedInstanceState);
    }

    private void luuDuLieu() {
        final Main2Activity main2Activity = (Main2Activity) getActivity();

        sGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gioiTinh = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenedt =edtTen.getText().toString();
                thanhPhoedt = edtThanhPho.getText().toString();
                main2Activity.setAvt(gioiTinh);
                main2Activity.setThongTin(tenedt,thanhPhoedt);
            }
        });


    }

    private void cauHinhSGioiTinh() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.arrGioiTinh));
        sGioiTinh.setAdapter(arrayAdapter);
    }


    private void anhXa() {
        edtTen = (EditText)getActivity().findViewById(R.id.edtTen);
        sGioiTinh = (Spinner)getActivity().findViewById(R.id.spinerGioiTinh);
        edtThanhPho = (EditText)getActivity().findViewById(R.id.edtThanhPho);
        btnSave =  (Button)getActivity().findViewById(R.id.save);
    }

}
