package features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.AdapterHistory;
import items.ItemHistory;
import model_dictionary.EVDictionary;
import model_dictionary.ModeDictionary;
import yourfuture.dictionary.R;

public class HistoryOfSearching extends AppCompatActivity
                                implements AdapterView.OnItemClickListener,
                                        SearchView.OnQueryTextListener {

    private ArrayList<ItemHistory> arrWord; //luu danh sach tu da tra -> lay du lieu tu database History
    private AdapterHistory adapterHistory;
    private ListView lvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_searching);
        //Goi ham khoi tao.
        //set menu cho toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Khoi tao.
        init();
    }

    //khoi tao cac thanh phan.
    private void init() {
        //Khoi tao du lieu cho arrWordEV va arrWordVE va arrWord
        arrWord = new ArrayList<>();
        for (String i : ModeDictionary.getExecDic().getListWordFromHistory()) {
           arrWord.add(new ItemHistory(i));
        }
        //khoi tao du lieu adapter adapterWord
        adapterHistory =
                new AdapterHistory(getApplicationContext(), arrWord);
        //Khoi tao du lieu ListView.
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setAdapter(adapterHistory);
        //Lang nghe su kien cho ListView.
        lvHistory.setOnItemClickListener(this);
        //su kien tim kiem tu trong lich su.
        lvHistory.setTextFilterEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //nap menu.
        getMenuInflater().inflate(R.menu.main_menu_history, menu);
        if (ModeDictionary.getExecDic() instanceof EVDictionary) {
            menu.findItem(R.id.typeDic).setIcon(R.drawable.ic_ev);
        } else {
            menu.findItem(R.id.typeDic).setIcon(R.drawable.ic_ve);
        }
        //tim item tu menu.
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        //khoi tao doi tuong searchView
        SearchView searchView;
        searchView = (SearchView) itemSearch.getActionView();
        //lang nghe su kien tim kiem.
        searchView.setQueryHint("Type.....");
        searchView.setOnQueryTextListener(this);
        //true/ false
        return super.onCreateOptionsMenu(menu);
    }

    //Bat su kien cho item trong menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearHistory:
                startClearHistory();
                break;
            case android.R.id.home:
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Mot chuc nang xoa toan bo lich su.
    private void startClearHistory() {
        arrWord.clear();
        adapterHistory.notifyDataSetChanged();
        ModeDictionary.getExecDic().deleteHistory();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterHistory.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(HistoryOfSearching.this, SearchWord.class);
        intent.putExtra("KeyWord", arrWord.get(i).getTvContent());
        startActivity(intent);
        //Them tu vao lich su tra tu
    }
}