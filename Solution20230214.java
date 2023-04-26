import java.io.*;
import java.util.*;

public class Solution20230214 {
    public static void main(String args[]) throws Exception {

        int[] array1 = new int[] {12, 2, 6, 7, 11};
        int[] array2 = new int[] {6, 4};
        int[] array3 = new int[] {8, 1, 3, 5, 7};
        int[] array4 = new int[] {100};
        System.out.println(Main.SeatingStudents(array1));
        System.out.println(Main.SeatingStudents(array2));
        System.out.println(Main.SeatingStudents(array3));
        System.out.println(Main.SeatingStudents(array4));
    }
	
}

class Main {
    public static int SeatingStudents(int[] arr) {
        int count = 0;
        int size = arr[0];
        int i = 1;
        int j = 1;
        while (i < size) {
            if (j < arr.length && arr[j] == i) {
                i++;
                j++;
                continue;
            }
            if (i % 2 == 1) {
                if (j == arr.length || i + 1 != arr[j]) {
                    count++;
                }
                if (i + 2 <= size && (j == arr.length || arr[j] != i + 2)) {
                    if (j + 1 >= arr.length || arr[j + 1] != i + 2) {
                        count++;
                    }
                }
            } else {
                if (i + 2 <= size && (j == arr.length || arr[j] != i + 2)) {
                    if (j + 1 >= arr.length || arr[j + 1] != i + 2) {
                        count++;
                    }
                }
            }
            i++;
        }
        return count;
    }
}


