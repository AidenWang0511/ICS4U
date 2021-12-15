import java.util.*;

public class SequentialSearch {
    public static void main(String[] args) {
        
    }
    /**
     * Binary Search a number in an sorted array
     * method name: binarySearch
     * @param arr the array to search
     * @param value the value to search for
     * @return the index of the value if found, -1 if not found
     */
    public static int binarySearch(int[] arr, int value) {
        //create left and right indexes;
        //left index is 0, right index is the length of the array - 1
        //while the left <= right
            //set mid to the average of left and right (mid = (left+right) / 2))
            //if arr[mid] == value
                //return the mid index
            //if arr[mid] > value
                //set the right index to mid-1
            //if arr[mid] < value
                //set the left index to mid+1
        return -1;
    }

    /**
     * Recursive binary search
     * method name: recrusiveBinarySearch
     * @param arr the array to search
     * @param value the value to search for
     * @param left the left index
     * @param right the right index
     * @return the index of the value if found, -1 if not found
     */
    public static int recursiveBinarySearch(int[] arr, int value, int left, int right) {
        //if left > right
            return -1;
        //set mid to the average of left and right (mid = (left+right) / 2))
        //if arr[mid] == value
            //return the mid index
        //if value < arr[mid]
            //return recursiveBinarySearch(arr, value, left, mid-1)
        //else 
            //return recursiveBinarySearch(arr, value, mid+1, right)
    }

}
