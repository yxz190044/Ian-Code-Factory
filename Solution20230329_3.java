import java.util.*;
import java.math.*;

class Solution {
    int solution(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (isRegular(i, j, matrix)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    boolean isRegular(int row, int col, int[][] matrix) {
        int num = -1;
        
        for (int i = 0; i < matrix.length; i++) {
            if (i == row) {
                continue;
            }
            if (num == -1) {
                num = matrix[i][col];
                continue;
            }
            if (num != matrix[i][col]) {
                return false;
            }
        }
        
        for (int j = 0; j < matrix[0].length; j++) {
            if (j == col) {
                continue;
            }
            if (num == -1) {
                num = matrix[row][j];
                continue;
            }
            if (num != matrix[row][j]) {
                return false;
            }
        }
        return true;
    }
}

public class Solution20230329_3 {
    public static void main(String args[]) throws Exception {
        
        int[][] matrix = {{1, 1, 1, 1}, {2, 3, 1, 1}, {1, 1, 1, 0}, {1, 4, 1, 1}};
        Solution A = new Solution();
        System.out.println(A.solution(matrix));
    }
}



