/**
* Name: Aiden Wang
* Date: Sep 28, 2021
* Description: count the number of occurences of numbers from 1-10
*/

import java.util.*;

public class CountOccurences {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //int array to keep track of the occurences of 1-10
        int[] occur = new int[10];
        Arrays.fill(occur, 0); //fill the occur array with 0, not necessary because it set 0 by defult
        int num;
        while(true){ //keep the loop running infinitely
            System.out.println("Please enter a number between 1-10 inclusive: (a number outside this range will end the program)");
            num = in.nextInt();
            if(num <= 10 && num >= 1){ 
                //when the number user entered is within the range, occurence of that number goes up by 1
                num--;
                occur[num]++;
            }else{
                //if the number is outisde of the range, break the while loop to stop prompting user
                break;
            }
        }
        //for loop to print out as required
        for(int i=0; i<10; i++){
            System.out.println((i+1) + " occured " + occur[i] + " times");
        }
    }
}
