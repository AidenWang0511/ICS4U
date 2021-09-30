/**
* Name: Aiden Wang
* Date: Sep 28, 2021
* Description: L1 Array Exercise questions
*/

import java.util.*;

public class Level1ArrayExercises {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //quesiton 1: declare array to store 10 integers
        int[] arr = new int[10];

        //question 4
        System.out.println("Initializing array...");
        initializeArray(arr);
        System.out.println("Calling scanner method...");
        int numSize = enterFromKeyboard(arr);//keep track of the number of user entered int
        

        //question 12 and 4
        int check = -1; //int variable for users selection and check for quiting
        int sumAll = 0; //sum variable for average method
        do{
            //printing out the menu
            System.out.println("Please select the method you want to run: ");
            System.out.println(" 1 for initializeArray");
            System.out.println(" 2 for enterFromKeyboard (this will erase all previously saved numbers)");
            System.out.println(" 3 for countWhole, 4 for display");
            System.out.println(" 5 for displayReverse, 6 for sum");
            System.out.println(" 7 for average, 8 for findMax, 9 for findMin");
            System.out.println(" 10 for search");
            System.out.println(" -1 to exit code");
            check = in.nextInt();

            if(check == 1){
                initializeArray(arr);
                numSize = 0;//after initialize array, the numerb of integers user entered should be 0
            }else if(check == 2){
                numSize = enterFromKeyboard(arr);
            }else if(check == 3){
                countWhole(arr);
            }else if(check == 4){
                display(arr, numSize);
            }else if(check == 5){
                displayReverse(arr, numSize);
            }else if(check == 6){
                sumAll = sum(arr, numSize);
            }else if(check == 7){
                average(sumAll, numSize);
            }else if(check == 8){
                findMax(arr, numSize);
            }else if(check == 9){
                findMin(arr, numSize);
            }else if(check == 10){
                search(arr, numSize);
            }else{

            }

        }while(check != -1);


        
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
     * @return int - return the amount of numbers user entered
     */
    public static int enterFromKeyboard(int[] arr){
        initializeArray(arr);
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
                return counter;
            }
        }
        //when the while loop condition is false, it mean the user has entered 10 integers already
        System.out.println("Only 10 integers can be entered!");
        return counter;//return the number of elements for the display method
    }

    /**
     * display the number of positive integers in the array (Question 5)
     * Method name: countWhole
     * @param arr[] - int array
     * @return void - procedure method
     */
    public static void countWhole(int[] arr){
        int posIntCounter = 0; //int variable to count number of positive integers
        for(int i=0; i<arr.length; i++){
            if(arr[i] > 0){//check if the integer at index i is positive
                posIntCounter++;
            }
        }
        System.out.println("There are " + posIntCounter + " positive integers in the numbers you've enetered");//display the number of positive integers the user enetered
    }

    /**
     * display all numbers user entered (Question 6)
     * Method name: display
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void display(int[] arr, int numSize){
        System.out.print("The integers in order entered is ");
        for(int i=0; i<numSize; i++){
            System.out.print(arr[i] + " ");//print all numbers user entered
        }
        System.out.println();//create another line for better user experience
    }

    /**
     * display all numbers user entered in reverse order (Question 7)
     * Method name: displayReverse
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void displayReverse(int[] arr, int numSize){
        System.out.print("The integers in reverse order is ");
        for(int i=numSize-1; i>=0; i--){
            System.out.print(arr[i] + " ");//print all numbers user entered
        }
        System.out.println();//create another line for better user experience
    }

    /**
     * display sum of all numbers user entered (Question 8)
     * Method name: sum
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return int - return sum for the average method
     */
    public static int sum(int[] arr, int numSize){
        int sum = 0; //sum accumulator variable
        for(int i=0; i<numSize; i++){
            sum += arr[i];//add sum
        }
        System.out.println("The sum of all numbers you've entered: " + sum);//display sum
        return sum;
    }

    /**
     * display the average of all numbers user entered (Question 9)
     * Method name: average
     * @param sumAll - sum of user entered integers
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void average(int sumAll, int numSize){
        if(numSize == 0){//if no number was enetered, print the message that average is 0
            System.out.println("The average is: 0");
            return;
        }
        //find the average and multiply by 10 to truncate
        int average = sumAll * 10 / numSize; 
        double roundedAvg = (double)(average) / 10.0; //store int to double
        System.out.println("The average is: " + roundedAvg);
    }

     /**
     * find and display maximum of all numbers user entered (Question 10)
     * Method name: findMax
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void findMax(int[] arr, int numSize){
        int maxN = arr[0]; //variable to track of the maximum number
        if(numSize == 0){ //if user didn't enter any number, exit the method
            System.out.println("Users didn't enter any number");
            return;
        }
        for(int i=0; i<numSize; i++){//for loop through every number user entered
            if(maxN < arr[i]){//upadate maxN to bigger number
                maxN = arr[i];
            }
        }
        //display maximum
        System.out.println("The maxnimum number you've entered is: " + maxN);
    }

    /**
     * find and display minimum of all numbers user entered (Question 10)
     * Method name: findMax
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void findMin(int[] arr, int numSize){
        int minN = arr[0]; //variable to track of the maximum number
        if(numSize == 0){ //if user didn't enter any number, exit the method
            System.out.println("Users didn't enter any number");
            return;
        }
        for(int i=0; i<numSize; i++){//for loop through every number user entered
            if(minN > arr[i]){//upadate maxN to bigger number
                minN = arr[i];
            }
        }
        //display minimum
        System.out.println("The minimum number you've entered is: " + minN);
    }

    /**
     * display position of number entered (Question 11)
     * Method name: search
     * @param arr[] - int array
     * @param numSize - number of user entered int
     * @return void - procedure method
     */
    public static void search(int[] arr, int numSize){
        Scanner in = new Scanner(System.in);
        int[] pos = new int[10];//array to track positions for better display purposes
        System.out.println("Please enter an integer to search for position: ");
        int num = in.nextInt();
        int posCounter = 0; //keep track of the index of pos array
        for(int i=0; i<numSize; i++){
            if(arr[i] == num){ //if a position of the number is found, add it to the position array
                pos[posCounter] = i+1;
                posCounter++; //increase poscounter by 1 so the next position doesn't overwirte the previous one
            }
        }
        if(posCounter == 0){//if no position matches the number
            System.out.println("The number " + num + " does not exists!");
        }else{
            //printing all positions in required format
            System.out.print("The number " + num + " is found in the following positions: " + pos[0]);
            for(int i=1; i<posCounter; i++){
                System.out.print(", " + pos[i]);
            }
            System.out.println();
        }
    }

}