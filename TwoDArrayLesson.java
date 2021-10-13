import java.util.*;

public class TwoDArrayLesson {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int aGrid[][] = new int[10][10];
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.println("Enter an integer for row " + (i+1));
                aGrid[i][j] = in.nextInt();
            }
        }
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.print(aGrid[i][j] + " ");
            }
            System.out.println();
        }
    }    
}
