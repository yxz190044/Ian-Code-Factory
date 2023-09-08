import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class Solution20230205 {
	public static void main(String args[]) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine(); // line accepts data
        String[][] matrix = array2matrix(input);
        for (String[] array : matrix) {
            for (String s : array) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
    
    public static String[][] array2matrix(String input) {
       
        if (input == null || input.length() == 0) {
            return new String[0][0];
        }
        String[] array1 = input.split(";");
        String[] array11 = array1[0].split(",");
        String[][] matrix = new String[array11.length/2 + 1][array1.length + 1];
        HashMap<String, Integer> map = new HashMap<>();
        int curRow = 1;
        for (int i = 0; i < array1.length; i++) {
            if (array1[i].length() == 0) {
                continue;
            }
            array11 = array1[i].split(",");
            for (int j = 0; j < array11.length; j += 2) {
                String c0 = array11[j];
                String c1 = array11[j + 1];
                if (!map.containsKey(c0)) {
                    map.put(c0, curRow++);
                }
                int row = map.get(c0);
                int col = i + 1;
                
                if (j == array11.length - 2) {
                    String[] array2 = c1.split(":");
                    matrix[0][col] = array2[1].substring(1, array2[1].length());
                    matrix[row][col] = array2[0];
                    matrix[row][0] = c0;
                    continue;
                }
                matrix[row][col] = c1;
                matrix[row][0] = c0;
            }
        }
        return matrix;
    }
}

