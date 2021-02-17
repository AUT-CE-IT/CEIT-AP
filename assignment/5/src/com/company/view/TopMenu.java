package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 *
 * top menu constructor class extend MenuBar
 */
public class TopMenu extends JMenuBar {
    private final JMenuBar menuBar;

    public TopMenu(JFrame frame){
        this.menuBar = new JMenuBar();
        ArrayList<Component> menuItems = new ArrayList<>();
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(Color.decode("#fd6533"), 0));

        JMenu application = new JMenu("Application");
        JMenu exit = new JMenu("exit");
        JMenu option = new JMenu("option ");
        application.setIconTextGap(10);
        exit.setIconTextGap(40);
        option.setIconTextGap(40);
        application.add(option); application.add(exit);
        application.setMnemonic('A');

        JCheckBoxMenuItem fRedirect = new JCheckBoxMenuItem("follow redirect");
        JCheckBoxMenuItem darkTheme = new JCheckBoxMenuItem("dark theme");
        JCheckBoxMenuItem exitOnSystemTray = new JCheckBoxMenuItem("exit on system tray");
        exitOnSystemTray.addActionListener(actionEvent -> {
            if(exitOnSystemTray.getState()) {
                //c1 is checked
                frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
            } else {
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });

        fRedirect.setForeground(Color.decode("#545454"));
        fRedirect.setIconTextGap(20);

        darkTheme.setForeground(Color.decode("#545454"));
        darkTheme.setIconTextGap(20);

        exitOnSystemTray.setForeground(Color.decode("#545454"));
        exitOnSystemTray.setIconTextGap(20);

        option.add(fRedirect); option.add(darkTheme); exit.add(exitOnSystemTray);

        JMenu view = new JMenu("view");
        view.setMnemonic('V');

        JMenuItem fullScreen = new JMenuItem("full screen");
        fullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, KeyEvent.SHIFT_MASK));
        fullScreen.addActionListener(actionEvent -> {
            boolean isNormal = frame.getExtendedState() == JFrame.NORMAL;
            frame.setExtendedState(isNormal ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
        });
        fullScreen.setIconTextGap(25);

        JMenuItem toggle_sideBar = new JMenuItem("Toggle SideBar");
        toggle_sideBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B , InputEvent.SHIFT_DOWN_MASK));
        toggle_sideBar.setIconTextGap(25);

        view.add(fullScreen);
        view.add(toggle_sideBar);

        JMenu help = new JMenu("help");
        help.setMnemonic('H');
        JMenuItem about = new JMenuItem("about");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A , InputEvent.SHIFT_DOWN_MASK));
        JMenuItem h = new JMenuItem("help");
        h.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H , InputEvent.SHIFT_DOWN_MASK));
        about.setIconTextGap(15);
        h.setIconTextGap(15);
        help.add(about); help.add(h);

        //insert menuItems
        menuItems.add(application); menuItems.add(exit); menuItems.add(option); menuItems.add(view); menuItems.add(toggle_sideBar);
        menuItems.add(view); menuItems.add(help); menuItems.add(help); menuItems.add(about); menuItems.add(fullScreen);

        addHover(menuItems);

        menuBar.add(application);
        menuBar.add(view);
        menuBar.add(help);
    }

    private void addHover(ArrayList<Component> components){

        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                ((JMenu)mouseEvent.getSource()).doClick();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        };

        for (Component c: components) {
            if (c instanceof JMenu) {
                c.addMouseListener(ml);
                c.setForeground(Color.decode("#545454"));
            }
        }
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
