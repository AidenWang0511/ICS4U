/**
* Name: Aiden Wang
* Date: Oct 12, 2021
* Description: L1 Array Exercise questions
*/

import java.util.*;

public class ArrayListExercises {
    public static void main(String[] args) {
        ArrayList<Integer> num = new ArrayList<Integer>();
    }

    /**
     * Prompt up to 10 integers from user
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
            System.out.println("Do you want to continue: (yes/no)");
            String check = myScanner.next();
            if(check.equals("no")){
                break;
            }
        }
    }
}
