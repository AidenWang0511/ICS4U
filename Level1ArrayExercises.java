/**
* Name: Aiden Wang
* Date: Sep 28, 2021
* Description: L1 Array Exercise questions
*/

import java.util.*;

public class Level1ArrayExercises {
    public static void main(String[] args) {
        //quesiton 1: declare array to store 10 integers
        int[] arr = new int[10];

        //question 3
        System.out.println("Initializing array...");
        initializeArray(arr);
        System.out.println("Calling scanner method...");
        enterFromKeyboard(arr);
        System.out.println("Printing array...");
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }


        
    }

    /**
     * initialize every element of the int array to -1 (Question 2)
     * Method name: initializeArray
     * @param arr[] - int array
     * @return void - procedure method
     */
    public static void initializeArray(int[] arr){
        for(int i=0; i<arr.length; i++){
            arr[i] = -1; //set element to -1
        }
    }

    /**
     * Store 10 integers user entered in the array (Question 3)
     * Method name: enterFromKeyboard
     * @param arr[] - int array
     * @return void - procedure method
     */
    public static void enterFromKeyboard(int[] arr){
        Scanner in = new Scanner(System.in);
        //prompt user for 10 integers
        for(int i=0; i<arr.length; i++){
            System.out.println("Please enter an integer: ");
            arr[i] = in.nextInt(); 
        }
    }
    
}