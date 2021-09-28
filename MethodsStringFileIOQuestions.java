/**
* Name: Aiden Wang
* Date: Sep 26, 2021
* Description: questions from teacher for methods, string, and file IO
*/

import java.util.*;
import java.io.*;

public class MethodsStringFileIOQuestions {
    public static void main(String[] args) throws Exception{

        //part of question 1
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("The sum of the array is: " + sum(arr));


        //quesiton 2
        File stuInfo = new File("studentinfo");
        PrintWriter output = new PrintWriter(stuInfo);
        Scanner in = new Scanner(System.in);
        //prompt user for 10 students name and marks and print to the studentinfo txt file
        for(int i=0; i<10; i++){
            System.out.println("Please enter a student name: ");
            String name = in.next();
            System.out.println("Please enter " + name + "'s mark: ");
            int mark = in.nextInt();
            output.println(name);
            output.println(mark);
        }
        output.close();


        //quesiton 3
        Scanner fileIn = new Scanner(stuInfo);
        String highName = "";//keep track of name with highest mark
        int highMark = -1;//keep track of highest mark
        while(fileIn.hasNext()){
            String tempName = fileIn.next();
            int tempMark = fileIn.nextInt();
            //update highest mark name and highest mark if the higher mark occurs
            if(highMark < tempMark){
                highName = tempName;
                highMark = tempMark;
            }
        }
        //print to console as required
        System.out.println(highName + " has the highest mark of: " + highMark);
        fileIn.close();


        //question 4
        String check = "";
        //do while loop ensure at least 1 execution
        do{
            //prompt user for number of strings
            System.out.println("Please enter the number of strings you want to reverse: ");
            int num = in.nextInt();
            in.nextLine();//clear the scanner
            //create the string array based on the size user entered
            String[] stringArr = new String[num];
            for(int i=0; i<num; i++){
                //prompt for string
                System.out.println("Please enter a String: ");
                stringArr[i] = in.nextLine();
            }
            System.out.println("Now printing them in reverse order: ");
            //print in reverse order use counting down for loop
            for(int i=num-1; i>=0; i--){
                System.out.println(stringArr[i]);
            }
            //ask the user to continue or not
            System.out.println("Do you want to continue: (yes/no)");
            check = in.next();
        }while(!check.equals("no"));


        //question 5
        System.out.println("Please enter a phrase: ");
        in.nextLine();//clear the scanner
        String phrase = in.nextLine();
        String[] stringArr = phrase.split(" ");//split the phrase into array
        for(int i=0; i<stringArr.length; i++){
            if(i%2 == 0){//starting from index 0, even index is upper case
                System.out.print(stringArr[i].toUpperCase());
            }else{//odd index is lower case
                System.out.print(stringArr[i].toLowerCase());
            }
        }
        System.out.println();


        //question 6
        System.out.println("Please enter a phrase: ");
        phrase = in.nextLine();
        String[] wordArr = phrase.split(" ");//split the phrase into array
        for(int i=0; i<wordArr.length; i++){
            System.out.println(wordArr[i]);//print each word on a separate line
        }
    }

    /**
     * returns the sum of an array integers (question 1)
     * @param arr - array to sum
     * @return arrSum - sum of the array
     */
    public static int sum(int[] arr){
        int arrSum = 0;
        for(int i=0; i<arr.length; i++){
            arrSum += arr[i];
        }
        return arrSum;
    }
}
