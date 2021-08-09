package com.company;

/**
 * represent The user information.
 */
public class User
{
    protected String firstName;
    protected String lastName;
    protected String idNum;

    /**
     *
     * @param firstName user firstName
     * @param lastName user lastName
     * @param idNum user ID
     */
    public User( String firstName , String lastName ,String idNum )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNum = idNum;
    }
}
