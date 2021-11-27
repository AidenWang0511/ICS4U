import java.util.*;
public class QuickSort {
    public static void main(String[] args){

        //sort string array using quick sort in alphabetical order
        String[] strArray = {"pqr", "stu", "vwx", "abc","def", "ghi", "jkl", "mno", "yz"};    
        quickSort(strArray, 0, strArray.length-1);
        System.out.println("Sorted String Array: ");
        for(String s: strArray){
            System.out.print(s + " ");
        }
    }
    //quick sort method
    public static void quickSort(String[] strArray, int low, int high){
        if(low < high){
            int pivot = partition(strArray, low, high);
            quickSort(strArray, low, pivot-1);
            quickSort(strArray, pivot+1, high);
        }
    }
    //partition method
    public static int partition(String[] strArray, int low, int high){
        String pivot = strArray[high];
        int i = low-1;
        for(int j = low; j < high; j++){
            if(strArray[j].compareTo(pivot) > 0){
                i++;
                String temp = strArray[i];
                strArray[i] = strArray[j];
                strArray[j] = temp;
            }
        }
        String temp = strArray[i+1];
        strArray[i+1] = strArray[high];
        strArray[high] = temp;
        return i+1;
    }
   
}
