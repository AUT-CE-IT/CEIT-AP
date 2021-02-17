package com.company;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static com.company.Main.*;

public class TestLibrarySystem
{
    protected static void createLibraries()
    {
        for( int i = 0 ; 0 < 1 ; i++ )
        {
            Scanner sc = new Scanner(System.in);
            System.out.printf("Enter the %dth library information : " , i + 1);
            System.out.print("\nlibrary name : ");
            String libname = sc.nextLine();
            System.out.print("\nlibrary address : ");
            String libaddress = sc.nextLine();
            addlibrary(libaddress , libname);

            System.out.println("enter users information");
            for( int j = 0 ; j < 4 ; j++ )
            {
                System.out.print("user first name : ");
                String fname = sc.nextLine();
                System.out.print("\nuser last name : ");
                String lname = sc.nextLine();
                System.out.print("\nuser ID : ");
                String id = sc.nextLine();
                libraries.get(i).addUser(fname , lname , id);
            }

            System.out.println("enter books information");
            for( int k = 0 ; k < 1 ; k++ )
            {
                System.out.print("book title : ");
                String title = sc.nextLine();
                System.out.print("\nbook author : ");
                String author = sc.nextLine();
                libraries.get(i).addBook(title , author);
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
        Calendar calendar = new GregorianCalendar(year, month , day , hour , minute , second);
        Date now = new Date();
        Date date = calendar.getTime();
        System.out.println(date);
        System.out.println(now);
        libraries.get(0).printBook();
        libraries.get(0).printUser();
        libraries.get(0).borrowBook(libraries.get(0).books.get(0) , libraries.get(0).users.get(0) , now , date);
        String id = sc.next();
        System.out.print("\nenter user you are willing to delete");
        libraries.get(0).deleteUser( id );
        libraries.get(1).borrowBook(libraries.get(1).books.get(0) , libraries.get(1).users.get(0) , now , date);
        libraries.get(1).deleteBorrow( libraries.get(0).borrow.get(0) );
        libraries.get(0).deleteBorrow( libraries.get(0).borrow.get(0) );
        String libra = sc.next();
        System.out.print("\nenter library you are willing to delete");
        removeLibrary(libra);
        printAllLibraries();
        libraries.get(0).printBorrow();
        System.out.print("\nenter user you are willing to delete");
        id = sc.next();
        libraries.get(1).deleteUser(id);
    }
}
