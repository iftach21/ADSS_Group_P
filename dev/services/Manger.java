package services;

import DataAccesObject.*;
import Domain.Order;
import Domain.OrderManger;
import Domain.Supplier_Manger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manger implements ActionListener {


    private JFrame jframe;


    private JButton go_to_supplier;
    private JButton go_to_inventory;
    private JPanel buttonPanel;



    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(220, 20, 60)); // Red button background
        button.setForeground(Color.WHITE); // White text color
        button.setFocusPainted(false); // Remove focus border

        // Set font style
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button.setFont(buttonFont);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 99, 71)); // Orange hover background
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 20, 60)); // Red button background
            }
        });

        return button;
    }




    public Manger() {


        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Supplier Manager");




        JLabel label = new JLabel("Manager Menu");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Customize the font and size as desired
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        jframe.add(label, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);


        this.go_to_inventory=createStyledButton("Inventory Manger");
       this.go_to_supplier= createStyledButton("Supplier Manger");

       this.go_to_inventory.addActionListener(this);
       this.go_to_supplier.addActionListener(this);

       buttonPanel.add(this.go_to_inventory);
       buttonPanel.add(this.go_to_supplier);





        // Register the ActionListener for each button





        buttonPanel.setBackground(new Color(135, 0, 0));

        jframe.add(buttonPanel);
        jframe.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.go_to_supplier){
            //open a new frame
            Supplier_Manger_GUI supplier_manger=new Supplier_Manger_GUI();
        } else if (e.getSource()==this.go_to_inventory) {
            InventoryMangerGUI inventoryMangerGUI =new InventoryMangerGUI();




        }


    }

}
