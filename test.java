import java.util.Scanner;
public class test
{   
    public static void main(String args[])
    {
        Scanner myScanner = new Scanner(System.in);
        String name[] = new String[5];
        String phoneNum[] = new String[5];
        for(int i=0; i<5; i++){
            name[i] = myScanner.next();
            phoneNum[i] = myScanner.next();
        }
        String tempS, tempN;
        for(int i = 4; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(name[j].compareTo(name[j+1]) > 0){
                    tempS = name[j];
                    tempN = phoneNum[j];
                    name[j] = name[j+1];
                    phoneNum[j] = phoneNum[j+1];
                    name[j+1] = tempS;
                    phoneNum[j+1] = tempN;
                }
            }
        }
        for(int i = 0; i < 5; i++){
            System.out.println(name[i] + ' ' + phoneNum[i]);
        }
    }
}