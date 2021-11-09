/**
* Name: Aiden Wang
* Date: Oct 13, 2021
* Description: 2D array exercises questions
*/

import java.util.*;
import java.io.*;

public class Level4a2DArrayExercises {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int[][] num = new int[3][4]; //3row by 4 column int array
        populate2(num);
        display(num);
        transpose(num);
        sum(num);
    }

    /**
     * prompt user for values to put in array
     * method name: populate
     * @param num[][] - int 2D array
     * @return void - procedure method
     */
    public static void populate(int[][] num) {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                System.out.print("Enter a number: ");
                num[i][j] = in.nextInt();
            }
        }
        in.nextLine(); //clear the scanner
    }
    /**
     * populate values from a txt file into the 2D array
     * method name: populate2
     * @param num[][] - int 2D array
     * @return void - procedure method
     */
    public static void populate2(int[][] num) throws Exception {
        Scanner ms = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = ms.nextLine();
        File file = new File(fileName);
        Scanner in = new Scanner(file);
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                num[i][j] = in.nextInt();
            }
        }
        in.close();
    }

    /**
     * print the 2D array
     * method name: display
     * @param num[][] - int 2D array
     * @return void - procedure method
     */
    public static void display(int[][] num) {
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                System.out.print(num[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * print the 2D array in transpose form
     * method name: transpose
     * @param num[][] - int 2D array
     * @return void - procedure method
     */
    public static void transpose(int[][] num) {
        for (int i = 0; i < num[0].length; i++) {
            for (int j = 0; j < num.length; j++) {
                System.out.print(num[j][i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * print the 2D array with sum of each column and row
     * method name: sum
     * @param num[][] - int 2D array
     * @return void - procedure method
     */
    public static void sum(int[][] num){
        int[] rowSum = new int[num.length];
        int[] colSum = new int[num[0].length];
        for(int i=0; i<num.length; i++){
            for(int j=0; j<num[i].length; j++){
                rowSum[i] += num[i][j];
                colSum[j] += num[i][j];
            }
        }
        for(int i=0; i<num.length; i++){
            for(int j=0; j<num[i].length; j++){
                System.out.print(num[i][j] + "\t");
            }
            System.out.println(rowSum[i]);
        }
        for(int i=0; i<num[0].length; i++){
            System.out.print(colSum[i] + "\t");
        }
        System.out.println();
    }

}
