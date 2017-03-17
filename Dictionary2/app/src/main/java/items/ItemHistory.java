package items;

/**
 * Created by Dell on 16/11/2016.
 */
public class ItemHistory {
    private int icon;
    private String tvContent;
    private int del;

    public ItemHistory(String tvContent) {
        this.tvContent = tvContent;
    }

    public ItemHistory(int icon, String tvContent, int del) {
        this.icon = icon;
        this.tvContent = tvContent;
        this.del = del;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTvContent() {
        return tvContent;
    }

    public void setTvContent(String tvContent) {
        this.tvContent = tvContent;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }
}
