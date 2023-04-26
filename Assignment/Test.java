import java.lang.Cloneable;
import java.util.*;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        A a = new A(20, "GeeksForGeeks");
        A b = (A)a.clone(); //  b是a的克隆
        b.array[0] = 1;
        b.matrix[1][1] = 1;
        b.s = "abc";
        System.out.println(a.array[0]);
        System.out.println(a.matrix[1][1]);
        System.out.println(a.s);
    }

}


class A implements Cloneable {
    int i;
    String s;
    int[] array = new int[2];
    int[][] matrix = new int[2][2];
    // A class constructor
    public A(int i, String s) {
        this.i = i;
        this.s = s;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


