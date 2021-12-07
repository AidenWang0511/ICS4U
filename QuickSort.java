import java.util.*;
public class QuickSort {
    public static void main(String[] args) {
        
        String[] array = {"j", "a", "d", "r", "p"}; //array of names
        quickSort(array, 0, array.length - 1);  //call the sort method
        for (int i = 0; i < array.length; i++) { //pring the array
            System.out.println(array[i]);
        }
    }
    
    //quick sort recursive method
    public static void quickSort(String[] array, int low, int high) {
        if (low < high) { //base case
            int pivot = partition(array, low, high); //get the pivot
            quickSort(array, low, pivot - 1); //sort the low side
            quickSort(array, pivot + 1, high); //sort the high side
        }
    }

    //partition the array
    public static int partition(String[] array, int low, int high) {
        String pivot = array[high]; //get the pivot
        int i = low - 1; //index of smaller element
        for(int j = low; j<high; j++){ //loop through array
            if(array[j].compareTo(pivot) < 0){ //if element is smaller than pivot
                i++; //increment index of smaller element
                String temp = array[i]; //swap elements
                array[i] = array[j]; 
                array[j] = temp;
            }
        }
        String temp = array[i+1]; //swap elements 
        array[i+1] = array[high]; 
        array[high] = temp; 
        return i+1; //return index of pivot
    }
   
}
