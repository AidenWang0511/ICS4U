/**
* Name: Aiden Wang
* Date: Sep 17, 2021
* Description: questions from string review presentation
*/

import java.util.*;

public class StringReview {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] arrC = {'a', 'b', 'c', 'd', 'e', 'f'};
        //converts char array to String and prints it
        String s = String.valueOf(arrC);
        System.out.println("The char array produces the string: " + s);

        //prompts the user for 2 words and join them
        //continue this process until user says no
        String check;
        do{
            System.out.println("Please enter 2 words: ");
            String a = in.next();
            String b = in.next();
            String combined = a + b;
            System.out.println("The new combined words is: " + combined);
            System.out.println("Do you want to continue: yes/no (lower case pls)");
            check = in.next();
        }while(check.equals("yes"));

        //get rid of "not" from the String
        String phrase = "The blue car is not fast";
        phrase = phrase.replaceAll("not ", "");
        System.out.println("The new phrase is: " + phrase);

        //sort the string list in alphabetical order
        String[] arrS = {"Orange", "Yellow", "Blue", "Red", "Green", "Purple"};
        for(int i = arrS.length - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(arrS[j].compareTo(arrS[j+1]) > 0){
                    String temp = arrS[j];
                    arrS[j] = arrS[j+1];
                    arrS[j+1] = temp;
                }
            }
        }
        for(int i = 0; i < arrS.length; i++){
            System.out.print(arrS[i] + " ");
        }
        System.out.println();

    }
    
}
