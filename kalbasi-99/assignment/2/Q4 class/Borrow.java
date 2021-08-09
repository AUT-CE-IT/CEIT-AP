package com.company;

import java.util.Date;

/**
 * represent the information about a borrow from library
 */
public class Borrow
{
    protected User user;
    protected Book book;
    protected java.util.Date deadline;
    protected java.util.Date issuedDate;

    /**
     *
     * @param user user object
     * @param book book object
     * @param issuedDate current date
     * @param deadline deadline date
     */
    public Borrow(User user , Book book , Date issuedDate , Date deadline )
    {
             this.book = book;
             this.user = user;
             this.issuedDate = issuedDate;
             this.deadline = deadline;
    }
}
