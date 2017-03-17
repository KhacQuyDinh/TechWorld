package features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model_dictionary.ModeDictionary;
import model_dictionary.Word;
import yourfuture.dictionary.MainActivity;
import yourfuture.dictionary.R;

public class Update extends AppCompatActivity {

    private TextView tvKey;
    private EditText edtContent;
    private Intent prevIntent;
    private Button btnSave;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //Cai dat actionbar cho menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ModeDictionary.getExecDic() == ModeDictionary.getEVDic()) {
            getSupportActionBar().setTitle(getString(R.string.title_update_EV));
        } else {
            getSupportActionBar().setTitle(getString(R.string.title_update_VE));
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Khoi tao Intent.
        prevIntent = getIntent();

        //lay keyWord. co the la tieng anh hoac tieng viet tuy loai tu dien.
        key = prevIntent.getStringExtra("KeyWord");

        //setDuLieu.
        setData();

         //Lấy sự kiện cho btnSave
        btnSave = (Button) findViewById(R.id.btnUpdateSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdating();
            }
        });
    }

    private void setData() {
        //Khoi tao bien
        tvKey = (TextView) findViewById(R.id.tvKey);
        edtContent = (EditText) findViewById(R.id.edtContent);

        //Gan du lieu.
        tvKey.setText(key);
        edtContent.setText(ModeDictionary.getExecDic().getListWord().get(key));
    }

    private void startUpdating() {
        boolean updatedWord = false;
        String content = edtContent.getText().toString().trim();

        //Neu key va noi dung khong rong thi thuc hien update tren co so du lieu la du..
        if (!key.equals("")
                //Xac dinh la noi dung cua tu moi cap nhap (khác nội dung cũ).
                && !content.equals(ModeDictionary.getExecDic().getListWord().get(key))
                && ModeDictionary.getExecDic().updateWord(new Word(key, content))) {
            //xac nhan update thuc thi thanh cong.
            updatedWord = true;
        }
        if (updatedWord) {
            //thong bao tin nhan the hien su thanh cong.
            showMessage("Data is updated");

            //Quay lai giao dien chinh. --> khởi động lại
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            //Thong bao khong thanh cong --> muon thoat thi phai dung exit tren toolbar chua trong dau ba cham doc.
            showMessage("Data is not updated");
        }
        //Kết thúc giao diện hiện tại
        finish();
    }

    //Khoi tao menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Bat su kien item tren menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                case android.R.id.home:
                    finish();
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
