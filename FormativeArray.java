import java.util.*;
public class FormativeArray {
    public static void main(String[] args) {
        //store 50 unique values in array 0-100 inclusive and print frequency of each digit 0-9
        int[] arr = new int[50];
        int[] freq = new int[10];
        int counterU = 0;
        do{
            int num = (int)(Math.random()*101);
            boolean check = true;
            for(int i=0; i<counterU; i++){
                if(arr[i] == num){
                    check = false;
                }
            }
            if(check){//when it is unique
                arr[counterU] = num;
                counterU++;
                if(num == 0){
                    freq[0]++;
                }else{
                    while(num!=0){
                        freq[num%10]++;
                        num = num/10;
                    }
                }
            }
        }while(counterU<50);

        for(int i=0; i<50; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        for(int i=0; i<10; i++){
            System.out.println(i + " appeared " + freq[i] + " times");
        }

    }
}
