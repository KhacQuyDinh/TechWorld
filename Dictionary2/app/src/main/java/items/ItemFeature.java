package items;

/**
 * Created by Dell on 13/11/2016.
 */
public class ItemFeature {
    private int icon;
    private String name;

    public ItemFeature(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
