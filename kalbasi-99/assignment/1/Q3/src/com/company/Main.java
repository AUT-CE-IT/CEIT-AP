package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("olgo string is : ");
        String olgo = sc.nextLine();

        System.out.print("str string is : ");
        String str = sc.nextLine();

        int i = 0, j = 0;
        int strLen = olgo.length();
        int lenght = olgo.length();
        int temp = 1;
        int stringLen = str.length();

        for (char s : olgo.toCharArray()) {

            if( s == '!' )
            {
                if( str.charAt(j - 1) == str.charAt(j) )
                {
                    System.out.println("true");
                    return;
                }
            }

            String check = null;
            if (s == '*') {
                check = olgo.substring(0, i);
                olgo = olgo.substring(i + 1, strLen); //recursive

                if (!str.contains(check)) { break; }

                int index = str.indexOf(check);
                str = str.substring(index + check.length(), stringLen);
                i = -1;
                strLen = olgo.length();
                stringLen = str.length();
            }

            if (temp == lenght) {
                check = olgo.substring(0, i + 1);
                olgo = olgo.substring(i + 1, strLen); //recursive

                if (!str.contains(check)) { break; }

                if( str.substring(stringLen - strLen ,stringLen).equals(check))
                {
                    System.out.println("true");
                    return;
                }
                int index = str.indexOf(check);
                str = str.substring(index + check.length(), stringLen);
                i = -1;
                strLen = olgo.length();
                stringLen = str.length();
            }

            if (strLen == 0 && stringLen == 0)
            {
                System.out.println("true");
                return;
            }
            if (olgo.length() == 0 && check.length() == 0 && stringLen != 0)
            {
                System.out.println("true");
                return;
            }
            i++;
            j++;
            temp++;

        }
        System.out.println("false!");
    }
}
