/**
* Name: Aiden Wang
* Date: Sep 14, 2021
* Description: HW1-P1-count by fives from 100 to 50 inclusive
*/

import java.util.*;

public class AidenHW1P1 {
    public static void main(String[] args) {
        int num = 100; //int to count the numbers
        String output = "";//String to hold the final output

        while(num >= 50) {//while loop until the num value is less than 50
            if(num == 50){//if num is 50, don't add the comma for correct format
                output += num;
            }else{//otherwise, add num, comma, and space to the output string 
                output += num + ", ";
            }
            num -= 5;//count down by 5
        }
        System.out.println(output);//print the required output in a line
    }
}
