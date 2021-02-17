package com.company;

import com.company.model.*;
import com.company.view.*;
import com.company.view.*;
import java.io.IOException;

/**
 * @author BARDIA
 * @version 0.0
 *
 * com.company.view :: JFrame and GUI application
 * com.company.model :: logical part of application
 * com.company.terminal :: a semi terminal for jURL command
 */
public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        new MainFrame().run();
        //new SemiTerminal().run();
        //new HttpConnection().start();
    }
}
