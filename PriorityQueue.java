package com.company;
import java.util.*;

public class PriorityQueue {

    public static double FIFO(int[] array) {
        double time = 0;
        double N = array.length;
        for (int i = 0; i < array.length; i++) {
            time += array[i] * (N - i);
        }
        return (time / array.length); //no need to implement actual array, as times can be calculated simply based on order
    }//each item experiences a cumulative wait time of every item before it

    public static boolean less(int v, int w) {
        return v < w;
    }

    public static void exch(int[] array,int x,int y){
        int z = array[x];
        array[x] = array[y];
        array[y] = z;
    }

    public static void sink(int pos, int N,int[] array) {
        int lastIndex = N;
        while ((pos <= ((lastIndex-1) / 3)) && lastIndex > 0) {
            int left = 3 * pos + 1;
            int greater = left;
            if (left < lastIndex) {
                int middle = left + 1;
                if (array[middle] >= (array[left])) {
                    greater = middle;
                }
                if (middle < lastIndex) {
                    int right = middle + 1;
                    if (array[right]>=(array[greater])) {
                        greater = right;
                    }
                }
            }
            if (less(array[pos],array[greater])){
                exch(array,pos,greater);
                pos = greater;
            }
            else{
                break;
            }
        }
    }

    public static void hSort(int[] array)
    {
        int N = array.length - 1;
        for (int k = N/3; k >= 0; k--)
            sink(k,N,array);
        while (N > 0)
        {
            exch(array, 0, N--);
            sink(0,N,array);
        }
    }

    public static double SJF(int[] array) {
        hSort(array);
        return FIFO(array);
    } //sort to ensure shortest is served first, then FIFO

    public static double RR(int[] array,int M){
        double time =0;
       double Totaltime = 0;
       ArrayList<Integer> array1 = new ArrayList<>();
       for (int i = 0; i < array.length; i++){
           array1.add(array[i]);
       }
       int j = 0;
       while (!array1.isEmpty()){
           int g = array1.get(j);
           for (int i = 1; i <= M; i++ ){ //check if greater than time slice, subtract time slice
               g--;
               time++;
               if (g == 0){break;} //if item is smaller, it terminates the loop
           }
           array1.set(j,g);//update the time at position
           if (g == 0){
               array1.remove(j);
               Totaltime += time;
           }//if finished, remove, and increment time
           else{
               array1.add(g);
               array1.remove(j);//add to the back of the queue
           }
       }
       return Totaltime/array.length;
    }

    public static void main(String[] args) {
        int[] test = new int[100];
        Random rand = new Random();
        for (int i = 0; i < test.length;i++){
            test[i] = rand.nextInt(100);
        }
        System.out.println(FIFO(test));
        System.out.println(RR(test,20));
        System.out.println(SJF(test));
        System.out.println("Now round robin");
        test = new int[100];
        for (int i = 0; i < test.length;i++){//testing different round robin slices, upto 50
            test[i] = rand.nextInt(100);
        }
        for (int i = 5; i < 51;i+=5){
            System.out.print(RR(test,i));
            System.out.print(", ");
        }
    }
}
//2582.38
//3579.76
//1889.64
//Now round robin
//3333.58, 3315.02, 3299.21, 3260.7, 3165.03, 3218.11, 3202.6, 3052.48, 3046.47, 3008.18,
//differnce between slices is marginal, even in increments of 5