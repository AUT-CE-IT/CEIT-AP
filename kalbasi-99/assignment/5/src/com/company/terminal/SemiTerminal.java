package com.company.terminal;

import javax.swing.*;
import java.awt.*;

//TODO
// complete terminal
public class SemiTerminal implements Runnable {
    private JFrame frame = new JFrame("Terminal");
    private JTextArea txtArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane();
    private Font font = new Font("SansSerif", Font.PLAIN, 15);

    public SemiTerminal() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.setSize(new Dimension(800 , 400));
        frame.setLocation(new Point(200 , 200));
        scrollPane.setViewportView(txtArea);
        txtArea.setBackground(Color.BLACK);
        //txtArea.addKeyListener(new KeyListener());
        txtArea.setFont(font);
        txtArea.setForeground(Color.green);
        //disableArrowKeys(txtArea.getInputMap());
        frame.setVisible(true);
    }

    @Override
    public void run() {

    }
}