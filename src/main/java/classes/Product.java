package classes;

/**
 * Created by Michal on 04.04.2017.
 */
public class Product {


    private String author;
    private String title;
    private String type;
    private double price;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {


        if (price > 0) {
            this.price = price;
        } else {

            throw new IllegalArgumentException();

        }
    }

    public Product(String author, String title, String type, double price) {
        this.author = author;
        this.title = title;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return getAuthor() + "," + getTitle() + "," + getPrice() + "," + getType();
    }

}
