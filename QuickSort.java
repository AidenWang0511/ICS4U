import java.util.*;

public class QuickSort {
    public static void main(String[] args) {
        
        //bogo sort
        int[] arr1 = {1,2,3,4,5,7,6,8};
        bogoSort(arr1);
    }
    //bogo sort
    public static void bogoSort(int[] arr) {
        int n = arr.length;
        while (!isSorted(arr)) {
            shuffle(arr);
            print(arr);
        }
    }
    //print arr
    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    //shuffle
    public static void shuffle(int[] arr) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            int r = i + rand.nextInt(arr.length - i);
            swap(arr, i, r);
        }
    }
    //isSorted
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    //swap
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

