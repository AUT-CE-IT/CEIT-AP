package com.company;

import java.util.Scanner;

class SecondClass
{
    private int MyInt;

    public SecondClass(int i) {
        MyInt = i;
    }

    public void getMyInt()
    {
        Scanner sc = new Scanner(System.in);
        MyInt = sc.nextInt();
    }
}

public class Main
{
    public static void main(String[]args)
    {
        System.out.println("My First class");
        SecondClass sec = new SecondClass(2);
    }
}
