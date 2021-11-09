/**
* Name: Aiden Wang
* Date: Oct 25, 2021
* Description: questions from Recursion exercises
*/

import java.util.*;

public class RecursionExercises1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int n = in.nextInt();
        System.out.println("The factorial of " + n + " is: " + factorial(n));
        System.out.println("Enter 2 numbers to find their GCF: ");
        int a = in.nextInt();
        int b = in.nextInt();
        System.out.println("The GCF of " + a + " and " + b + " is: " + gcf(a, b));
        System.out.println("Enter a real value for base: ");
        double base = in.nextDouble();
        System.out.println("Enter a integer value for exponent: (can be negative)");
        int exp = in.nextInt();
        System.out.println("The value of " + base + " to the power of " + exp + " is: " + power(base, exp));
        System.out.println("Enter a number to find the fibonacci number at that index: ");
        n = in.nextInt();
        System.out.println("The fibonacci number at index " + n + " is: " + fib(n));
    }

    /**
     * factorial method
     * method name: factorial
     * @param n - the number to find the factorial of
     * @return - the factorial of n
     */
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * Euclid's algorithm
     * method name: gcf
     * @param m - the first number
     * @param n - the second number
     * @return - the greatest common divisor of m and n
     */
    public static int gcf(int m, int n) {
        if (m == n) {
            return m;
        }else if(m >= n){
            return gcf(n, m-n);
        }else{
            return gcf(n, m);
        }
    }

    /**
     * to the power of method
     * method name: power
     * @param base - double, the base
     * @param exponent - int, the exponent, can be negative
     * @return double - the base to the power of the exponent
     */
    public static double power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent < 0) {
            return (1.0 / base) * power(base, exponent + 1);
        }else{
            return base * power(base, exponent - 1);
        }
    }

    /**
     * recursive method to return the nth fibonacci number
     * method name: fib
     * @param n - the number of fibonacci numbers to print
     * @return int - the nth fibonacci numbers
     */
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

}
