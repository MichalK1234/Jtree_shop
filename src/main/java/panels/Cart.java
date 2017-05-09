package panels;


import jtree.DynamicTreeDemo;
import classes.Book;
import classes.CD;
import database.Database;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 06.04.2017.
 */
public class Cart extends JPanel {

    private List<String> productsInCart = new ArrayList<>();

    private static JList<String> list = new JList<>();
    private static DefaultListModel listModel;

    private JButton btnRemove;
    private JTextField tfPriceSum;
    private JLabel jlPrice;

    private DynamicTreeDemo dynamicTreeDemo;


    public Cart(List<String> listCart, DynamicTreeDemo dynamicTreeDemo) {

        super(new GridBagLayout());


        this.dynamicTreeDemo = dynamicTreeDemo;

        setCart(listCart);

        jlPrice = new JLabel("Total price: ");
        tfPriceSum = new JTextField(String.valueOf(calculatePrice()));
        tfPriceSum.setEditable(false);


        listModel = new DefaultListModel();
        list = new JList<>(listModel);

        if (!listCart.isEmpty()) {
            listCart.forEach(e -> {
                listModel.addElement(e);
            });
        } else {
            listModel.addElement("Your cart is empty");


        }


        JScrollPane pane = new JScrollPane(list);


        btnRemove = new JButton("remove");

        btnRemove.addActionListener(e -> {

            if (productsInCart.isEmpty() || productsInCart == null) {

                JOptionPane.showMessageDialog(null, "Products list is empty");
                return;
            } else if (list.getSelectedValue() == null) {

                JOptionPane.showMessageDialog(null, "Choose some product");
                return;
            }

            String ss = String.valueOf(list.getSelectedValue());
            int idx = list.getSelectedIndex();

            String[] arr = ss.split(":");

            String[] arr2 = arr[1].split(",");

            if (arr[0].matches("Book")) {

                try {


                    Database.insertBook(new Book(arr2[0], arr2[1], arr2[2], Double.parseDouble(arr2[3]), Integer.parseInt(arr2[4])));

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            } else if (arr[0].matches("CD")) {


                try {

                    Database.insertCd(new CD(arr2[0], arr2[1], arr2[2], Double.parseDouble(arr2[3]), Integer.parseInt(arr2[4])));


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }


            productsInCart.remove(idx);
            if (!productsInCart.isEmpty()) {

                setCart(productsInCart);
                listModel.clear();
                productsInCart.forEach(ee -> {

                    listModel.addElement(ee);

                });


            } else {
                listModel.clear();
                listModel.addElement("Your cart is empty");
            }


            tfPriceSum.setText(String.valueOf(calculatePrice()));
            list.updateUI();
            tfPriceSum.updateUI();
            dynamicTreeDemo.refreshJTree();


        });


        JPanel fields = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFields = new GridBagConstraints();

        gbcFields.gridx = 0;
        gbcFields.gridy = 0;
        fields.add(jlPrice, gbcFields);

        gbcFields.gridx = 1;
        gbcFields.gridy = 0;
        fields.add(tfPriceSum, gbcFields);

        GridBagConstraints gbcMain = new GridBagConstraints();

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        add(list, gbcMain);

        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        add(fields, gbcMain);

        gbcMain.gridx = 0;
        gbcMain.gridy = 2;
        add(btnRemove, gbcMain);

    }


    public double calculatePrice() {


        double priceSum = 0;

        for (String s : productsInCart) {


            String[] arr = s.split(",");

            priceSum += Double.parseDouble(arr[arr.length - 2]);
        }

        return priceSum;

    }


    public void setCart(List<String> cartContent) {

        this.productsInCart = cartContent;

    }


}
