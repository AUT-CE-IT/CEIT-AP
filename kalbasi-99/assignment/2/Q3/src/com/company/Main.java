package com.company;

/**
 * this method uses something like merge sort and sort array from highest number to lowest
 *
 * @author bardia ardakanian 9831072
 */
class Main
{
    static int part(int[] arr, int low, int high)
    {
        int pivot = arr[high], i = low;
        {
            for (; low < high; low++) {
                if (arr[i] > pivot) {
                    int temp = arr[i];
                    arr[i] = arr[low];
                    arr[low] = temp;
                    i++;
                }
            }
            int temp = arr[i];
            arr[i] = arr[high];
            arr[high] = temp;
        }
        return i;
    }

    static void sort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int p = part(arr, low, high);
            //System.out.println("one");
            sort(arr, low, p-1);
            sort(arr, p+1, high);
        }
    }

    static void print(int... arr)
    {
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();
    }

    // Driver program
    public static void main(String args[])
    {
        int[] a = {10, 7, 8, 9, 1, 5};
        int n = a.length;
        sort(a, 0, n-1);
        print(a);
    }
} 