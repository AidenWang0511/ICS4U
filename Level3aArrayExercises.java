/**
* Name: Aiden Wang
* Date: Oct 1, 2021
* Description: L3a Array Exercise questions
*/

import java.util.*;
import java.io.*;

public class Level3aArrayExercises {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int num[] = new int[7];
        noDuplicates(num);
        randomNoDuplicates();
        enterAndCount();
        randomOrder(false);
        threeRandomOrder();

    }

    /**
     * Prompt user for 7 unique numbers in total
     * Method name: noDuplicates
     * @param arr[] - int array
     * @return void - procedure method
     */

    public static void noDuplicates(int arr[]){
        Scanner myScanner = new Scanner(System.in);
        int uniqueNum = 0;
        do{
            System.out.println("Please enter an integer: ");
            int num = myScanner.nextInt();
            boolean check = true;
            for(int i=0; i<uniqueNum; i++){
                if(arr[i] == num){
                    check = false;
                }
            }
            if(check){//when it is unique
                arr[uniqueNum] = num;
                uniqueNum++;
            }else{
                System.out.println("The number you entered exist already!");
            }
        }while(uniqueNum < 7);

    }

    /**
     * print 10 unique integers randomized from user given range
     * Method name: randomNoDuplicates
     * no param
     * @return void - procedure method
     */

    public static void randomNoDuplicates(){
        Scanner myScanner = new Scanner(System.in);
        int uniqueNum = 0;
        int arr[] = new int[10];
        System.out.println("Please enter a range (must be greater than 10) for your random number generator (e.g. 1 10 indicating 1 to 10 inclusive)");
        int firstN = myScanner.nextInt();
        int secondN = myScanner.nextInt();
        do{
            int num = (int)(Math.random()*(secondN - firstN + 1)) + firstN;
            boolean check = true;
            for(int i=0; i<uniqueNum; i++){
                if(arr[i] == num){
                    check = false;
                }
            }
            if(check){//when it is unique
                arr[uniqueNum] = num;
                uniqueNum++;
            }
        }while(uniqueNum < 10);
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }

    /**
     * prompt user for 4 numbers and count the # of occurences of those numbers in a text file
     * Method name: enterAndCount
     * no param
     * @return void - procedure method
     */

    public static void enterAndCount() throws Exception{
        Scanner myScanner = new Scanner(System.in);
        int[] userN = new int[4];
        int[] occurences = new int[4];
        int uniqueNum = 0;
        do{
            System.out.println("Please enter an integer: ");
            int num = myScanner.nextInt();
            boolean check = true;
            for(int i=0; i<uniqueNum; i++){
                if(userN[i] == num){
                    check = false;
                }
            }
            if(check){//when it is unique
                userN[uniqueNum] = num;
                uniqueNum++;
            }else{
                System.out.println("The number you entered exist already!");
            }
        }while(uniqueNum < 4);
        System.out.println("Please enter a text file name you want to read: ");
        myScanner.nextLine();//clear scanner
        String txtFile = myScanner.nextLine();
        File file1 = new File(txtFile);
        Scanner input = new Scanner(file1);
        while(input.hasNext()){
            int num = input.nextInt();
            for(int i=0; i<userN.length; i++){
                if(num == userN[i]){
                    occurences[i]++;
                }
            }
        }
        int maxOccurIndex = 0;
        for(int i=0; i<userN.length; i++){
            if(occurences[maxOccurIndex] < occurences[i]){
                maxOccurIndex = i;
            }
            System.out.print(userN[i] + ": ");
            for(int j=0; j<occurences[i]; j++){
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("The most common number chosen was " + userN[maxOccurIndex]);
        input.close();
    }

    /**
     * Output the names of 5 divers in a random diving order
     * Method name: randomOrder
     * @param checkThree - boolean to check if this method is used for assisting threeRandomOrder
     * @return order[] - order of divers, int array
     */

    public static int[] randomOrder(boolean checkThree){
        Scanner myScanner = new Scanner(System.in);
        String diverName[] = new String[5];
        int diverOrder[] = new int[5];
        for(int i=0; i<diverName.length; i++){
            if(!checkThree){
                System.out.println("Please enter a diver's name: ");
                diverName[i] = myScanner.nextLine();
            }
            boolean check = true;
            int order;
            do{
                order = (int)(Math.random() * 5) + 1;
                check = true;
                for(int j=0; j<i; j++){
                    if(diverOrder[j] == order){
                        check = false;
                    }
                }
            }while(!check);
            diverOrder[i] = order;
        }
        if(!checkThree){
            for(int i=1; i<=5; i++){
                for(int j=0; j<diverName.length; j++){
                    if(diverOrder[j] == i){
                        System.out.println(diverOrder[j] + ". " + diverName[j]);
                    }
                }
            }
        }
        return diverOrder;
    }

    /**
     * Output the names of 5 divers in a random diving order 3 times with different first and last place
     * Method name: threeRandomOrder
     * no param
     * @return voic, prcedure type method
     */

    public static void threeRandomOrder(){
        Scanner myScanner = new Scanner(System.in);
        int diffOrder = 0;
        int first[] = new int[3];
        int last[] = new int[3];
        String diverName[] = new String[5];
        for(int i=0; i<diverName.length; i++){
            System.out.println("Please enter a diver's name: ");
            diverName[i] = myScanner.nextLine();
        }
        do{
            int order[] = randomOrder(true);
            int start = 0;
            int end = 0;
            for(int i=0; i<5; i++){
                if(order[i] == 1){
                    start = i;
                }else if(order[i] == 5){
                    end  = i;
                }
            }
            boolean check = true;
            for(int i=0; i<diffOrder; i++){
                if(first[i] == start || last[i] == end){
                    check = false;
                }
            }
            if(check){
                first[diffOrder] = start;
                last[diffOrder] = end;
                diffOrder++;
                for(int i=1; i<=5; i++){
                    for(int j=0; j<diverName.length; j++){
                        if(order[j] == i){
                            System.out.println(order[j] + ". " + diverName[j]);
                        }
                    }
                }
                System.out.println();
            }

        }while(diffOrder < 3);
    }

}
