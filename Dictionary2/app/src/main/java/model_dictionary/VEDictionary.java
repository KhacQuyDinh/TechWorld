package model_dictionary;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import databases.DatabaseVE;
import databases.DatabaseHistoryVE;

/**
 * Created by Dell on 11/11/2016.
 */
public class VEDictionary extends GeneralDictionary {
    private DatabaseVE dbListWordVE;
    private DatabaseHistoryVE dbHistoryVE;
    private HashMap<String, String> listWordVE; //Dùng hashmap để tiện quản lý.
    private HashSet<String> listHistoryVE; // Dùng hashset để tránh trùng lặp key.

    public VEDictionary(Context context) {
        dbListWordVE = new DatabaseVE(context);
        dbHistoryVE  = new DatabaseHistoryVE(context);
        listWordVE = dbListWordVE.getListEV();
        listHistoryVE = dbHistoryVE.getListHistoryVE();
    }

    public ArrayList<String> getListKeyWord() {
        return new ArrayList<>(listWordVE.keySet());
    }

    /**
     *
     * @param keyWord la khoa tim kiem
     * @return null neu khong ton tai tu do trong tu dien hoac mot word tim duoc.
     */
    @Override
    public Word searchWord(String keyWord) {
        Word oldWord = null;
        String keyValue = listWordVE.get(keyWord);
        if (keyValue != null) {
            oldWord = new Word(keyWord, keyValue);
            if (!this.addNewWordToHistory(keyWord)) {
                return null;
            }
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
        addedWord = (listWordVE.put(newWord.getKey(), newWord.getContent()) == null)
                    && dbListWordVE.addNewWord(newWord);
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
        updatedWord = (listWordVE.put(newWord.getKey(), newWord.getContent()) != null)
                    && dbListWordVE.updateOldWord(newWord);
        return updatedWord;
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
        boolean deletedWord = (listWordVE.remove(keyWord) != null)
                && dbListWordVE.deleteOldWord(keyWord);
        if (deletedWord && listHistoryVE.contains(keyWord)) {
            deletedWord = listHistoryVE.remove(keyWord);
        }
        return deletedWord;
    }


    /**
     *
     * @param EnglishWord từ cần thêm
     * @return thành công hay không
     */
    @Override
    public boolean addNewWordToHistory(String EnglishWord) {
        boolean addedWord = false;
        if (!listHistoryVE.contains(EnglishWord)) {
            addedWord = listHistoryVE.add(EnglishWord)
                    && dbHistoryVE.addNewWordToHisToryVE(EnglishWord);
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
        boolean deletedHistory = dbHistoryVE.deleteHistoryVE();
        listHistoryVE.clear();
        return deletedHistory;
    }

    /**
     *
     * @param keyWord từ khóa
     * @return xóa thành công hoặc không.
     */
    @Override
    public boolean deleteAWordOfHistory(String keyWord) {
        boolean isDeleted = listHistoryVE.remove(keyWord) //xóa từ trong danh sách từ hiện tại trên màn hình
                && dbHistoryVE.deleteAWordOfHistoryVE(keyWord);//xóa trong db. --> lâu dài và xóa thực.
        return isDeleted;
    }

    /**
     *
     * @return ListEnglishWord trong databaseHistoryVE
     */
    @Override
    public HashSet<String> getListWordFromHistory() {
        return listHistoryVE;
    }

    /**
     *
     * @return danh sách từ
     */
    @Override
    public HashMap<String, String> getListWord() {
        return listWordVE;
    }
}
