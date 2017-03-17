package model_dictionary;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Dell on 11/11/2016.
 * Cấu trúc chung của một từ điển gồm các thao tác của nó.
 */
public abstract class GeneralDictionary extends AppCompatActivity {
    public abstract ArrayList<String> getListKeyWord();
    public abstract Word searchWord(String keyWord);
    public abstract boolean addNewWord(Word newWord);
    public abstract boolean updateWord(Word oldWord);
    public abstract boolean deleteWord(String keyWord);
    public abstract boolean addNewWordToHistory(String keyWord);
    public abstract boolean deleteHistory();
    public abstract boolean deleteAWordOfHistory(String keyWord);
    public abstract HashSet<String> getListWordFromHistory();
    public abstract HashMap<String, String> getListWord();
}