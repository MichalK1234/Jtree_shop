package database;

import classes.*;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michal on 12.04.2017.
 */
public class Database {

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE = "jdbc:sqlite:Shop.db";

    private static Connection conn;
    private static Statement stat;

    public static void connect() throws SQLException, ClassNotFoundException {

        Class.forName(DRIVER);
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        conn = DriverManager.getConnection(DATABASE, config.toProperties());
        stat = conn.createStatement();

    }

    public static void createTable() throws SQLException {


        String BookSQL = "CREATE TABLE IF NOT EXISTS Book (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author VARCHAR(50) NOT NULL, title VARCHAR(50) NOT NULL, type VARCHAR(50) NOT NULL, price DOUBLE NOT NULL, pages INTEGER NOT NULL);";

        String CdSQL = "CREATE TABLE IF NOT EXISTS Cd (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author VARCHAR(50) NOT NULL, title VARCHAR(50) NOT NULL, type VARCHAR(50) NOT NULL, price DOUBLE NOT NULL, duration INTEGER NOT NULL);";


        stat.execute(BookSQL);
        stat.execute(CdSQL);


    }

    public static void insertCd(CD cd) throws SQLException {

        String sqlInsert = "INSERT INTO Cd (author, title, type, price, duration) VALUES (?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sqlInsert);

        ps.setString(1, cd.getAuthor());
        ps.setString(2, cd.getTitle());
        ps.setString(3, cd.getType());
        ps.setDouble(4, cd.getPrice());
        ps.setInt(5, cd.getDuration());

        ps.execute();


    }

    public static void insertBook(Book book) throws SQLException {

        String sqlInsert = "INSERT INTO Book (author, title, type, price, pages) VALUES (?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sqlInsert);

        ps.setString(1, book.getAuthor());
        ps.setString(2, book.getTitle());
        ps.setString(3, book.getType());
        ps.setDouble(4, book.getPrice());
        ps.setInt(5, book.getPages());
        ps.execute();
    }


    public static void deleteCd(int id) throws SQLException {

        String sqlDelete = "DELETE FROM Cd WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sqlDelete);
        ps.setInt(1, id);
        ps.execute();

    }


    public static void deleteBook(int id) throws SQLException {

        String sqlDelete = "DELETE FROM Book WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sqlDelete);
        ps.setInt(1, id);
        ps.execute();

    }


    public static List<CD> selectCDs() throws SQLException {

        String sqlSelect = "SELECT * FROM Cd";


        ResultSet rs = stat.executeQuery(sqlSelect);

        String author, title, type;
        double price;
        int duration;

        List<CD> list = new ArrayList<>();

        while ((rs.next())) {

            author = rs.getString(1);
            title = rs.getString(2);
            type = rs.getString(3);
            price = rs.getDouble(4);
            duration = rs.getInt(5);


            list.add(new CD(author, title, type, price, duration));

        }
        return list;
    }

    public static List<CD> groupByCd() throws SQLException {
        String groupBySQL = "SELECT author,title,type,price,duration FROM Cd GROUP BY author,title, type, price, duration";


        String title, type, author;
        double price;
        int duration;

        List<CD> cdList = new ArrayList<>();

        ResultSet rs = stat.executeQuery(groupBySQL);

        while (rs.next()) {

            author = rs.getString(1);
            title = rs.getString(2);
            type = rs.getString(3);
            price = rs.getDouble(4);
            duration = rs.getInt(5);


            cdList.add(new CD(author, title, type, price, duration));

        }


        return cdList;
    }

    public static Integer countCd(String title, double price, String type, String author) throws SQLException {

        String countSQL = "SELECT COUNT(*) FROM Cd WHERE  title = " + "'" + title + "'" + " AND author = " + "'" + author + "'" +
                " AND type = " + "'" + type + "'" + " AND price =" + price;

        ResultSet rs = stat.executeQuery(countSQL);

        Integer amount = -1;

        if (rs.next()) {

            amount = rs.getInt(1);

        }

        return amount;
    }

    public static Map<CD, Integer> cdMap() throws SQLException {

        Map<CD, Integer> cdMap = new HashMap<>();


        List<CD> list = new ArrayList<>(Database.groupByCd());

        list.forEach(e -> {

            try {

                cdMap.put(e, Database.countCd(e.getTitle(), e.getPrice(), e.getType(), e.getAuthor()));

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });





        return cdMap;

    }



    public static List<Book> selectBooks() throws SQLException {

        String sqlSelect = "SELECT * FROM Book";


        ResultSet rs = stat.executeQuery(sqlSelect);

        String author, title, type;
        double price;
        int pages;

        List<Book> list = new ArrayList<>();

        while ((rs.next())) {

            author = rs.getString(1);
            title = rs.getString(2);
            type = rs.getString(3);
            price = rs.getDouble(4);
            pages = rs.getInt(5);


            list.add(new Book(author, title, type, price, pages));

        }
        return list;
    }

    public static List<Book> groupByBook() throws SQLException {
        String groupBySQL = "SELECT author,title,type,price,pages FROM Book GROUP BY author,title, type, price, pages";


        String title, type, author;
        double price;
        int pages;

        List<Book> bookList = new ArrayList<>();

        ResultSet rs = stat.executeQuery(groupBySQL);

        while (rs.next()) {

            author = rs.getString(1);
            title = rs.getString(2);
            type = rs.getString(3);
            price = rs.getDouble(4);
            pages = rs.getInt(5);


            bookList.add(new Book(author, title, type, price, pages));

        }


        return bookList;
    }

    public static Integer countBook(String title, double price, String type, String author) throws SQLException {

        String countSQL = "SELECT COUNT(*) FROM Book WHERE  title = " + "'" + title + "'" + " AND author = " + "'" + author + "'" +
                " AND type = " + "'" + type + "'" + " AND price =" + price;

        ResultSet rs = stat.executeQuery(countSQL);

        Integer amount = -1;

        if (rs.next()) {

            amount = rs.getInt(1);

        }

        return amount;
    }

    public static Map<Book, Integer> bookMap() throws SQLException {

        Map<Book, Integer> bookMap = new HashMap<>();


        List<Book> list = new ArrayList<>(Database.groupByBook());

        list.forEach(e -> {

            try {

                bookMap.put(e, Database.countBook(e.getTitle(), e.getPrice(), e.getType(), e.getAuthor()));

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });



        return bookMap;

    }

    public static int deleteCdByIdWhere(String author, String title, String type, double price, int duration) throws SQLException {


        String sql = "SELECT MIN(Id) FROM CD WHERE  author = " + "'" + author + "'" + " AND title = " + "'" + title + "'" +
                " AND type = " + "'" + type + "'" + " AND price =" + price + " AND duration =" + duration;

        int idx;

        ResultSet rs = stat.executeQuery(sql);

        if (rs.next()) {

            idx = rs.getInt(1);

            return idx;
        }

        return -1;

    }

    public static int deleteBookByIdWhere(String author, String title, String type, double price, int pages) throws SQLException {


        String sql = "SELECT MIN(Id) FROM Book WHERE  author = " + "'" + author + "'" + " AND title = " + "'" + title + "'" +
                " AND type = " + "'" + type + "'" + " AND price =" + price + " AND pages =" + pages;

        int idx;

        ResultSet rs = stat.executeQuery(sql);

        if (rs.next()) {

            idx = rs.getInt(1);

            return idx;
        }

        return -1;

    }

}
