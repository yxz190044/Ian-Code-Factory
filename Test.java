

import java.util.*;
public class Test{
    public static void main(String[] args){
//        int a = 2;
//        double b = 1.2;
//        double result = multiply(a,b);
//        System.out.println("min1 = " + result);
//
//        double c = 1.3;
//        int d = 3;
//        double result2 = multiply(c,d);
//        System.out.println("min2 = " + result2);
//    }
//
//    public static double multiply(int a, double b){
//        return a*b;
//    }
//    public static double multiply(double a, int b){
//        return a*b;
        
        
//        double y = 1.9;
//        int x = (int)y;
//        while(x==1){
//        System.out.println(x);
//            x = x-1;
//        }
//        System.out.println(args.length);
//        for(String s : args){
//            System.out.println(s + " ");
//        }
        
//        char x = '\u0000';
//        if (x == null){
//        System.out.println(x + x);
//        }
//        Student A = new Student();
//        System.out.println(A.getName());
        
//        int[][] A = new int[4][5];
//        System.out.println(A[0].length);
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(3);
        queue.offer(5);
        Solution A = new Solution();
        System.out.println(A.maxInQueue(queue));
        
    }
}

class Student
{
    public String getName()
    {
        return "Yiran Zheng!";
    }
}

class Solution {
    
    public int maxInQueue(Queue<Integer> queue) {
    
    int maxval = queue.peek();
    int s = queue.size();
    for(int i = 1; i<s; i++)
    {
      queue.poll();
      maxval = maxval>queue.peek()? maxval : queue.peek();

    }
    return maxval;
    }
}
