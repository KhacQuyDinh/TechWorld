package model_dictionary;

/**
 * Created by Dell on 11/11/2016.
 * Mot Word gom hai truong:
 * key : chua khoa cua tu -> tieng Anh neu la tu dien E-V, tieng Viet neu la tu dien V-E
 * content : nghia tuong ung.
 * Su dung de tao tu moi de luu tam.
 */
public class Word {
    private String key;
    private String content;

    public Word(String key, String content) {
        this.key = key;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
