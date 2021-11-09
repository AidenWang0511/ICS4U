/**
* Name: Aiden Wang
* Date: Oct 13, 2021
* Description: 2D array exercises questions
*/

import java.util.*;
import java.io.*;

public class Level4b2DArrayExercises {
    public static void main(String[] args) throws Exception {
        File marksFile = new File("marks.txt");
        Scanner in = new Scanner(marksFile);
        int stuNum = in.nextInt();
        int markNum = in.nextInt();
        String[] names = new String[stuNum];
        double[][] marks = new double[stuNum][markNum];
        double[] stuAvg = new double[stuNum];
        double[] markAvg = new double[markNum];
        for (int i = 0; i < stuNum; i++) {
            names[i] = in.next();
            for (int j = 0; j < markNum; j++) {
                marks[i][j] = in.nextDouble();
                stuAvg[i] += marks[i][j];
                markAvg[j] += marks[i][j];
            }
        }

        for (int i = 0; i < stuNum; i++) {
            stuAvg[i] /= markNum;
            stuAvg[i] = Math.round(stuAvg[i] * 100.0) / 100.0;
        }

        for (int i = 0; i < markNum; i++) {
            markAvg[i] /= stuNum;
            markAvg[i] = Math.round(markAvg[i] * 100.0) / 100.0;
        }

        for (int i = 0; i < stuNum; i++) {
            System.out.print(names[i] + ":\t\t");
            for(int j = 0; j < markNum; j++) {
                System.out.print(marks[i][j] + "\t");
            }
            System.out.println("student average: " + stuAvg[i]);
        }
        System.out.print("Average mark:\t");
        for(int i = 0; i  <markNum; i++) {
            System.out.print(markAvg[i] + "\t");
        }

        in.close();

    }
}
