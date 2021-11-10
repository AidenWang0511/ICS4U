import java.util.*;

public class test{
    public static void main(String[] args) {

        int[] arr = arrGen();

        boolean a = false;
        for(int i=0; i<4; i++){
            a = a||true;
        }

        System.out.println(a);
        
    }

    public static int[] arrGen(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the size of the array: ");
        int size = in.nextInt();
        int[] arr = new int[size];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * 100);
        }
        return arr;
    }

}