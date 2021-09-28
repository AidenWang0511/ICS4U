/**
* Name: Aiden Wang
* Date: Sep 27, 2021
* Description: reverse the number user entered without java API methods
*/

import java.util.*;

public class AlgorithmChallenge {
    public static void main(String[] args) {
        /*
         * Steps for reverse number algorithm:
         *   promt the user for a number and store it in int variable num
         *   create a int variable called reNum and set it to 0
         *   while num does not equal to 0 perform the following commands:
         *     multiply reNum by 10
         *     add the value of num mod(%) 10 to reNum
         *     divide num by 10
         *   print out reNum
         */
        Scanner in = new Scanner(System.in);
        //prompt user for a nuymber to reverse
        System.out.println("Please enter a number you want to reverse: ");
        int num = in.nextInt();
        int reNum = 0;
        while(num!=0){//when num = 0, it means there is no left over digit
            reNum *= 10; //create an extra "spot" for next digit
            reNum += num%10; //add the next digit
            num /= 10; //get rid of the digit that's already in reNum through truncation
        }
        //print as required
        System.out.println("The number reversed is: " + reNum);
    }
}
