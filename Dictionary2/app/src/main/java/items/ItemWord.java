package items;

import java.io.Serializable;

import yourfuture.dictionary.R;

public class ItemWord implements Serializable {
    private int icon;
    private String content;

    public ItemWord(String content) {
        this.content = content;
        icon = R.drawable.ic_pin;
    }

    public ItemWord(int icon, String word) {
        this.icon = icon;
        this.content = word;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
