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
        Scanner in = new Scanner(System.in); //Scanner for input
        int counter = 0; //counter for up to 10 int
        while(counter < 10){ //make sure only 10 integers can be entered
            System.out.println("Do you want to enter a integer: (yes/no)"); //ask the user to contiue enter number or not
            String check = in.next();
            if(check.equals("yes")){//if user want to enter a number, then prompt the user and increase counter by 1
                System.out.println("Please enter an integer: ");
                arr[counter] = in.nextInt();
                counter++;
            }else{//otherwise, the moethod ends
                return;
            }
        }
        //when the while loop condition is false, it mean the user has entered 10 integers already
        System.out.println("Only 10 integers can be entered!");
    }
}