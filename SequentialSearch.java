import java.util.*;

public class SequentialSearch {
    public static void main(String[] args) {
        
    }

    /**
     * sequential search
     * @param arr
     * @param key
     */
    public static void sequentialSearch(String[] arr, String key) {
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (array[i].equals(key)) {
                System.out.println("Found " + key + " at index " + i);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Not found");
        }
    }







    /**
     * Binary Search a number in an sorted array
     * method name: binarySearch
     * @param arr the array to search
     * @param value the value to search for
     * @return the index of the value if found, -1 if not found
     */
    public static int binarySearch(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
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
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (arr[mid] == value) {
            return mid;
        } else if (arr[mid] < value) {
            return recursiveBinarySearch(arr, value, mid + 1, right);
        } else {
            return recursiveBinarySearch(arr, value, left, mid - 1);
        }
    }

}
