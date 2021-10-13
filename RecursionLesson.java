import java.util.*;

public class RecursionLesson {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a number you want to factorial: ");
        int num = in.nextInt();
        System.out.println("The factorial of " + num + " is: " + factorial(num));
    }

    public static int factorial(int num){
        if(num == 0 || num == 1){ //exit condition & base case
            return 1;
        }else{ //recursive step
            return (num * factorial(num-1));
        }
    }

}
