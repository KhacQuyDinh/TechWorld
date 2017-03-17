package model_dictionary;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import databases.DatabaseEV;
import databases.DatabaseHistoryEV;

/**
 * Created by Dell on 11/11/2016.
 */
public class EVDictionary extends GeneralDictionary {
    private DatabaseEV dbListWordEV;
    private DatabaseHistoryEV dbHistoryEV;
    private HashMap<String, String> listWordEV;
    private HashSet<String> listHistoryEV;

    public EVDictionary(Context context) {
        dbListWordEV = new DatabaseEV(context);
        dbHistoryEV  = new DatabaseHistoryEV(context);
        listWordEV = dbListWordEV.getListEV();
        listHistoryEV = dbHistoryEV.getListHistoryEV();
    }

    public ArrayList<String> getListKeyWord() {
        return new ArrayList<>(listWordEV.keySet());
    }

    /**
     *
     * @param keyWord la khoa tim kiem
     * @return null neu khong ton tai tu do trong tu dien hoac mot word tim duoc.
     */
    @Override
    public Word searchWord(String keyWord) {
        Word oldWord = null;
        String keyValue = listWordEV.get(keyWord);
        if (keyValue != null) {
            oldWord = new Word(keyWord, keyValue);
        }
        return oldWord;
    }

    /**
     *
     * @param newWord tu moi can them
     * @return true tuc la them tu moi thanh cong.
     * false neu tu do da ton tai trong tu dien hoac chen vao database loi.
     */
    @Override
    public boolean addNewWord(Word newWord) {
        boolean addedWord;
        addedWord = (listWordEV.put(newWord.getKey(), newWord.getContent()) == null)
                && dbListWordEV.addNewWord(newWord);
        return addedWord;
    }

    /**
     *
     * @param newWord tu moi duoc sua doi.
     * @return true neu update thanh cong.
     * false neu tu do khong co trong tu dien hoac tu can sua doi giong y het tu cu.
     * hoac viec them vao database hoac TreeMap loi.
     */
    @Override
    public boolean updateWord(Word newWord) {
        boolean updatedWord;
        updatedWord = (listWordEV.put(newWord.getKey(), newWord.getContent()) != null)
                    && dbListWordEV.updateOldWord(newWord);
        return updatedWord;
    }

    @Override
    public HashMap<String, String> getListWord() {
        return listWordEV;
    }
    /**
     *
     * @param keyWord tu khoa cua tu can xoa.
     * @return true neu xoa tu thanh cong.
     * false neu xoa tu that bai.
     * hoac viec xoa tu khoi database hoac TreeMap loi.
     */
    @Override
    public boolean deleteWord(String keyWord) {
        //Phep toan && --> Neu xoa tu trong Database listWordEV bat thanh thi dung xoa trong listWordEV.
        boolean deletedWord = (listWordEV.remove(keyWord) != null)
                && dbListWordEV.deleteOldWord(keyWord);
        if (deletedWord && listHistoryEV.contains(keyWord)) {
            deletedWord = listHistoryEV.remove(keyWord);
        }
        return deletedWord;
    }


    public boolean addNewWordToHistory(String EnglishWord) {
        boolean addedWord = false;
        if (!listHistoryEV.contains(EnglishWord)) {
            addedWord = listHistoryEV.add(EnglishWord)
                    && dbHistoryEV.addNewWordToHisToryEV(EnglishWord);
        }
        return addedWord;
    }

    /**
     *
     * @return true neu xoa lich su thanh cong.
     * false neu xoa lich su khong thanh cong.
     */
    @Override
    public boolean deleteHistory() {
        boolean deletedHistory = dbHistoryEV.deleteHistoryEV();
        listHistoryEV.clear();
        return deletedHistory;
    }

    @Override
    public boolean deleteAWordOfHistory(String keyWord) {
        boolean isDeleted =  listHistoryEV.remove(keyWord)
                && dbHistoryEV.deleteAWordOfHistoryEV(keyWord);
        return isDeleted;
    }

    /**
     *
     * @return ListEnglishWord trong databaseHistoryEV
     */
    @Override
    public HashSet<String> getListWordFromHistory() {
        return listHistoryEV;
    }
}
