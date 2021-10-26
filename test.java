import java.util.*;

public class test{
    public static void main(String[] args) {
        String a = "ABCD";
        String b = a.substring(4);
        System.out.println(b);
        
    }

    /**
     * Euclid's algorithm for determining the greatest common divisor
     * Method name: gcd
     * @param m - an integer to be used
     * @param n - an integer to be used
     * @return the greatest common divisor of m and n
     */
    public static int gcd(int m, int n) {
        // base case
        if (m == 0) {
            return n;
        }
        return gcd(n % m, m);
    }

}