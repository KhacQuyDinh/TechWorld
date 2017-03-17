package yourfuture.dictionary;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import adapters.AdapterFeature;
import adapters.AdapterWord;
import features.AddWord;
import features.HistoryOfSearching;
import features.SearchWord;
import items.ItemFeature;
import items.ItemWord;
import model_dictionary.EVDictionary;
import model_dictionary.ModeDictionary;

public class Main2Activity extends AppCompatActivity implements Features
        , SearchView.OnQueryTextListener {

    //  private static ModeDictionary modeDictionary;//dung singleton de chi tao mot lan
    public static final int ADD_REQUEST = 1;
    private ArrayList<ItemWord> arrWord;
    private ArrayList<ItemWord> arrWordEV;
    private ArrayList<ItemWord> arrWordVE;
    private ArrayList<ItemFeature> arrFeature;
    private AdapterWord adapterWord;
    private AdapterFeature adapterFeature;
    private ListView listWord;
    private ListView listFeature;
    private SearchView searchView;
    private static int EV_DICTIONARY = 0;
    private final int SPEECH_RECOGNITION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Goi ham khoi tao.
        //set menu cho toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();

//        Tạo hai Thread để khởi tạo dữ liệu cho Database.
//        Phai doi cho nap xong co the xem android device monitor de theo doi dung luong nap.

//        Thread t1 = new Thread(r1);
//        Thread t2 = new Thread(r2);
//        t1.start();
//        t2.start();
    }

