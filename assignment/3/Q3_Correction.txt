package com.company;

public class Main
{
    public static void main(String[] args) {
        int[] array = {14, 19, 8, 7, 48, 16, 63, 27};
        int n = array.length;
        for (int i = 0 ; i < n; i++)
        {
            int j = i;
            while (j>0 && array[j-1]>array[j])
            {
                int key = array[j];
                array[j] = array[j-1];
                array[j-1] = key;
                j--;
            }

        }

        for (int i : array)
        {
            System.out.print(i + " ");
        }
    }
}
