package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.company.Main.libraries;

/**
 *
 * the Library class represent a Library in a administration system.
 * it holds list of books , list of users , list of borrowed books , address of the Library and name of the Library
 *
 * @author Bardia Ardakanian 9831072
 */
public class Library
{
    protected ArrayList<Book> books;
    protected ArrayList<User> users;
    protected String address;
    protected String name;
    protected ArrayList<Borrow> borrow;

    /**
     *
     * @param address the Library address
     * @param name the Library name
     */
    public Library( String address , String name )
    {
        this.address = address;
        this.name = name;
        borrow = new ArrayList<Borrow>();
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
    }

    /**
     * add a new user to library user list
     *
     * @param newUser is the given user to add
     */
    protected void addUser( User newUser )
    {
        if( userSearch(newUser.idNum) )
        {
            System.out.println("user already exist!");
            return;
        }
        users.add(newUser);
    }

    /**
     * this function will find and locate the index of given id
     * if it founds it, it will remove it from the list
     *
     * @param duser is the given user to remove
     */
    protected void deleteUser( User duser )
    {
        if( borrowUserSearch(duser.idNum) )
        {
            System.out.println("user have a debt!");
            return;
        }

        if( !userSearch(duser.idNum) )
        {
            System.out.println("user does not exist!");
            return;
        }
        users.remove(duser);
    }

    /**
     * search for a user with given ID and return a boolean
     *
     * @param uID given user id
     * @return boolean
     */
    boolean userSearch( String uID )
    {
        for( User i : users )
        {
            if( uID.equals( i.idNum ) )
                return true;
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
     * @param newBook is the given book to add
     */
    protected void addBook( Book newBook )
    {
        if( bookSearch(newBook.name , newBook.author) )
        {
            System.out.println("book already exist!");
            return;
        }
        books.add(newBook);
    }

    /**
     * delete a book from library
     *
     * @param dbook given book to remove
     */
    protected void deleteBook( Book dbook )
    {
        if( borrowBookSearch(dbook.name) )
        {
            System.out.println("someone have borrowed this book!");
            return;
        }

        if( !bookSearch(dbook.name , dbook.author) )
        {
            System.out.println("book does not exist!");
            return;
        }
        books.remove(dbook);
    }

    /**
     * search for a given book in library
     *
     * @param bname books name
     * @return boolean
     */
    boolean bookSearch( String bname , String bauthor)
    {
        for( Book i : books )
        {
            if( bname.equals( i.name ) && bauthor.equals( i.author ))
                return true;
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
     *search for the book to borrow.
     *then search for the borrower.
     *then add the user to borrower list and remove the book from library books.
     *
     * @param booktoborrow borrow the following book
     */
    protected void borrowBook( Borrow booktoborrow ) {
        if (!bookSearch(booktoborrow.book.name , booktoborrow.book.author)) {
            System.out.println("book not found!\n");
            return;
        }

        if (!userSearch(booktoborrow.user.idNum)) {
            System.out.println("user not found!\n");
            return;
        }

        if( borrowBookSearch(booktoborrow.book.name) )
        {
            System.out.println("this book has been borrowed to someone!");
            return;
        }


        int cmp = booktoborrow.issuedDate.compareTo(booktoborrow.deadline);
        if (cmp >= 0) {
            System.out.println("deadline cant be before or equal the current date!\n");
            return;
        }

        borrow.add(booktoborrow);
        books.remove(booktoborrow.book);
    }

    /**
     * delete a borrowed book
     */
    protected void deleteBorrow( Borrow deleteBorrow )
    {
        borrow.remove(deleteBorrow);
    }

    /**
     * search for borrower
     *
     * @param bID borrower ID
     * @return boolean
     */
    boolean borrowUserSearch(String bID)
    {
        for( Borrow i : borrow )
        {
            if( bID.equals( i.user.idNum ) )
                return true;
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
        for( Borrow i : borrow )
        {
            if( bname.equals( i.book.name ) )
                return true;
        }
        return false;
    }

    /**
     * check if the book is from this library or not.
     * if it does belong to the library return book to library and remove the borrower from borrow list
     * @param borrow
     */
    protected void giveBackBook(Borrow borrow)
    {
        if( !bookSearch(borrow.book.name , borrow.book.author) && !borrowBookSearch(borrow.book.name)  )
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
            printBorrowDetail(i);
        }
    }

    /**
     * print detail such as firstname , lastname , id , book title , book author
     * issued date , deadline date and remaining time.
     *
     * @param i given borrow to print its detail
     */
    public static void printBorrowDetail( Borrow i )
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

    /**
     * print passed deadline borrows
     */
    public static void printPassedDeadlineBorrows()
    {
        int j = 0;
        Date issuedDate = new Date();
        for( Library i : libraries )
        {
            j = 0;
            if( i.borrow.isEmpty() ) continue;
            int cmp = issuedDate.compareTo(i.borrow.get(j).deadline);
            if (cmp > 0) {
                System.out.print("deadline has passed!\n");
                printBorrowDetail(i.borrow.get(j));
            }
            j++;
        }
    }

    /**
     * return difference between two times as its milliseconds
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillies = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
