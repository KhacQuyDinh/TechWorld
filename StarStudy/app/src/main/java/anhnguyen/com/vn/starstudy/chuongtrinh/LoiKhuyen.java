package anhnguyen.com.vn.starstudy.chuongtrinh;


import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import anhnguyen.com.vn.starstudy.Main2Activity;
import anhnguyen.com.vn.starstudy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoiKhuyen extends Fragment implements View.OnClickListener {

    private TextView tvTip1, tvTip2, tvTip3, tvTip4, tvTip5, tvTip0_link_group;
    private EditText edtLinkTuGo;
    private Button btnGoto;

    private static final int MAX_ELEMENT = 5;
    private static final String[] link_groups = new String[]{
            "http://tuyensinh247.com/gioi-thieu-r3.html",
            "http://thuvienvatly.com/forums/",
            "http://www.moon.vn/Home1/Default.aspx",
            "https://diendan.hocmai.vn/"
    };

    public LoiKhuyen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Main2Activity presentActivity = (Main2Activity) getActivity();
        presentActivity.getSupportActionBar().setTitle("Lời khuyên");
        return inflater.inflate(R.layout.fragment_loi_khuyen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        System.out.println("RUN CREATED_LOI_KHUYEN");
        super.onActivityCreated(savedInstanceState);

        edtLinkTuGo = (EditText) getActivity().findViewById(R.id.edtLinkCuaBan);
        Button btnGoto = (Button) getActivity().findViewById(R.id.btnGoto);
        btnGoto.setOnClickListener(this);

        tvTip0_link_group = (TextView) getActivity().findViewById(R.id.tvTip0_link_group);
        tvTip1 = (TextView) getActivity().findViewById(R.id.tvTip1);
        tvTip2 = (TextView) getActivity().findViewById(R.id.tvTip2);
        tvTip3 = (TextView) getActivity().findViewById(R.id.tvTip3);
        tvTip4 = (TextView) getActivity().findViewById(R.id.tvTip4);
        tvTip5 = (TextView) getActivity().findViewById(R.id.tvTip5);

        String[] danhSachLoiKhuyen = getDanhSachLoiKhuyen();
        tvTip0_link_group.setText(getLinkGroups());
        tvTip1.setText(danhSachLoiKhuyen[0]);
        tvTip2.setText(danhSachLoiKhuyen[1]);
        tvTip3.setText(danhSachLoiKhuyen[2]);
        tvTip4.setText(danhSachLoiKhuyen[3]);
        tvTip5.setText(danhSachLoiKhuyen[4]);
    }

    private String getLinkGroups() {
        String link = "Các trang trao đổi học tập bạn nên tham khảo: \n\n\n" + link_groups[0] + "\n\n" + link_groups[1]
                + "\n\n" + link_groups[2] + "\n\n" + link_groups[3];
        return link;
    }

    private String[] getDanhSachLoiKhuyen() {
        String[] danhSachLoiKhuyen = new String[MAX_ELEMENT];
        try {
            AssetManager assetManager = getActivity().getAssets();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(assetManager.open("loikhuyen.txt"), "UTF-8"));
            String data = "";
            String tmpData = "";
            int index = 0;
            while ((tmpData = bufReader.readLine()) != null) {
                if (tmpData.equals("|*|")) {
                    danhSachLoiKhuyen[index++] = data;
                    data = "";
                } else {
                    data += tmpData + "\n";
                }
            }
            bufReader.close();
        } catch (Exception e) {
            Log.e("RUNGETLOIKHUYEN", "getDanhSachLoiKhuyen: ");
            e.printStackTrace();
        }
        return danhSachLoiKhuyen;
    }

    @Override
    public void onClick(View view) {
        try {
//        btnGoto.setBackgroundResource(R.drawable.when_click_arrow_right);
            String url = edtLinkTuGo.getText() + "";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
//        btnGoto.setBackgroundResource(R.drawable.arrow_right);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Link của bạn cung cấp không chính xác có lẽ thiếu https://." +
                    "\nBạn hãy viết đầy đủ link như https://daynhauhoc.com", Toast.LENGTH_LONG).show();
        }
    }
}
