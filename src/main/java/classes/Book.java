package classes;

/**
 * Created by Michal on 06.04.2017.
 */
public class Book extends Product {

    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Book(String author, String title, String type, double price, int pages) {
        super(author, title, type, price);
        this.pages = pages;
    }

    @Override
    public String toString() {
        return getAuthor() + "," + getTitle() + "," + getType() + "," + getPrice() + "," + pages;
    }


}
