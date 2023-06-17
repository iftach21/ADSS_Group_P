package services;

import Domain.Supplier_Manger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manger implements ActionListener {


    private JFrame jframe;


    private  JButton print_all_suppliers;
    private  JButton print_supplierby_id;

    private  JPanel buttonPanel;

    private Supplier_Manger supplier_manger;

    public  Manger(){


        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Supplier Manager");

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        addnewSupplierButton = createStyledButton("Add new Supplier");
        removeSupplierButton = createStyledButton("Remove Supplier");
        UpdateContactToSupplier = createStyledButton("Update Contact to Supplier");
        addItemtoSupplier = createStyledButton("Add Item to Supplier");
        removeItemFromSupplier = createStyledButton("Remove Item from Supplier");
        UpdateitemonContract = createStyledButton("Update Item on Contract");
        print_all_suppliers = createStyledButton("Present All Suppliers");
        print_supplierby_id = createStyledButton("Get Supplier by ID");

        // Register the ActionListener for each button
        addnewSupplierButton.addActionListener(this);
        removeSupplierButton.addActionListener(this);
        UpdateContactToSupplier.addActionListener(this);
        addItemtoSupplier.addActionListener(this);
        removeItemFromSupplier.addActionListener(this);
        UpdateitemonContract.addActionListener(this);
        print_all_suppliers.addActionListener(this);
        print_supplierby_id.addActionListener(this);

        buttonPanel.add(addnewSupplierButton);
        buttonPanel.add(removeSupplierButton);
        buttonPanel.add(UpdateContactToSupplier);
        buttonPanel.add(addItemtoSupplier);
        buttonPanel.add(removeItemFromSupplier);
        buttonPanel.add(UpdateitemonContract);
        buttonPanel.add(print_all_suppliers);
        buttonPanel.add(print_supplierby_id);

        jframe.add(buttonPanel);
        jframe.setVisible(true);
        this.supplier_manger=new Supplier_Manger();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
