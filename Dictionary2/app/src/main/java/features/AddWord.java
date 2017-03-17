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
import android.widget.Toast;

import model_dictionary.Word;
import yourfuture.dictionary.R;
import model_dictionary.EVDictionary;
import model_dictionary.ModeDictionary;


public class AddWord extends AppCompatActivity {
    private Intent intent;

    //textview để hiện thị nội dung lên giao diện
    private EditText tvAddEngLish;
    private EditText tvAddVietNamese;

    private Button btnAddSave;
    private static final String goodMessage = "The word is added";
    private static final String badMessage = "The word is not added";

    private int mode;//xác định kiểu từ điển hiện tại.
    private static final int EV_DICTIONARY_MODE = 0;//kiểu từ EV dictionary.
    private static final int VE_DICTIONARY_MODE = 1;//Kiểu từ VE dictionary.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ModeDictionary.getExecDic() == ModeDictionary.getEVDic()) {
            getSupportActionBar().setTitle(getString(R.string.title_add_word_EV));
        } else {
            getSupportActionBar().setTitle(getString(R.string.title_add_word_VE));
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //Khoi tao intent;
        intent = getIntent();
        //Chuyen du lieu vao bundle va intent
        intent.putExtra("KeyWord", "");
        if (ModeDictionary.getExecDic() instanceof EVDictionary) {
            initWithEVDic();
        } else {
            initWithVEDic();
        }
        //setResult de chay cho phuong thuc setOnResultActivity() cua trang truoc.
        setResult(mode, intent);
        btnAddSave = (Button) findViewById(R.id.btnAddSave);
        btnAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddingNewWord();
                //NHƯNG KHI CHẠY SỰ KIỆN CHO BUTTON THÌ LẠI PHẢI THÊM LẠI.
                setResult(mode, intent);
                finish();
            }
        });
    }

    /**
     * them tu thong bao ket qua va luu du lieu va bundle.
     */
    public void startAddingNewWord() {
        //Truoc tien la khoi tao EditText va du lieu ban dau cho intent
        boolean isAdded = false;
        String EngLish = (tvAddEngLish.getText()+"").trim();
        String VietNamese = (tvAddVietNamese.getText()+"").trim();
        if (!EngLish.equals("") && !VietNamese.equals("")) {
            if (mode == EV_DICTIONARY_MODE) {
                isAdded = ModeDictionary.getExecDic().addNewWord(new Word(EngLish, VietNamese));
            } else {
                isAdded = ModeDictionary.getExecDic().addNewWord(new Word(VietNamese, EngLish));
            }
        }
        if (isAdded) {
            if (mode == EV_DICTIONARY_MODE) {
                intent.putExtra("KeyWord", EngLish);
            } else {
                intent.putExtra("KeyWord", VietNamese);
            }
            showMessage(goodMessage);
        } else {
            showMessage(badMessage);
        }
    }

    private void initWithEVDic() {
        tvAddEngLish = (EditText) findViewById(R.id.tvAddFirstWord);
        tvAddEngLish.setHint(R.string.hintAddWordEV);
        tvAddVietNamese = (EditText) findViewById(R.id.tvAddSecondWord);
        tvAddVietNamese.setHint(R.string.hintAddMeaningEV);
        mode = EV_DICTIONARY_MODE;
    }

    private void initWithVEDic() {
        tvAddVietNamese = (EditText) findViewById(R.id.tvAddFirstWord);
        tvAddVietNamese.setHint(R.string.hintAddWordVE);
        tvAddEngLish = (EditText) findViewById(R.id.tvAddSecondWord);
        tvAddEngLish.setHint(R.string.hintAddMeaningVE);
        mode = VE_DICTIONARY_MODE;
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_add, menu);
        MenuItem item = menu.findItem(R.id.mode_dic);
        if (ModeDictionary.getExecDic() instanceof EVDictionary) {
            item.setIcon(R.drawable.ic_ev);
        } else {
            item.setIcon(R.drawable.ic_ve);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
            case android.R.id.home:
                execExit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void execExit() {
        showMessage(badMessage);
        finish();
    }
}
