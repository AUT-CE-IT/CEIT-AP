package com.company;

/**
 * <h1>advanced programming Lab!</h1>
 * <div>this program provides a list of names , last names , student id , grades</div>
 * <div>joey doesnt share food!</div>
 *
 * @author BARDIA
 * @version 1
 * @since 2/22/2020
 */

public class Main {

    public static void main(String[] args) {
        Student std1 = new Student("Bardia" , "Ardakanian" , "9831072" , 18);
        Student std2 = new Student("Amir" , "khosravi" , "9831113" , 20);

        Lab Hydra = new Lab(10 , "shanbe");
        Hydra.enrollStudent(std1);
        Hydra.enrollStudent(std2);
        Hydra.print();
    }
}
