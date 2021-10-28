import java.util.*;

public class test{
    public static void main(String[] args) {
        mystery(5);
    }

    /**
     * Mystery recursive method
     * method name: mystery
     * @param n - an integer
     * @return void
     */
    public static void mystery(int n){
        if(n == 0 || n == 1){
            return;
        }
        mystery(n-2);
        System.out.println(n);
        mystery(n-1);
    }

}