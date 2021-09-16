import java.util.*;

public class Review1{
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
    
  }
}