package com.company;

public class Student
{
    private String firsName;
    private String lastName;
    private String id;
    private int grade;

    public Student( String fname , String lname , String sID , int gd )
    {
        firsName = fname;
        lastName = lname;
        id = sID;
        grade = gd;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void print()
    {
        System.out.println("student name : " + firsName + "\nstudent last name : " + lastName + "\nstudent id : " + id + "\nstudent grade : " + grade);
    }
}
