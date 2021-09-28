/**
* Name: Aiden Wang
* Date: Sep 17, 2021
* Description: java basic review questions
*/
import java.util.*;

public class JavaBasicReview{
  public static void main(String[] args) {
    Scanner myScanner = new Scanner(System.in);
    
    // 0 for scissors, 1 for rock, 2 for paper
    int player =0; //create the variable for the player 
    int pc = (int)(Math.random()*3); // give a random value from 0-2 for the computer
    String check = "yes";//create the variable for continue playing or not

    //while loop make sure that if player says no, the game does not continue
    while(!check.equals("no")){
      System.out.println("Enter 0 for scissors, 1 for rock, 2 for paper: ");
      player = myScanner.nextInt();
      if(player == pc){
        System.out.println("Tie");
        System.out.println("because computer did: " + pc);
      }else if(player > pc && player!=0 && pc!=2){
        System.out.println("You Win");
        System.out.println("because computer did: " + pc);
      }else if(pc > player && player!=2 && pc!=0){
        System.out.println("You Lose");
        System.out.println("because computer did: " + pc);
      }else if(player==0 && pc==2){
        System.out.println("You Win");
        System.out.println("because computer did: " + pc);
      }else if(player==2 && pc==0){
        System.out.println("You Lose");
        System.out.println("because computer did: " + pc);
      }else{
      
      }
      System.out.println("want to continue(yes/no)");
      check = myScanner.next();
      
    }

    myScanner.nextLine();//clear the scanner

    //question 2
    System.out.println("Please enter a String: ");
    String user = myScanner.nextLine();
    do{//keep printing the String user entered until user says no
      System.out.println(user);
      System.out.println("do you want to keep printing(yes/no)");
      check = myScanner.next();
    }while(!check.equals("no"));

    //question 3
    System.out.println("You have 3 chance to guess a number between 1-10 inclusive!");
    int num = (int)(Math.random()*10) + 1;//generate a number from 1 to 10 inclusive
    //for loop 3 times for user to guess, if correct, break the for loop
    for(int i=3; i>=1; i--){
      System.out.println("Please input your guess: (" + i + " chances remaining)");
      int guess = myScanner.nextInt();
      if(guess == num){
        System.out.println("Correct! :) It took you " + (4-i) + " times to guess it right!");
        num = -1;//set num to -1 for checking purposes 
        break;
      }else{
        System.out.println("Incorrect! :(");
      }
    }
    //if gueesed correctly within 3 tries, won't print the correct answer
    if(num != -1){
      System.out.println("The correct answer is: " + num);
    }

    
  }
}