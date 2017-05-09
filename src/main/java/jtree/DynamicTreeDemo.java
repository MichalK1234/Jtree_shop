package jtree;

import classes.Book;
import classes.CD;
import database.Database;
import panels.Cart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Michal on 14.04.2017.
 */
public class DynamicTreeDemo extends JPanel implements ActionListener {

    private static JButton btnAdd;
    private static JButton btnCart;


    private DynamicTree treePanel;

    public DynamicTreeDemo() {
        super(new BorderLayout());

        treePanel = new DynamicTree();
        try {
            populateTree(treePanel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {

            treePanel.putCurrentProductIntoCart();

        });

        btnCart = new JButton("Cart");
        btnCart.addActionListener(e -> {


            JDialog frame = new JDialog(null, "Your cart content:", Dialog.ModalityType.APPLICATION_MODAL);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Cart panel = new Cart(treePanel.getCartContent(), this);
            panel.setVisible(true);
            frame.setContentPane(panel);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);

        });


        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);


        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(btnAdd);
        panel.add(btnCart);
        add(panel, BorderLayout.SOUTH);
    }

    public static void populateTree(DynamicTree treePanel) throws SQLException {


        String cdName = "Cd";
        String bookName = "Books";


        DefaultMutableTreeNode cd, book;

        Map<CD, Integer> cdMap = null;


        cdMap = Database.cdMap();

        cd = treePanel.addObject(null, cdName);

        cdMap.entrySet().forEach(e -> {

            treePanel.addObject(cd, e.getKey() + "-" + e.getValue());

        });

        //=======================================================================

        Map<Book, Integer> bookMap = null;

        bookMap = Database.bookMap();

        book = treePanel.addObject(null, bookName);

        bookMap.entrySet().forEach(e -> {

            treePanel.addObject(book, e.getKey() + "-" + e.getValue());

        });


    }

    public void actionPerformed(ActionEvent e) {


    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {


        // Create and set up the window.
        JFrame frame = new JFrame("Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        DynamicTreeDemo newContentPane = new DynamicTreeDemo();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public void refreshJTree() {

        treePanel.reload();

    }
}