//    ĐOẠN CODE NÀY ĐỂ NẠP CƠ SỞ DỮ LIỆU BAN ĐẦU VÀO MÁY --> CHỈ CẦN NẠP 1 LẦN VỚI 1 MÁY.
/*
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
              copyFrom("ev_dic.txt", ModeDictionary.getEVDic());
        }
    };

    Runnable r2 = new Runnable() {
        @Override
        public void run() {
             copyFrom("ve_dic.txt", ModeDictionary.getVEDic());
        }
    };

        private void copyFrom(String f, GeneralDictionary dic) {
            try {
                InputStream inputStreamWord = getAssets().open(f);
                BufferedReader bufferWord = new BufferedReader(new InputStreamReader(inputStreamWord, "UTF-8"));
                String word;
                StringTokenizer token;
                while ((word = bufferWord.readLine()) != null) {
                    try {
                        token = new StringTokenizer(word, "\",\"");
                        dic.addNewWord(new Word(token.nextToken().replaceAll("\"", ""), token.nextToken().replaceAll("\"", "")));
                    } catch (Exception e) {
                    }
            }
            bufferWord.close();
            inputStreamWord.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * Thực hiện chức năng nói để tra từ.
     */
    private void startSpeechToText() {
        //xac nhan thuc hien chuc nang nhan recognize_speech
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        //Ngon ngu nhan dien la Locale.US
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //Tieu de khi hien thi loa.
        String title = "";
        String error = "";
        if (ModeDictionary.getExecDic() == ModeDictionary.getEVDic()) {
            title = "Hãy nói điều gì đó...";
            error = "Xin lỗi! Nhận dạng giọng nói không được hỗ trợ trên thiết bị này.";
        } else {
            title = "Speak something...";
            error = "Sorry! Speech recognition is not supported in this device.";
        }
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, title);

        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    //chay khi chuyen tu mot activity cua mot feature ve.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case SPEECH_RECOGNITION_CODE://thực hiện tìm kiếm bằng giọng nói sau khi đã khởi tạo.
                    //Nhan noi dung giong noi thanh mot list.
                    if (resultCode == RESULT_OK && data != null) {
                        ArrayList<String> resultList =
                                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        //nhan the real data.
                            String result = resultList.get(0);

                            //hiển thị text vừa nói lên trên searchView.
                            searchView.setQuery(result, true);
                            onQueryTextChange(result);
                    }
                    break;

                case ADD_REQUEST:
                    String keyWord = data.getStringExtra("KeyWord");
                    if (keyWord != null && !keyWord.equals("")) {
                        ItemWord itemWord = new ItemWord(keyWord);
                        processAddRequest(resultCode, itemWord);
                    }
                    break;
            }
        }
    }

    //khi chuyen tu activity add tro lai.
    private void processAddRequest(int typeDictionary, ItemWord itemWord) {
        if (typeDictionary == EV_DICTIONARY) {
            arrWordEV.add(itemWord);
        } else {
            arrWordVE.add(itemWord);
        }
        refreshAdapterWord();
    }

    //khoi tao cac thanh phan.
    private void init() {
        //Khoi tao MySQLite va arrWord, arrFeature
        new ModeDictionary(getApplicationContext());
        arrWord = new ArrayList<>();
        arrWordEV = new ArrayList<>();
        arrWordVE = new ArrayList<>();
        //Gan du lieu cho arrWordEV arrWordVE va arrWord the hien loai tu dien nao dang thuc thi.
        for (String i : ModeDictionary.getEVDic().getListKeyWord()) {
            arrWordEV.add(new ItemWord(i));
        }
        for (String i : ModeDictionary.getVEDic().getListKeyWord()) {
            arrWordVE.add(new ItemWord(i));
        }
        for (String i : ModeDictionary.getExecDic().getListKeyWord()) {
            arrWord.add(new ItemWord(i));
        }
        //Khoi tao arrFeature
        arrFeature = new ArrayList<>();

        //khoi tao du lieu cho arrFeature;
        arrFeature.add(new ItemFeature(android.R.drawable.presence_audio_away, Features.SEARCH_BY_VOICE_EV));
        arrFeature.add(new ItemFeature(R.drawable.ic_add_word, Features.ADD_NEW_WORD_EV));
        arrFeature.add(new ItemFeature(R.drawable.ic_history_search, Features.HISTORY_SEARCH_EV));
        arrFeature.add(new ItemFeature(R.drawable.ic_exit, Features.EXIT_EV));

        listWord = (ListView) findViewById(R.id.listViewWord);
        //khoi tao du lieu adapter adapterWord
        adapterWord =
                new AdapterWord(getApplicationContext(), arrWord, listWord);
        //Khoi tao du lieu ListView.
        listWord.setAdapter(adapterWord);

        listFeature = (ListView) findViewById(R.id.listViewFeature);
        //Khoi tao du lieu adapter adapterFeature
        adapterFeature =
                new AdapterFeature(getApplicationContext(), arrFeature);
        //Khoi tao du lieu ListView.
        listFeature.setAdapter(adapterFeature);

        //Lang nghe su kien cho ListView.
        listWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    default:
                        moveToSearchActivityWith(arrWord.get(i).getContent());
                        break;
                }
            }
        });

        listFeature.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case Features.MODE_SEARCH_BY_VOICE:
                        startSpeechToText();
                        break;

                    case Features.MODE_ADD_NEW_WORD:
                        moveToAddActivity();
                        break;

                    case Features.MODE_HISTORY:
                        moveToHistoryActivity();
                        break;

                    case Features.MODE_EXIT:
                        execExit();
                        break;
                }
            }
        });
        listWord.setTextFilterEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //nap menu.
        getMenuInflater().inflate(R.menu.main_menu_activity_2, menu);
        //tim item tu menu.
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        //khoi tao doi tuong searchView
        searchView = (SearchView) itemSearch.getActionView();
        //lang nghe su kien tim kiem.
        String querryHint = "";
        if (ModeDictionary.getExecDic() == ModeDictionary.getEVDic()) {
            querryHint = "Gõ English cần tìm kiếm...";
        } else {
            querryHint = "Type Vietnamese to search...";
        }
        searchView.setQueryHint(querryHint);
        searchView.setOnQueryTextListener(this);
        //true/ false
        return super.onCreateOptionsMenu(menu);
    }

    //Bat su kien cho item trong menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mode_dic:
                if (ModeDictionary.getExecDic() instanceof EVDictionary) {
                    item.setIcon(R.drawable.ic_ve);
                    changeToVEDictionary();
                } else {
                    item.setIcon(R.drawable.ic_ev);
                    changeToEVDictionary();
                }
                break;

            case R.id.exit:
                execExit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //doi sang che to VE Dictionary. de thuc hien cac thao tac tu dien. tim kiem them sua xoa
    private void changeToVEDictionary() {
        ModeDictionary.turnOnVEDictionary();
        arrWord.clear();
        for (ItemWord i : arrWordVE) {
            arrWord.add(i);
        }
        adapterWord.notifyDataSetChanged(); //because, it conflicts with setVisibility method.
//        refreshAdapterWord();

        arrFeature.get(Features.MODE_SEARCH_BY_VOICE).setName(Features.SEARCH_BY_VOICE_VE);
        arrFeature.get(Features.MODE_ADD_NEW_WORD).setName(Features.ADD_NEW_WORD_VE);
        arrFeature.get(Features.MODE_HISTORY).setName(Features.HISTORY_SEARCH_VE);
        arrFeature.get(Features.MODE_EXIT).setName(Features.EXIT_VE);
        adapterFeature.notifyDataSetChanged();
    }

    //doi sang che to EV Dictionary. de thuc hien cac thao tac tu dien. tim kiem them sua xoa
    private void changeToEVDictionary() {
        ModeDictionary.turnOnEVDictionary();
        arrWord.clear();
        for (ItemWord i : arrWordEV) {
            arrWord.add(i);
        }
        adapterWord.notifyDataSetChanged(); //because, it conflicts with setVisibility method.
//        refreshAdapterWord();

        arrFeature.get(Features.MODE_SEARCH_BY_VOICE).setName(Features.SEARCH_BY_VOICE_EV);
        arrFeature.get(Features.MODE_ADD_NEW_WORD).setName(Features.ADD_NEW_WORD_EV);
        arrFeature.get(Features.MODE_HISTORY).setName(Features.HISTORY_SEARCH_EV);
        arrFeature.get(Features.MODE_EXIT).setName(Features.EXIT_EV);
        adapterFeature.notifyDataSetChanged();
    }

    //cho thao tác tìm kiếm
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //cho thao tác tìm kiếm
    @Override
    public boolean onQueryTextChange(String newText) {
//        System.out.println(newText);
        adapterWord.getFilter().filter(newText);
        return true;
    }

    //chuyen toi addActivity.
    public void moveToAddActivity() {
        startActivityForResult(new Intent(getApplicationContext(), AddWord.class), ADD_REQUEST);
    }

    //chuyen toi History Activity.
    public void moveToHistoryActivity() {
        startActivity(new Intent(getApplicationContext(), HistoryOfSearching.class));
    }

    //chuyen toi search activity voi tu khoa (Key la English(EV dictionary) hoac Key la Vietnamese(VE dictionary))
    public void moveToSearchActivityWith(String keyWord) {
        Intent intent = new Intent(getApplicationContext(), SearchWord.class);
        intent.putExtra("KeyWord", keyWord);
        ModeDictionary.getExecDic().addNewWordToHistory(keyWord);
        startActivity(intent);
    }

    //bat su kien phim quay ve.
    @Override
    public void onBackPressed() {
        execExit();
    }

    //Thuc hien thao tac xoa.
    private void execExit() {
        String title = "CONFIRM EXIT";
        String message = "Do you really want to exit?";
        final AlertDialog.Builder notify = new AlertDialog.Builder(Main2Activity.this);
        notify.setTitle(title);
        notify.setMessage(message)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();//tuong tu dismiss(). Nhung phu thuoc hon vao setCancelable(true);//default true.
                            }
                        });
        notify.show();
    }

    private void refreshAdapterWord() {
        adapterWord = new AdapterWord(Main2Activity.this, arrWord, listWord);
        listWord.setAdapter(adapterWord);
    }
}
