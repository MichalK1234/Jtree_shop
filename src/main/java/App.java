import jtree.DynamicTreeDemo;
import database.Database;

import java.sql.SQLException;

/**
 * Created by Michal on 09.05.2017.
 */
public class App {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        Database.connect();
        Database.createTable();

     /* Database.insertCd( new CD("Ania Wyszkoni","\"Jestem tu nowa\"","POP",34.99, 75 ));
        Database.insertCd( new CD("Ania Wyszkoni","\"Jestem tu nowa\"","POP", 34.99,75));


        Database.insertCd( new CD("Budka Suflera","\"Ratujmy co sie da\"","Rock", 35.99,71));
        Database.insertCd( new CD("Budka Suflera","\"Ratujmy co sie da\"","Rock", 35.99,71));

        Database.insertBook(new Book("\"Harry Potter 6\"","J.K. Rowling", "Fantasy", 49.99,990));
        Database.insertBook(new Book("\"Harry Potter 6\"","J.K. Rowling", "Fantasy", 49.99,990));
        Database.insertBook(new Book("\"Harry Potter 6\"","J.K. Rowling", "Fantasy", 49.99,990));
        Database.insertBook(new Book("\"Harry Potter 5\"","J.K. Rowling", "Fantasy", 39.99,564));
        Database.insertBook(new Book("\"Harry Potter 5\"","J.K. Rowling", "Fantasy", 39.99,564));*/


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DynamicTreeDemo.createAndShowGUI();
            }
        });
    }

}
