/**
* Name: Aiden Wang
* Date: Oct 12, 2021
* Description: L1 Array Exercise questions
*/

import java.util.*;

public class ArrayListExercises {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> num = new ArrayList<Integer>();

        enterFromKeyboard(num);
        //display menu
        int check = -1;
        do{
            //printing out the menu
            System.out.println("Please select the method you want to run: ");
            System.out.println(" 1 for enterFromKeyboard");
            System.out.println(" 2 for countWhole, 3 for display");
            System.out.println(" 4 for displayReverse, 5 for sum");
            System.out.println(" 6 for average, 7 for findMax, 8 for findMin");
            System.out.println(" 9 for search, 10 for bubble sort");
            System.out.println(" -1 to exit code");
            check = in.nextInt();

            if(check == 1){
                enterFromKeyboard(num);
            }else if(check == 2){
                countWhole(num);
            }else if(check == 3){
                display(num);
            }else if(check == 4){
                displayReverse(num);
            }else if(check == 5){
                sum(num);
            }else if(check == 6){
                average(num);
            }else if(check == 7){
                findMax(num);
            }else if(check == 8){
                findMin(num);
            }else if(check == 9){
                search(num);
            }else if(check == 10){
                bubbleSort(num);
            }else{

            }

        }while(check != -1);
    }

    /**
     * Prompt up to 10 integers from user (Question 2)
     * Method name: enterFromKeyboard
     * @param num - Integer ArrayList
     * @return void - procedure method
     */

    public static void enterFromKeyboard(ArrayList<Integer> num){
        int counter = 0;
        Scanner myScanner = new Scanner(System.in);
        while(counter<10){
            System.out.println("Please enter a integer: ");
            int user = myScanner.nextInt();
            num.add(user);
            counter++;
            System.out.println("Do you want to continue: (yes/no)");
            String check = myScanner.next();
            if(check.equals("no")){
                break;
            }
        }
    }

    /**
     * display the number of positive integers in the ArrayList (Question 4)
     * Method name: countWhole
     * @param num - ArrayList of integers
     * @return void - procedure method
     */

    public static void countWhole(ArrayList<Integer> num){
        int counter = 0;
        for(int i = 0; i < num.size(); i++){
            if(num.get(i) > 0){
                counter++;
            }
        }
        System.out.println("The number of positive integers in the ArrayList is: " + counter);
    }

    /**
     * display all numbers user entered (Question 5)
     * Method name: display
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void display(ArrayList<Integer> num){
            System.out.println("The integers in order entered is " + num);
    }

    /**
     * display all numbers user entered in reverse order (Question 6)
     * Method name: displayReverse
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void displayReverse(ArrayList<Integer> num){
        System.out.print("The integers in reverse order is ");
        for(int i = num.size()-1; i >= 0; i--){
            System.out.print(num.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * display the sum of all numbers user entered (Question 7)
     * Method name: sum
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void sum(ArrayList<Integer> num){
        int sum = 0;
        for(int i = 0; i < num.size(); i++){
            sum += num.get(i);
        }
        System.out.println("The sum of all numbers in the ArrayList is: " + sum);
    }

    /**
     * display the average of all numbers user entered to 1 decimal place (Question 8)
     * Method name: average
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void average(ArrayList<Integer> num){
        int sum = 0;
        for(int i = 0; i < num.size(); i++){
            sum += num.get(i);
        }
        //find the average and multiply by 10 to truncate
        int average = sum * 10 / num.size(); 
        double roundedAvg = (double)(average) / 10.0; //store int to double
        System.out.println("The average is: " + roundedAvg);
    }

    /**
     * display the largest number user entered (Question 9)
     * Method name: findMax
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void findMax(ArrayList<Integer> num){
        int max = num.get(0);
        for(int i = 0; i < num.size(); i++){
            if(num.get(i) > max){
                max = num.get(i);
            }
        }
        System.out.println("The largest number in the ArrayList is: " + max);
    }

    /**
     * display the smallest number user entered (Question 9)
     * Method name: findMin
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void findMin(ArrayList<Integer> num){
        int min = num.get(0);
        for(int i = 0; i < num.size(); i++){
            if(num.get(i) < min){
                min = num.get(i);
            }
        }
        System.out.println("The smallest number in the ArrayList is: " + min);
    }

    /**
     * display the index of the number user entered (Question 10)
     * Method name: search
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void search(ArrayList<Integer> num){
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Please enter a number to search for: ");
        int user = myScanner.nextInt();
        int index = 0;
        System.out.print("The index of the number you entered is: ");
        for(int i = 0; i < num.size(); i++){
            if(num.get(i) == user){
                index = i;
                System.out.print(index + " ");
            }
        }
        System.out.println();
    }

    /**
     * sort the numbers in the ArrayList (Question 12)
     * Method name: bubbleSort
     * @param num - ArrayList of integers
     * @return void - procedure method
     */
    public static void bubbleSort(ArrayList<Integer> num){
        int temp = 0;
        for(int i = 0; i < num.size(); i++){
            for(int j = 0; j < num.size()-1; j++){
                if(num.get(j) > num.get(j+1)){
                    temp = num.get(j);
                    num.set(j, num.get(j+1));
                    num.set(j+1, temp);
                }
            }
        }
        System.out.println("The sorted ArrayList is: ");
        for(int i = 0; i < num.size(); i++){
            System.out.print(num.get(i) + " ");
        }
        System.out.println();
    }

}
