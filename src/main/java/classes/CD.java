package classes;

/**
 * Created by Michal on 06.04.2017.
 */
public class CD extends Product {

    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public CD(String author, String title, String type, double price, int duration) {
        super(author, title, type, price);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return getAuthor() + "," + getTitle() + "," + getType() + "," + getPrice() + "," + duration;
    }
}


