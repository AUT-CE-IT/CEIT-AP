package com.company;

/**
 * represent information about books
 */
public class Book
{
    protected String name;
    protected String author;

    /**
     *
     * @param name book title
     * @param author book author
     */
    public Book(String name  , String author)
    {
        this.name = name;
        this.author = author;
    }

}
