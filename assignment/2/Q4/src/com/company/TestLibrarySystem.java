package com.company;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static com.company.Library.printPassedDeadlineBorrows;
import static com.company.Main.*;

public class TestLibrarySystem
{
    protected static void createLibraries()
    {
        for( int i = 0 ; i < 2 ; i++ )
        {
            Scanner sc = new Scanner(System.in);
            System.out.printf("Enter the %dth library information : " , i + 1);
            System.out.print("\nlibrary name : ");
            String libname = sc.nextLine();
            System.out.print("\nlibrary address : ");
            String libaddress = sc.nextLine();
            Library lib = new Library(libaddress , libname);
            addlibrary(lib);

            System.out.println("enter users information");
            for( int j = 0 ; j < 4 ; j++ )
            {
                System.out.print("user first name : ");
                String fname = sc.nextLine();
                System.out.print("\nuser last name : ");
                String lname = sc.nextLine();
                System.out.print("\nuser ID : ");
                String id = sc.nextLine();
                User us = new User(fname , lname , id);
                libraries.get(i).addUser(us);
            }

            System.out.println("enter books information");
            for( int k = 0 ; k < 4 ; k++ )
            {
                System.out.print("book title : ");
                String title = sc.nextLine();
                System.out.print("\nbook author : ");
                String author = sc.nextLine();
                Book b = new Book(title , author);
                libraries.get(i).addBook(b);
            }
        }
    }

    protected static void methodTest()
    {
        int year , month , day , hour , minute , second;
        Scanner sc = new Scanner(System.in);
        System.out.print("deadline year : ");
        year = sc.nextInt();
        System.out.println();
        System.out.print("deadline month : ");
        month = sc.nextInt();
        System.out.println();
        System.out.print("deadline day : ");
        day = sc.nextInt();
        System.out.println();
        System.out.print("deadline hour : ");
        hour = sc.nextInt();
        System.out.println();
        System.out.print("deadline minute : ");
        minute = sc.nextInt();
        System.out.println();
        System.out.print("deadline second : ");
        second = sc.nextInt();
        System.out.println();

        String fmame = "bardia", lname = "ak" , uid = "9831072" , btitle = "inferno" , bauthor = "dan brown";
        User u1 = new User(fmame , lname , uid);
        Book b1 = new Book(btitle , bauthor);
        Book b2 = new Book("btitle" , "bauthor");
        Book b3 = new Book("aa" , "aa");
        Date curr = new Date();
        month--;
        Calendar calendar = new GregorianCalendar(year, month, day , hour , minute , second);
        Date now = new Date();
        Date date = calendar.getTime();
        System.out.println(date);
        System.out.println(now);
        Library lib = new Library("22th shelf" , "lib 1");
        Library lib2 = new Library("100th shelf" , "lib 2");
        addlibrary(lib);
        addlibrary(lib2);
        libraries.get(0).addUser(u1);
        libraries.get(0).addBook(b1);
        libraries.get(0).addBook(b2);
        libraries.get(0).addUser(u1);//اضافه کردن کاربر یا کتاب تکراری به سیستم کتابخانه .
        libraries.get(0).addBook(b1);//اضافه کردن کاربر یا کتاب تکراری به سیستم کتابخانه .
        printAllLibraries();
        libraries.get(0).printUser();
        libraries.get(0).printBook();
        Borrow borrow1 = new Borrow(u1 , b1 , curr , date);
        Borrow borrow2 = new Borrow(u1 , b3 , curr , date);
        libraries.get(0).borrowBook(borrow1);
        libraries.get(0).borrowBook(borrow1);
        libraries.get(1).borrowBook(borrow2);
        libraries.get(0).borrowBook(borrow2);
        libraries.get(0).printBorrow();
        libraries.get(1).printBorrow();
        libraries.get(0).deleteUser(u1); //حذف کاربری که کتابی گرفته که هنوز پس ندادهحذف کاربری که کتابی گرفته که هنوز پس نداده
        libraries.get(0).printUser();
        libraries.get(0).printBook();
        libraries.get(1).giveBackBook(borrow1);
        libraries.get(0).giveBackBook(borrow1); //پس دادن کتابی که از کتابخانه x گرفته شده به کتابخانه
        printPassedDeadlineBorrows();
        removeLibrary(libraries.get(0));
        printAllLibraries();
    }
}
