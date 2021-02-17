package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * the Library class represent a Library in a administration system.
 * it holds list of books , list of users , list of borrowed books , address of the Library and name of the Library
 *
 * @author Bardia Ardakanian 9831072
 */
public class Library
{
    protected ArrayList<Book> books = new ArrayList<Book>();
    protected ArrayList<User> users = new ArrayList<User>();
    protected String address;
    protected String name;
    protected ArrayList<Borrow> borrow = new ArrayList<Borrow>();

    /**
     *
     * @param address the Library address
     * @param name the Library name
     */
    public Library( String address , String name )
    {
        this.address = address;
        this.name = name;
    }

    /**
     * add a new user to library user list
     *
     * @param firstName user firstName
     * @param lastName user lasrName
     * @param idNum user ID
     */
    protected void addUser( String firstName , String lastName ,String idNum )
    {
        if( userSearch(idNum) )
        {
            System.out.println("User already exist!\n");
            return;
        }

        User newUser = new User(firstName , lastName , idNum);
        users.add(newUser);
    }

    /**
     * this function will find and locate the index of given id
     * if it founds it, it will remove it from the list
     *
     * @param uID is the given id to remove
     */
    protected void deleteUser( String uID )
    {
        if( borrowUserSearch( uID ) )
        {
            System.out.println("User is in borrow list and cant be removed!\n");
            return;
        }
        int j = 0;
        for( User i : users )
        {
            if( uID.equals( i.idNum ) )
            {
                users.remove( j );
                System.out.println(uID + "has been removed.\n");
                return;
            }
            j++;
        }

        System.out.println("'" + uID + "'" + "not found!");
    }

    /**
     * search for a user with given ID and return a boolean
     *
     * @param uID given user id
     * @return boolean
     */
    boolean userSearch( String uID )
    {
        int j = 0;
        for( User i : users )
        {
            if( uID.equals( i.idNum ) )
                return true;
            j++;
        }
        return false;
    }

    /**
     * print users of a given library
     */
    protected void printUser()
    {
        System.out.println("Users are : ");
        for( User i : users )
        {
            System.out.println("User >>> FullName : " + i.firstName + " " + i.lastName + " | ID : " + i.idNum);
        }
        System.out.println();
    }

    /**
     * add a new book to library
     *
     * @param name title of the book
     * @param author writer of the book
     */
    protected void addBook( String name ,String author )
    {
        if( bookSearch(name) )
        {
            System.out.println("book already exist in the Library!\n");
            return;
        }

        Book newBook = new Book(name , author);
        books.add(newBook);
    }

    /**
     * delete a book from library
     *
     * @param bname books name
     */
    protected void deleteBook( String bname )
    {
        int j = 0;
        for( Book i : books )
        {
            if( bname.equals( i.name ) )
            {
                books.remove( j );
                System.out.println("'" + bname + "'" + "has been removed.\n");
                return;
            }
            j++;
        }

        System.out.println(bname + "not found\n");
    }

    /**
     * search for a given book in library
     *
     * @param bname books name
     * @return boolean
     */
    boolean bookSearch( String bname )
    {
        int j = 0;
        for( Book i : books )
        {
            if( bname.equals( i.name ) )
                return true;
            j++;
        }
        return false;
    }

    /**
     * print books information of a library
     */
    protected void printBook()
    {
        System.out.println("Books are : ");
        for ( Book i : books )
        {
            System.out.println("Book >>> Title : " + i.name + " | Author : " + i.author);
        }
        System.out.println();
    }

    /**
     * search for the book to borrow.
     * then search for the borrower.
     * then add the user to borrower list and remove the book from library books.
     *
     * @param book book to borrow
     * @param user borrower
     * @param isuuedDate current date
     * @param deadline bringing back deadline date
     */
    protected void borrowBook( Book book , User user , Date isuuedDate , Date deadline )
    {
        if( !bookSearch( book.name ) )
            System.out.println("book not found!\n");
        if( !userSearch( user.idNum ) )
            System.out.println("user not found!\n");
        //The book and user exist or not
        int cmp = isuuedDate.compareTo(deadline);
        if( cmp >= 0 )
        {
            System.out.println("deadline cant be before or equal the current date!\n");
            return;
        }
        Borrow newborrow = new Borrow( user , book , isuuedDate ,deadline);
        borrow.add( newborrow );
        deleteBook( book.name );
    }

    /**
     * delete a borrowed book
     */
    protected void deleteBorrow( Borrow deleteBorrow )
    {
        int j = 0;
        for( Borrow i : borrow )
        {
            if( i == deleteBorrow )
            {
                borrow.remove( j );
                return;
            }
            j++;
        }

        System.out.println("borrower not found\n");
    }

    /**
     * search for borrower
     *
     * @param bID borrower ID
     * @return boolean
     */
    boolean borrowUserSearch(String bID)
    {
        int j = 0;
        for( Borrow i : borrow )
        {
            if( bID.equals( i.user.idNum ) )
                return true;
            j++;
        }
        return false;
    }

    /**
     * search for borrowed book
     *
     * @param bname book name
     * @return boolean
     */
    boolean borrowBookSearch(String bname)
    {
        int j = 0;
        for( Borrow i : borrow )
        {
            if( bname.equals( i.book.name ) )
                return true;
            j++;
        }
        return false;
    }

    /**
     * check if the book is from this library or not.
     * if it does belong to the library return book to library and remove the borrower from borrow list
     * @param borrow
     */
    protected void giveBackBook( Borrow borrow )
    {
        if( !bookSearch(borrow.book.name) && !borrowBookSearch(borrow.book.name)  )
        {
            System.out.println("This book does not belong to this Library");
            return;
        }

        if( !borrowBookSearch(borrow.book.name) )
        {
            System.out.println("this book was not borrowed!\n");
            return;
        }

        System.out.println();
        books.add( borrow.book );
        deleteBorrow( borrow );
    }

    /**
     * print borrowed book information and the borrower information.
     * plus it prints the issued date and deadline date and show the remaining time.
     */
    protected void printBorrow()
    {
        if( borrow.isEmpty() )
        {
            System.out.println("borrow list is empty!");
            return;
        }

        for( Borrow i : borrow )
        {
            System.out.println("Borrower => FullName : " + i.user.firstName + " " + i.user.lastName + " | ID : " + i.user.idNum);
            System.out.println("Book => Title : " + i.book.name + " | Author : " + i.book.author);
            System.out.println("IssuedDate : " + i.issuedDate.toString());
            System.out.println("Deadline : " + i.deadline.toString());

            long days = getDateDiff (i.issuedDate, i.deadline, TimeUnit.DAYS);
            long hours = getDateDiff (i.issuedDate, i.deadline, TimeUnit.HOURS);
            long minutes = getDateDiff (i.issuedDate, i.deadline, TimeUnit.MINUTES);

            System.out.println("Remaining => days : " + days + " , hours : " + (hours - days * 24 ) + " , Minutes : " + (minutes - hours * 60) + "\n");
        }
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillies = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
