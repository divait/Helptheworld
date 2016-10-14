package co.waspp.divait.helptheworld.recycle;

/**
 * Created by divait on 12/10/2016.
 */

public class RecycleItem {
    private String title;
    private String description;
    private int image;
    private int background;

    RecycleItem(String title, String description, int image, int background) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public int getBackground() {
        return background;
    }
}
