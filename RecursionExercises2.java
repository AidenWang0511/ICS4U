/**
* Name: Aiden Wang
* Date: Oct 26, 2021
* Description: questions from more Recursion exercises
*/

import java.util.*;

public class RecursionExercises2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a number to print HA: ");
        int n = in.nextInt();
        printHA(n);
        System.out.println();
        System.out.println("Enter size of an int array: ");
        int size = in.nextInt();
        int[] arr = new int[size];
        System.out.println("Enter the array: ");
        for (int i = 0; i < size; i++) {
            arr[i] = in.nextInt();
        }
        System.out.println("The sum of the array is: " + sumArray(arr, 0));
        System.out.println("Please enter the number of fibonacci numbers you want to print:");
        n = in.nextInt();
        printFib(n, 1, 0, 0);

    }

    /**
     * recusively print a string n times
     * method name: printHA
     * @param n the number of times to print the string
     * @return void - procedure method
     */
    public static void printHA(int n) {
        if (n == 0) {
            return;
        }
        System.out.print("HA ");
        printHA(n - 1);
    }

    /**
     * recursilvely add all the numbers in an array
     * @param arr the array of numbers
     * @param index current index of the array to process
     * @return int - the sum of the array
     */
    public static int sumArray(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        return arr[index] + sumArray(arr, index + 1);
    }

    /**
     * recursively print first n numbers of the fibonacci sequence
     * method name: printFib
     * @param n the number of numbers to print
     * @param cur the current number that is being printed
     * @param prev the previous number that is being printed
     * @param curIndex the current index of the sequence
     * @return void procedure method
     */
    public static void printFib(int n, int cur, int prev, int curIndex) {
        if (curIndex == n) {
            return;
        }
        System.out.print(cur + " ");
        printFib(n, prev + cur, cur, curIndex + 1);
    }

}
