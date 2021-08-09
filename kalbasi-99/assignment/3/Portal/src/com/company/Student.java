package com.company;

import java.util.LinkedHashSet;

public class Student {
    private String firstName;
    private String studentID;
    private String facility;
    private int averageGrade;
    private LinkedHashSet<Course> course;

    public Student( String firstName , String studentID , String facility , int averageGrade)
    {
        if( studentID.length() != 7 ) {
            System.out.println("Student ID is not valid");
            return;
        }

        if( averageGrade < 0 || averageGrade > 20 ) {
            System.out.println("Average grade is not valid!");
        }

        setFirstName(firstName);
        setStudentID(studentID);
        setFacility(facility);
        setAverageGrade(averageGrade);
        course = new LinkedHashSet<Course>();
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setStudentID(String studentID) { this.studentID = studentID; }

    public void setFacility(String facility) { this.facility = facility; }

    public void setAverageGrade(int averageGrade) { this.averageGrade = averageGrade; }

    public void setCourse(LinkedHashSet<Course> course) { this.course = course; }

    public String getFirstName() { return firstName; }

    public String getStudentID() { return studentID; }

    public String getFacility() { return facility; }

    public int getAverageGrade() { return averageGrade; }

    public LinkedHashSet<Course> getCourse() { return course; }

    void addCourse(Course course)
    {
        if( averageGrade >= 15 && unitTotal() + course.getCourseUnit() > 20 ) {
            System.out.println("Access denied!");
            return;
        }

        if( averageGrade < 15 && averageGrade >= 10 && unitTotal() + course.getCourseUnit() > 15 ) {
            System.out.println("Access denied!");
            return;
        }

        if( averageGrade < 10 ){
            System.out.println("you cant have any courses with your current grades!");
        }

        if( facility != course.getCourseFacility() ){
            System.out.println("This course does not belong to your facility!");
        }

        if( this.course.contains(course) )
        {
            System.out.println("This course already exist!");
            return;
        }

        if( course.getCourseStudents().size() >= course.getCourseCapacity() ){
            System.out.println("this course is full!");
            return;
        }

        this.course.add(course);
    }

    private int unitTotal()
    {
        int sum = 0;
        for ( Course i : course ) {
            sum += i.getCourseUnit();
        }
        return sum;
    }

    void removeCourse(Course course)
    {
        if( !this.course.contains(course) ){
            System.out.println("you dont have this course!");
            return;
        }

        this.course.remove(course);
    }

    void printStudentInformation(){
        System.out.println("firstName : " + firstName + " StudentID : " + studentID + "\nFacility of " + facility
                                                                               + "\naverageGrade : " + averageGrade );
        System.out.println("Attending Courses :");
        for ( Course i : course )
        {
            System.out.println("Course name : " + i.getCourseName() + " Course ID : " + i.getCourseID() );
        }
    }
}
