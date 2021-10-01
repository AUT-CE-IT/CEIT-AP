package com.company;

import java.util.LinkedHashSet;

public class Course {
    private String courseName;
    private String courseID;
    private String courseFacility;
    private int courseUnit;
    private int courseCapacity;
    protected LinkedHashSet<Student> courseStudents;

    public Course( String courseName , String courseID , String courseFacility , int courseUnit , int courseCapacity)
    {
        if( courseID.length() != 4 ) {
            System.out.println("Course ID is not valid!");
            return;
        }

        if( courseUnit < 1 || courseUnit > 3 ) {
            System.out.println("Course unit is not valid!");
            return;
        }

        setCourseName(courseName);
        setCourseID(courseID);
        setCourseFacility(courseFacility);
        setCourseUnit(courseUnit);
        setCourseCapacity(courseCapacity);
        courseStudents = new LinkedHashSet<Student>();
    }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public void setCourseID(String courseID) { this.courseID = courseID; }

    public void setCourseFacility(String courseFacility) { this.courseFacility = courseFacility; }

    public void setCourseUnit(int courseUnit) { this.courseUnit = courseUnit; }

    public void setCourseCapacity(int courseCapacity) { this.courseCapacity = courseCapacity; }

    public void setCourseStudents(LinkedHashSet<Student> courseStudents) { this.courseStudents = courseStudents; }

    public String getCourseName() { return courseName; }

    public String getCourseID() { return courseID; }

    public String getCourseFacility() { return courseFacility; }

    public int getCourseUnit() { return courseUnit; }

    public int getCourseCapacity() { return courseCapacity; }

    public LinkedHashSet<Student> getCourseStudents() { return courseStudents; }

    void addStudent(Student student)
    {
        if( courseStudents.contains(student) ) {
            System.out.println("This student is already participating in this course!");
            return;
        }

        courseStudents.add(student);
    }

    void removeStudent(Student student)
    {
        if( !courseStudents.contains(student) ) {
            System.out.println("This student is not participating in this course!");
            return;
        }

        courseStudents.remove(student);
    }

    void printCourseInformation()
    {
        System.out.println("Course name : " + courseName + " Course ID : " + courseID
                             + "\nFacility of " + courseFacility + "\nCourse capacity : " + courseCapacity
                                                          + " Course total students : " + courseStudents.size());
        System.out.println("Course Students : \n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        for (Student i : courseStudents)
            i.printStudentInformation();
    }
}
