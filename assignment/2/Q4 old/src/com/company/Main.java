package com.company;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.company.TestLibrarySystem.createLibraries;
import static com.company.TestLibrarySystem.methodTest;

/**
 *
 * the Library class represent a Library in a administration system.
 * it holds list of books , list of users , list of borrowed books , address of the Library and name of the Library
 *
 * @author Bardia Ardakanian 9831072
 */

public class Main {
    protected static ArrayList<Library> libraries = new ArrayList<Library>();

    /**
     * create a new library and add it to library list
     *
     * @param address is the address of library
     * @param name in the name of the library
     */
    protected static void addlibrary(String address, String name)
    {
        Library newLib = new Library( address , name );
        libraries.add( newLib );
    }

    /**
     * delete a library from library list.
     *
     * @param removeLib name of The library we are willing to delete.
     */
    protected static void removeLibrary( String removeLib )
    {
        int j = 0;
        for( Library i : libraries )
        {
            if( removeLib.equals( i.name ) )
            {
                libraries.remove( j );
                System.out.println(removeLib + "has been removed.");
                return;
            }
            j++;
        }

        System.out.println("'" + removeLib + "'" + "not found!");
    }

    /**
     * print all library names and locations
     */
    protected static void printAllLibraries()
    {
        System.out.println("Libraries are : ");
        for ( Library i : libraries )
        {
            System.out.println("name : " + i.name + " | address : " + i.address );
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //month decrease by one it self
        /*
        Calendar calendar = new GregorianCalendar(2021, 1 , 25 , 14 , 20 , 33);
        Date now = new Date();
        Date date = calendar.getTime();
        System.out.println(date);
        System.out.println(now);
         */
        createLibraries();
        //methodTest();
    }
}
