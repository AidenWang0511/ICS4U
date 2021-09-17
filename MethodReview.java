/**
* Name: Aiden Wang
* Date: Sep 17, 2021
* Description: question from java methods review presentation
*/

import java.util.*;

public class MethodReview {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);

        int arr[] = new int[10];//creating the array
        for(int i = 0; i < 10; i++) {
           System.out.println("Please enter an integer: ");
           arr[i] = in.nextInt();
        }

        //print the max number in an array of integers
        printMax(arr);

        //prompt the user for celsius, calculates and print out Kelvin
        System.out.println("Please enter a degree in Celcius: ");
        double c = in.nextDouble();
        System.out.println("The Celcius degree you've entered in Kelvin is: " + celsiusToKelvin(c) + "K");

        //prompt 2 x&y coordinates, and return the distance between them
        System.out.println("Please enter the x,y coordinate with space between them: (e.g. 3 4)");
        double x1 = in.nextDouble();
        double y1 = in.nextDouble();
        System.out.println("Please enter another x,y coordinate with space between them: (e.g. 3 4)");
        double x2 = in.nextDouble();
        double y2 = in.nextDouble();
        System.out.println("The distance between the 2 coordinates is: " + coordinateDis(x1, y1, x2, y2));
    }

    public static void printMax(int[] arr){
        //print the maximum number inside the array
        int maxN = arr[0];
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > maxN){
                maxN = arr[i];
            }
        }
        System.out.println("The largest number is: " + maxN);
    }

    //convert celsius to Kelvin
    public static double celsiusToKelvin(double c){
        return c + 273.15;
    }

    //calculates the distance between 2 coordinates
    public static double coordinateDis(double x1, double y1, double x2, double y2){
        return Math.sqrt( ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)) );
    }

}
