/**
* Name: Aiden Wang
* Date: Sep 28, 2021
* Description: L2a Array Exercise questions
*/

import java.util.*;

public class Level2bArrayExercises {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int check = -1; //int variable for users selection and check for quiting
        do{
            //printing out the menu
            System.out.println("Please select the method you want to run: ");
            System.out.println(" 1 for countOccurrences");
            System.out.println(" 2 for countOccurrences2");
            System.out.println(" 3 for totals");
            System.out.println(" -1 to exit code");
            check = in.nextInt();

            if(check == 1){
                countOccurrence();
            }else if(check == 2){
                countOccurrence2();
            }else if(check == 3){
                totals();
            }else{

            }

        }while(check != -1);
    }

    /**
     * counting occurences of number 1-10 (Part B Question 1)
     * Method name: countOccurrence
     * @param 
     * @return void - procedure method
     */

    public static void countOccurrence(){
        Scanner in = new Scanner(System.in);
        int[] occur = new int[10]; 
        int num = 1;
        while(true){
            System.out.println("Enter a number (ranges outside 1-10 will exit prompting process)");
            num = in.nextInt();
            if((num <= 10) && (num >= 1)){
                occur[num-1]++;
            }else{
                break;
            }
        }
        for(int i=0; i<10; i++){
            System.out.println("The number " + (i+1) + " appeared " + occur[i] + " times");
        }

    }

    /**
     * counting occurences of number 15-25 (Part B Question 1)
     * Method name: countOccurrence2
     * @param 
     * @return void - procedure method
     */

    public static void countOccurrence2(){
        Scanner in = new Scanner(System.in);
        int[] occur = new int[11]; 
        int num = 1;
        while(true){
            System.out.println("Enter a number (ranges outside 15-25 will exit prompting process)");
            num = in.nextInt();
            if((num <= 25) && (num >= 15)){
                occur[num-15]++;
            }else{
                break;
            }
        }
        for(int i=0; i<11; i++){
            System.out.println("The number " + (i+15) + " appeared " + occur[i] + " times");
        }

    }

    /**
     * counting sum of number 0-9, 10-19, 20-29, ... 90-99 (Part B Question 1)
     * Method name: totals
     * @param 
     * @return void - procedure method
     */

    public static void totals(){
        Scanner in = new Scanner(System.in);
        int[] sum = new int[10]; 
        int num = 1;
        while(true){
            System.out.println("Enter a number (ranges outside 0-99 will exit prompting process)");
            num = in.nextInt();
            if(num < 0 || num > 99){
                break;
            }else{
                sum[num/10] += num;
            }
        }
        for(int i=0; i<10; i++){
            System.out.println("The number you've entered between " + (i*10) + " to " + (i*10+9) + " sums to: " + sum[i]);
        }

    }

    
}
