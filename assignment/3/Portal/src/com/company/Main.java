package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Course> allCourses = new ArrayList<>();
    public static ArrayList<Student> allStudents = new ArrayList<>();

    public static void addCourse(Student student , Course course)
    {
        if( allCourses.isEmpty() || allStudents.isEmpty()) return;

        if( !allCourses.contains(course) ) {
            System.out.print("error code 404 not found!!");
            return;
        }

        if( !student.getFacility().equals(course.getCourseFacility()) ) return;

        int index = allCourses.indexOf(course) , studentIndex = allStudents.indexOf(student);

        if(allCourses.get(index).getCourseCapacity() == allCourses.get(index).courseStudents.size()) {
            System.out.println("course is full!");
            return;
        }

        allStudents.get(studentIndex).addCourse(course);
        allCourses.get(index).addStudent(student);
    }

    public static void removeCourse(Student student , Course course)
    {
        if( allCourses.isEmpty() || allStudents.isEmpty()) {
            System.out.println("empty!");
            return;
        }

        if( !allCourses.contains(course) ) {
            System.out.print("error code 404 not found!!");
            return;
        }

        if( !student.getFacility().equals(course.getCourseFacility()) ) {
            System.out.println("not participating the course!");
            return;
        }

        int index = allCourses.indexOf(course);
        int studentIndex = allStudents.indexOf(student);

        allStudents.get(studentIndex).removeCourse(course);
        allCourses.get(index).removeStudent(student);
    }

    public static void printCoursesInformation() {
        for (Course i : allCourses) {
            i.printCourseInformation();
            System.out.println();
        }
    }

    public static void portal(){
        int coursesCount , studentsCount , addAndRemoveCount;
        Scanner sc = new Scanner(System.in);
        coursesCount = sc.nextInt();
        for( int i = 0 ; i < coursesCount ; i++ )
        {
            String courseName , courseID;
            int courseCapacity , courseUnit;
            courseName = sc.next();
            courseID = sc.next();
            courseUnit = sc.nextInt();
            courseCapacity = sc.nextInt();
            Course course = new Course(courseName , courseID , "CE" , courseUnit , courseCapacity);
            allCourses.add(course);
        }

        studentsCount = sc.nextInt();
        for( int i = 0 ; i < studentsCount ; i++ )
        {
            String firstName , studentID , courseID;
            int gradeAverage , attendingCourseCount;
            firstName = sc.next();
            studentID = sc.next();
            gradeAverage = sc.nextInt();
            Student std = new Student(firstName , studentID , "CE" , gradeAverage);
            allStudents.add(std);
            attendingCourseCount = sc.nextInt();
            for(int j = 0 ; j < attendingCourseCount ; j++)
            {
                courseID = sc.next();
                if( findGivenCourse(courseID) == null ) continue;
                addCourse(std , findGivenCourse(courseID));
            }
        }

        addAndRemoveCount = sc.nextInt();
        for( int i = 0 ; i < addAndRemoveCount ; i++ )
        {
            int addAndRemoveCourseCount;
            String firstName , courseID;
            firstName = sc.next();
            addAndRemoveCourseCount = sc.nextInt();
            for( int z = 0 ; z < addAndRemoveCourseCount ; z++ )
            {
                courseID = sc.next();
                if( findGivenStudent(firstName).getCourse().contains(findGivenCourse(courseID)) )
                    removeCourse(findGivenStudent(firstName) , findGivenCourse(courseID));
                else addCourse( findGivenStudent(firstName) , findGivenCourse(courseID) );
            }
        }
        print();
    }

    public static void print()
    {
        for( Course i : allCourses )
        {
            System.out.print(i.getCourseName() + ": ");
            for( Student j : i.getCourseStudents() )
                System.out.print(j.getFirstName() + " ");
            System.out.println();
        }

        for( Student i : allStudents )
        {
            System.out.print(i.getFirstName() + ": ");
            for( Course j : i.getCourse() )
                System.out.print(j.getCourseName() + " ");
            System.out.println();
        }
    }

    public static Course findGivenCourse(String ID)
    {
        for (Course i : allCourses) {
            if(i.getCourseID().equals(ID)) return i;
        }
        return null;
    }

    public static Student findGivenStudent(String firstName)
    {
        for ( Student i : allStudents )
        {
            if( i.getFirstName().equals(firstName) )return i;
        }
        return null;
    }

    public static void main(String[] args) {
        portal();
    }
    /*
    3
    Physics 3111 3 2
    Math 3112 2 5
    AP 3113 3 3
    2
    Dara 9831001 14 2 3111 3112
    Sara 9431002 16 3 3111 3113 3112
    1
    Dara 2 3111 3113
    */
}
