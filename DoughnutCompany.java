/**
* Name: Aiden Wang
* Date: Sep 14, 2021
* Description: DoughnutCompany program to calculate the cost given the amount of doughnut
*/

import java.util.*;

public class DoughnutCompany {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        //prompt the use for number of doughnuts
        System.out.println("How many doughnuts would you like to buy?");
        int total = myScanner.nextInt(); //stores the total # of doughnuts

        //calculates and sotres the number of dozens, automatically round down even if it doesn't divide
        int dozenNum = total/12; 
        //calculates and sotres the extra # of doughnuts
        int extraD = total % 12;
        //double to store the total cost, money can have up to 2 decimal places
        double costs = 0.0;

        /*
         *if and else if selection statements to check the corresponding price of the given # of doughnuts
         *first if statements to make sure no negative # of doughnuts was entered, didn't consider for decimal value of doughnuts
         */
        if( (dozenNum < 0) || (extraD < 0)){
            System.out.println("Invalid number of doughnuts!");
        }else if( (dozenNum < 6) && (dozenNum >= 0) ){
            costs = (double)dozenNum * 8.99 + (double)extraD * 8.99 / 12.0;
        }else if( (dozenNum < 12) && (dozenNum >= 6) ){
            costs = (double)dozenNum * 7.99 + (double)extraD * 7.99 / 12.0;
        }else if( (dozenNum < 16) && (dozenNum >= 12) ){
            costs = (double)dozenNum * 6.99 + (double)extraD * 6.99 / 12.0;
        }else if(dozenNum >= 16){
            costs = (double)dozenNum * 5.99 + (double)extraD * 5.99 / 12.0;
        }

        //rounding the total costs to 2 decimal places
        costs = costs * 100;
        costs = Math.round(costs);
        costs = costs / 100;
        
        //print out the total costs
        System.out.println("The total cost is: $" + costs);
    }
}
