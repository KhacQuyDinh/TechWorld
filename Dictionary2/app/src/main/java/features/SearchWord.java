package features;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import model_dictionary.GeneralDictionary;
import model_dictionary.ModeDictionary;
import model_dictionary.VEDictionary;
import yourfuture.dictionary.MainActivity;
import yourfuture.dictionary.R;

public class SearchWord extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView tvKey;
    private TextView tvContent;
    private Intent prevIntent;
    private Button btnSpeak;
    private String key = "";
    private TextToSpeech tts;
    private boolean isVEDic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
        //Cai dat actionbar cho menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ModeDictionary.getExecDic() == ModeDictionary.getEVDic()) {
            getSupportActionBar().setTitle(getString(R.string.title_search_word_EV));
        } else {
            getSupportActionBar().setTitle(getString(R.string.title_search_word_VE));
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Khoi tao Intent.
        prevIntent = getIntent();
        //lay keyWord. co the la tieng anh hoac tieng viet tuy loai tu dien.
        key = prevIntent.getStringExtra("KeyWord");
//        System.out.println("KEY = " + key);
        //setDuLieu.
        setData();
    }

    public void setData() {
        //Khoi tao bien
        tvKey = (TextView) findViewById(R.id.tvKey);
        tvContent = (TextView) findViewById(R.id.tvContent);
        //Gan du lieu.
        tvKey.setText(key);
        tvContent.setText(ModeDictionary.getExecDic().getListWord().get(key));
        // init tools for TTS
        tts = new TextToSpeech(this, this);
        btnSpeak = (Button) findViewById(R.id.btnSpeech);

        //Nếu là từ điển Việt Anh thì chưa hỗ trỗ phát âm.
        if (ModeDictionary.getExecDic() instanceof VEDictionary) {
            isVEDic = true;
        }
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut();
            }
        });
    }

    //Chay phuong thuc onCreateOptionsMenu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Lang nghe su kien click Item tren listView
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
            case android.R.id.home:
                finish();
                break;
            case R.id.update:
                startUpdating();
                break;
            case R.id.delete:
                startDeleteWord();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startUpdating() {
        Intent intent = new Intent(getApplicationContext(), Update.class);
        intent.putExtra("KeyWord", key);
        finish();
        startActivity(intent);
    }
    //Xoa bat dau xoa tu.
    private void startDeleteWord() {
        //Kiem tra xoa tu khoi dbListWord va dbHistory neu thanh cong thi..
        //key la loai tu dien dang thuc thi.
        boolean isDeleted;
        GeneralDictionary tmp = ModeDictionary.getExecDic();
        isDeleted = tmp.getListWord().containsKey(key) && tmp.deleteWord(key);
        //..Khoi dong lai khi thuc hien xoa tu de gan lai moi du lieu.
        if (isDeleted) {
            showMessage("Word is deleted");
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            showMessage("Word is not deleted");
        }
    }

    /**
     * Nói text thành tiếng. - text to speech
     */
    private void speakOut() {
        if (isVEDic) {// Nếu là tiếng Việt thì thông báo chưa hỗ trợ âm thanh.
            showMessage(getString(R.string.mute));
        } else {
            if (!key.equals("")) {
                tts.speak(key, TextToSpeech.QUEUE_FLUSH, null);//Xâu không rỗng thì cho vào hàng đợi của TextToSpeech
            } else {
                tts.speak("you haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    public void onInit(int status) {
        //TODO Auto-generrated method stub
        //TTS is successfully initalized
        if (isVEDic) {
            showMessage(getString(R.string.mute));
        } else {
            showMessage(getString(R.string.voice));
        }

        if (status == TextToSpeech.SUCCESS) {
            //Cài đặt ngôn ngữ để nói ra tiếng từ text là United States
            int result = tts.setLanguage(Locale.US);

            //Nếu thiết bị không hỗ trợ ngôn ngữ cài đặt ở trên
            // hoặc dữ liệu bị mất thì thông báo không được.
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
            }

            //Mở lại sự kiện cho biểu tượng loa đã bị khóa trong file .XML (giao diện)
            else {
                btnSpeak.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "TTS innitialization failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * có chức năng hiển thị message
     * @param message tin nhắn hiển thị lên màn hình.
     */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
