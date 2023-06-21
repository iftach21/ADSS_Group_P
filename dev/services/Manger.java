package services;

import DataAccesObject.*;
import Domain.Order;
import Domain.OrderManger;
import Domain.Supplier_Manger;

import javax.swing.*;
import javax.swing.border.Border;
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



    public Manger() {

        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Supplier Manager");


        JLabel label = new JLabel("Manager Menu");
        label.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        jframe.add(label, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(173, 216, 230));

        this.go_to_inventory=createStyledButton("Inventory Manger");
        this.go_to_supplier= createStyledButton("Supplier Manger");

        this.go_to_inventory.addActionListener(this);
        this.go_to_supplier.addActionListener(this);

        buttonPanel.add(this.go_to_inventory);
        buttonPanel.add(this.go_to_supplier);


        // Register the ActionListener for each button


//        buttonPanel.setBackground(new Color(135, 0, 0));

        jframe.add(buttonPanel);
        jframe.setVisible(true);

    }


    // previous
//    private static JButton createStyledButton(String text) {
//        JButton button = new JButton(text);
//        button.setBackground(new Color(220, 20, 60)); // Red button background
//        button.setForeground(Color.WHITE); // White text color
//        button.setFocusPainted(false); // Remove focus border
//
//        // Set font style
//        Font buttonFont = new Font("Arial", Font.BOLD, 16);
//        button.setFont(buttonFont);
//
//        // Add hover effect
//        button.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                button.setBackground(new Color(255, 99, 71)); // Orange hover background
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                button.setBackground(new Color(220, 20, 60)); // Red button background
//            }
//        });
//
//        return button;
//    }


    // staled Buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton("<html><center>" + text.replaceAll("\\n", "<br>") + "</center></html>");
        button.setPreferredSize(new Dimension(180, 60));

        // font options
//        button.setFont(new Font("Harlow Solid Italic", Font.BOLD, 22));
//        button.setFont(new Font("Sitka", Font.BOLD, 20));
//        button.setFont(new Font("Segoe Print", Font.BOLD, 20));
        button.setFont(new Font("Haettenschweiler", Font.BOLD, 26));

        //color options
//        Color buttonColor = new Color(235, 235, 235);
        Color buttonColor = new Color(255, 212, 121);

        button.setBackground(buttonColor);
        button.setForeground(Color.DARK_GRAY);


        int cornerRadius = 20;
        Border roundedBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
        Border compoundBorder = BorderFactory.createCompoundBorder(roundedBorder, emptyBorder);

        // Create a rounded border using the corner radius
        Border roundedCornerBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
                g2d.dispose();
            }
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
            }
            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };

        // Apply the rounded corner border and compound border to the button
        button.setBorder(BorderFactory.createCompoundBorder(roundedCornerBorder, compoundBorder));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);


        // mark the button when the mouse is on
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });

        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.go_to_supplier){
            //open a new frame
            Supplier_Manger_GUI supplier_manger = new Supplier_Manger_GUI(true);
            jframe.setVisible(false);

        } else if (e.getSource()==this.go_to_inventory) {
            InventoryMangerGUI inventoryMangerGUI = new InventoryMangerGUI(true);
            jframe.setVisible(false);


        }
    }
}
