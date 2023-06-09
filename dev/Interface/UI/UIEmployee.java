package Interface.UI;

import javax.swing.*;

public class UIEmployee extends JFrame {
    public UIEmployee(){

        this.setTitle("Employ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420,420);
        this.setVisible(true);
        ImageIcon imageIcon=new ImageIcon("");
        this.setIconImage(imageIcon.getImage());

    }

    //for testing this:
    public static void main(String[] args){
        UIEmployee ui = new UIEmployee();
    }
}
