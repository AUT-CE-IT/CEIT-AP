package com.company;

import java.util.*;

import static com.company.Library.printBorrowDetail;
import static com.company.Library.printPassedDeadlineBorrows;
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


    protected static void addlibrary( Library newLib )
    {
        if( librarySearch(newLib.name , newLib.address) )
        {
            System.out.println("library already exist!");
            return;
        }
        libraries.add( newLib );
    }

    /**
     * delete a library from library list.
     *
     * @param removeLib name of The library we are willing to delete.
     */
    protected static void removeLibrary( Library removeLib )
    {
        if( !librarySearch(removeLib.name , removeLib.address) )
        {
            System.out.println("library does not exist!");
            return;
        }
        libraries.remove(removeLib);
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

    /**
     * search for a given library
     *
     * @param name library name
     * @param add library address
     * @return boolean
     */
    protected static boolean librarySearch( String name , String add )
    {
        for( Library i : libraries )
        {
            if( i.name.equals(name) && i.address.equals(add) )
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        createLibraries();
        methodTest();
    }
}
