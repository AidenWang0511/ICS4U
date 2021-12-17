/*
File Name: InsertionSortActivity
Author: Aiden Wang
Date: Dec, 2021
Description: Acticity for insertion sort presentation
 */

import java.util.*;

public class InsertionSortActivity{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        //question 1: sort array of 7 integers in ascending order:  5, 8, 12, 1, 3, 6, 2
        int[] arr = {5, 8, 12, 1, 3, 6, 2};
        question1(arr);
        System.out.println("Question 1: ");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        //question 2:
        ArrayList<Integer> list = new ArrayList<Integer>();
        System.out.println("Please enter 8 integers: ");
        for(int i = 0; i < 8; i++){
            int num = in.nextInt();
            list.add(num);//promtp user for 8 integers and store them in the list
        }
        question2(list);
        System.out.println("Question 2: ");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        //question 3:
        System.out.println("Please enter 5 words: ");
        String words[] = new String[5];
        for(int i = 0; i < 5; i++){
            words[i] = in.next();
        }
        question3(words);
        System.out.println("Question 3: ");
        for(int i = 0; i < words.length; i++){
            System.out.print(words[i] + " ");
        }
        System.out.println();
    }

    /**
     * insertion sort method in ascending order for array
     * method name: question1
     * @param arr[] - array to be sorted
     * @return void - no return, but the array is sorted
     */
    public static void question1(int[] arr){
        int n = arr.length;
        for(int i = 1; i < n; i++){ //iterate throough the array
            for(int j = i-1; j>=0; j--){ //iterate through all the elements to the left of the current element
                if(arr[j] > arr[j+1]){ //if the element to the left is greater than the current element, swap them
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * insertion sort method in descending order for arraylist
     * method name: question2
     * @param list - arraylist to be sorted
     * @return void - no return, but the arraylist is sorted
     */
    public static void question2(ArrayList<Integer> list){
        int n = list.size();
        //iterate throough the array
        for(int i = 1; i < n; i++){ 
            //iterate through all the elements to the left of the current element
            for(int j=0; j<i; j++){ 
                //if the element to the left is smaller than the current element
                if(list.get(j) < list.get(i)){
                    int temp = list.get(i);
                    list.remove(i); //remove the element at index i
                    list.add(j, temp); //add the current element at index i to index j
                    break;
                }
            }
        }
    }

    /**
     * insertion sort method in ascending order for array of strings
     * method name: question3
     * @param words - array of strings to be sorted
     * @return void - no return, but the array of strings is sorted
     */
    public static void question3(String[] words){
        int n = words.length;
        //iterate throough the array
        for(int i = 1; i < n; i++){ 
            //iterate through all the elements to the left of the current element
            for(int j=0; j<i; j++){ 
                //if the element to the left is smaller than the current element
                if(words[j].compareTo(words[i]) > 0){
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                    break;
                }
            }
        }
    }
    
}
