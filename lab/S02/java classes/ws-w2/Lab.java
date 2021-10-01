package com.company;

public class Lab {
    private Student[] students;
    private int avg;
    private String day;
    private int capacity;
    private int currentSize = 0;

    /**
     *
     * @param cap capacity of the lab
     * @param d date of the session
     */

    public Lab(int cap, String d) {
        capacity = cap;
        day = d;
        students = new Student[capacity];
    }

    /**
     *
     * @param std set the current size of the lab
     */

    public void enrollStudent(Student std) {
        if (currentSize < capacity) {
            students[currentSize] = std;
            currentSize++;
        } else {
            System.out.println("Lab is full!");
        }
    }

    /**
     * this method prints the student list and awful grades:))
     * haha!
     */
    public void print()
    {
        System.out.println("lab class students : ");
        for( int i = 0 ; i < currentSize ; i++ )
        {
            students[i].print();
        }
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public int getAvg() {
        return avg;
    }

    /**
     * this method calculate the average grade of students!
     * its usually lower than 10..
     * dumbasses!
     */
    public void calculateAvg()
    {
        avg = 0;

        for( Student std : students )
        {
            avg += std.getGrade();
        }

        avg /= currentSize;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
