package com.company.view;

import javax.swing.*;
import java.awt.*;

/**
 * creates the mainFrame of gui
 */
public class MainFrame extends JFrame implements Runnable{

    public void createMainFrame(){
        setJMenuBar(new TopMenu(this).getMenuBar());
        this.setLayout(new GridLayout(1 , 3));
        this.add(new RequestPanel().getSplitPane());

        ImageIcon img = new ImageIcon(".\\icon\\icon.png");
        this.setIconImage(img.getImage());
        setSize(1300, 750);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Postman");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        createMainFrame();
    }
}
