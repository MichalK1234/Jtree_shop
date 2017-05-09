package jtree;

import classes.Book;
import classes.CD;
import classes.Product;
import database.Database;
import panels.Cart;


import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Created by Michal on 14.04.2017.
 */
public class DynamicTree extends JPanel {


    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private List<String> cart = new ArrayList<>();

    private Cart c;

    public DynamicTree() {
        super(new GridLayout(1, 0));

        rootNode = new DefaultMutableTreeNode("Products");
        treeModel = new DefaultTreeModel(rootNode);

        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }


    public List<String> addToCart(String className, Product product) {

        cart.add(className + ":" + product);


        return cart;
    }

    public List<String> getCartContent() {


        return cart;
    }

    public void reload() {
        rootNode.removeAllChildren();
        treeModel.reload();
        try {
            DynamicTreeDemo.populateTree(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void putCurrentProductIntoCart() {

        if (tree.getLastSelectedPathComponent() != null) {


            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();


            if (selectedNode.getParent() != rootNode && selectedNode.getParent() != null) {


                String[] arr = String.valueOf(selectedNode).split("-");
                String[] p = arr[0].split("\\,");
                if (String.valueOf(selectedNode.getParent()).matches("Cd")) {

                    addToCart("CD", new CD(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));
                    try {
                        Database.deleteCd(Database.deleteCdByIdWhere(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                } else if (String.valueOf(selectedNode.getParent()).matches("Books")) {


                    addToCart("Book", new Book(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));//dodawanie do modellist cd:newCd()


                    try {
                        Database.deleteBook(Database.deleteBookByIdWhere(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }

                reload();


            } else {

                toolkit.beep();
            }


        } else {

            toolkit.beep();

        }

    }

    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }


        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());


        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
}
