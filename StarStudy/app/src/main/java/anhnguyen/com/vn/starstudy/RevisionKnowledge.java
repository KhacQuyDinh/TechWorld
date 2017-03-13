package anhnguyen.com.vn.starstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class RevisionKnowledge extends AppCompatActivity {

    private ArrayList<Integer> arrLinkAnh = new ArrayList<>();
    private RevisionKnowledgeAdapter revisionAdapter;
    private ListView lvRevision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_knowledge);

        Intent intent = getIntent();
        int monHoc = intent.getIntExtra("KeyOnTap", -100);
        setTaiNguyenAnhCho(arrLinkAnh, monHoc);
        revisionAdapter = new RevisionKnowledgeAdapter(RevisionKnowledge.this, arrLinkAnh);
        lvRevision = (ListView) findViewById(R.id.lvRevision);
        lvRevision.setAdapter(revisionAdapter);
    }

    private void setTaiNguyenAnhCho(ArrayList<Integer> tmpLinkAnh, int monHoc) {
        switch (monHoc) {
            case MaMonHoc.TOANHOC:
                setTaiNguyenAnhMonToan(tmpLinkAnh); break;
            case MaMonHoc.HOAHOC:
                setTaiNguyenAnhMonHoa(tmpLinkAnh); break;
            case MaMonHoc.VATLY:
                setTaiNguyenAnhMonLy(tmpLinkAnh); break;
            default:
                break;
        }
    }

    private void setTaiNguyenAnhMonToan(ArrayList<Integer> tmpLinkAnh) {
        tmpLinkAnh.add(R.drawable.toanhoc_picture_01);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_02);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_03);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_04);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_05);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_06);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_07);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_08);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_09);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_10);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_11);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_12);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_13);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_14);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_15);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_16);
        tmpLinkAnh.add(R.drawable.toanhoc_picture_17);
    }

    private void setTaiNguyenAnhMonLy(ArrayList<Integer> tmpLinkAnh) {
        tmpLinkAnh.add(R.drawable.vatly_picture_01);
        tmpLinkAnh.add(R.drawable.vatly_picture_02);
        tmpLinkAnh.add(R.drawable.vatly_picture_03);
        tmpLinkAnh.add(R.drawable.vatly_picture_04);
        tmpLinkAnh.add(R.drawable.vatly_picture_05);
        tmpLinkAnh.add(R.drawable.vatly_picture_06);
        tmpLinkAnh.add(R.drawable.vatly_picture_07);
        tmpLinkAnh.add(R.drawable.vatly_picture_08);
        tmpLinkAnh.add(R.drawable.vatly_picture_09);
        tmpLinkAnh.add(R.drawable.vatly_picture_10);
        tmpLinkAnh.add(R.drawable.vatly_picture_11);
        tmpLinkAnh.add(R.drawable.vatly_picture_12);
        tmpLinkAnh.add(R.drawable.vatly_picture_13);
        tmpLinkAnh.add(R.drawable.vatly_picture_14);
        tmpLinkAnh.add(R.drawable.vatly_picture_15);
        tmpLinkAnh.add(R.drawable.vatly_picture_16);
        tmpLinkAnh.add(R.drawable.vatly_picture_17);
        tmpLinkAnh.add(R.drawable.vatly_picture_18);
        tmpLinkAnh.add(R.drawable.vatly_picture_19);
    }

    private void setTaiNguyenAnhMonHoa(ArrayList<Integer> tmpLinkAnh) {
        tmpLinkAnh.add(R.drawable.hoahoc_picture_01);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_02);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_03);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_04);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_05);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_06);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_07);
        tmpLinkAnh.add(R.drawable.hoahoc_picture_08);
    }
}
