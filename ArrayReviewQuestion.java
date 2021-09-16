/**
* Name: Aiden Wang
* Date: Sep 16, 2021
* Description: processing and outputing an array of 10 integers in the required ways
*/

import java.util.*;

public class ArrayReviewQuestion {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arr[] = new int[10];//creating the array
        for(int i = 0; i < 10; i++) {
            System.out.println("Please enter an integer: ");
            arr[i] = in.nextInt();
        }
        //a
        printAll(arr);
        //b
        printAllRev(arr);
        //c
        printAllPos(arr);
        //d
        printAllNeg(arr);
        //e
        printMin(arr);
        //f
        printAvg(arr);
    }
    //a
    public static void printAll(int[] arr){
        //print all integers in the order entered
        System.out.println("Print all numbers in order entered: ");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    //b
    public static void printAllRev(int[] arr){
        //print all integers in the reverse order entered
        System.out.println("Print all numbers in reverse order entered: ");
        for(int i = arr.length - 1; i >= 0; i--){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    //c
    public static void printAllPos(int[] arr){
        //print all positive integers
        System.out.println("Printing all positive numbers: ");
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > 0){
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
    }
    //d
    public static void printAllNeg(int[] arr){
        //print all negative integers
        System.out.println("Printing all negative numbers: ");
        for(int i = 0; i < arr.length; i++){
            if(arr[i] < 0){
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
    }
    //e
    public static void printMin(int[] arr){
        //print the minimum number of the array
        int minN = arr[0];
        for(int i = 0; i < arr.length; i++){
            if(arr[i] < minN){
                minN = arr[i];
            }
        }
        System.out.println("The smallest number is: " + minN);
    }
    //f
    public static void printAvg(int[] arr){
        //print the average value of the array
        int sum = 0;
        for(int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        System.out.println("The average value of the array is: " + (double)(sum / 10.0));
    }
}
