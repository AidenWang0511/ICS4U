/**
* Name: Aiden Wang
* Date: Sep 14, 2021
* Description: calculate the sum of a igven inteeger and next 5 consecutive odd numberss
*/

import java.util.*;

public class ConsecutiveSum {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        //prompt the use for the starting number
        System.out.println("Please enter an integer:");
        int num = myScanner.nextInt(); //stores the starting number
        int sum = num; // declare the sum variable

        //find the smallest next odd integer
        if(num%2 == 1){
            num += 2;
        }else{
            num += 1;
        }

        //for loop 5 times, every time add num to sum, and make num the next odd integer
        for(int i = 0; i < 5; i++){
            sum += num;
            num += 2;
        }

        //print out the desired sum
        System.out.println(sum);
    
    }
}
