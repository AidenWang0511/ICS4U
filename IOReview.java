/**
* Name: Aiden Wang
* Date: Sep 17, 2021
* Description: questions from string review presentation
*/

import java.util.*;
import java.io.*;

public class IOReview {
    public static void main(String[] args) throws Exception{

        //quesiton 1
        File myFile = new File("writeEx1");
        PrintWriter output = new PrintWriter(myFile);
        String s = "POPCORN";
        for(int i = 0; i < s.length(); i++){
            output.println(s.charAt(i));
        }
        output.close();


        //quesiton 2
        Scanner myScanner = new Scanner(System.in);
        int[] marks = new int[5];
        //prompt user for 5 marks
        for(int i=0; i<5; i++){
            System.out.println("Please enter a integer mark between 0-100");
            marks[i] = myScanner.nextInt();
        }
        //bubble sort marks
        for(int i=4; i>0; i--){
            for(int j=0; j<i; j++){
                if(marks[j] > marks[j+1]){
                    int temp = marks[j];
                    marks[j] = marks[j+1];
                    marks[j+1] = temp;
                }
            }
        }
        myScanner.nextLine();//clear the scanner
        System.out.println("File name you want to print these marks to: ");//prompt user for file name
        String fileName = myScanner.nextLine();
        File markFile = new File(fileName);//create the file
        output = new PrintWriter(markFile);//create owriter for the file
        for(int i=0; i<5; i++){
            output.println(marks[i]);//print marks on to file
        }
        output.close();//close the printer to save


        //question 3
        Scanner in = new Scanner(myFile);//create scanner for file in quesiton 1
        while(in.hasNext()){//scan the file and print whatever is in it to the console
            s = in.next();
            System.out.println(s);
        }
        in.close();


        //question 4
        in = new Scanner(markFile);//create scanner for file in quesiton 2
        int avg = 0;
        int passed = 0;
        int failed = 0;
        while(in.hasNext()){//scan the file and print whatever is in it to the console
            int mark = in.nextInt();
            if(mark > 49){
                passed++;
            }else{
                failed++;
            }   
            avg += mark;
        }
        avg = avg/5;//calculates the average
        //print as required
        System.out.println("You have passed " + passed + " tests and failed " + failed + " tests.");
        System.out.println("The average mark of the tests was " + avg + ".");
        in.close();
    }
}
