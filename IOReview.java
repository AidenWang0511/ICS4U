/**
* Name: Aiden Wang
* Date: Sep 17, 2021
* Description: questions from string review presentation
*/

import java.util.*;
import java.io.*;

public class IOReview {
    public static void main(String[] args) throws Exception{
        File myFile = new File("writeEx1");
        PrintWriter output = new PrintWriter(myFile);
        //Scanner in = new Scanner(myFile);
        String s = "POPCORN";
        for(int i = 0; i < s.length(); i++){
            output.println(s.charAt(i));
        }
        output.close();
    }
}
