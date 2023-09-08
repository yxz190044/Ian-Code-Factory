import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution1 {
	public static void main(String args[]) throws Exception {
		Solution sol = new Solution();
		int[][] matrix = {{1, 1, 1, 1, 2}, {2, 2, 3, 4, 5}, {1, 2, 4, 5, 6}, {1, 4, 6, 7, 8}, {2, 3, 5, 7, 9}};
		for (int[] array : matrix) {
			for (int num : array) {
				System.out.print(num + " ");
			}
			System.out.println();
 		}
		System.out.println(sol.maxArea(matrix, 1, 1));
		System.out.println(sol.maxArea(matrix, 1, 2));
		System.out.println(sol.maxArea(matrix, 2, 1));
		System.out.println(sol.maxArea(matrix, 2, 2));
		System.out.println(sol.maxArea(matrix, 2, 3));
		System.out.println(sol.maxArea(matrix, 3, 2));
		System.out.println(sol.maxArea(matrix, 3, 3));
	}
}

class Solution {
	
	int[][] matrix;
	int[][] leftUpArm;
	int[][] rightUpArm;
	int[][] leftRightUpPreSum;
	int m;
	int n;
	int max;
	
	
	
	public int maxArea(int[][] matrix, int a, int b) {
		m = matrix.length;
		n = matrix[0].length;
		this.matrix = matrix;
		leftUpArm();
		rightUpArm();
		leftRightUpPreSum();
		max = Integer.MIN_VALUE;
		maxArea(a, b);
		maxArea(b, a);
		return max;
	}
	
	private void maxArea(int a, int b) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int Ai = i, Aj = j;
				int Bi = i - a + 1, Bj = j - a + 1;
				int Ci = i - b + 1, Cj = j + b - 1;
				int Di = i - a - b + 2, Dj = j - a + b;
				if (Bj < 0 || Cj >= n || Di < 0) {
					continue;
				}
				int rA = leftRightUpPreSum[Ai][Aj];
				int rB = leftRightUpPreSum[Bi][Bj];
				int rC = leftRightUpPreSum[Ci][Cj];
				int rD = leftRightUpPreSum[Di][Dj];
				int r1 = rightUpArm[Bi][Bj] - rightUpArm[Di][Dj];
				int r2 = leftUpArm[Ci][Cj] - leftUpArm[Di][Dj];
				int result = rA - rB - rC + rD + r1 + r2 + matrix[Di][Dj];
				max = Math.max(max, result);
			}
		}
	}
	
	private void leftUpArm() {
		leftUpArm = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int leftUp = (i == 0 || j == 0) ? 0 : leftUpArm[i - 1][j - 1];
				leftUpArm[i][j] = matrix[i][j] + leftUp;
			}
		}
	}
	
	private void rightUpArm() {
		rightUpArm = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = n - 1; j >= 0; j--) {
				int rightUp = (i == 0 || j == n - 1) ? 0 : rightUpArm[i - 1][j + 1];
				rightUpArm[i][j] = matrix[i][j] + rightUp;
			}
		}
	}
	
	private void leftRightUpPreSum() {
		leftRightUpPreSum = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int up = i == 0 ? 0 : leftRightUpPreSum[i - 1][j];
				int left = leftUpArm[i][j];
				int right = rightUpArm[i][j];
				leftRightUpPreSum[i][j] = up + left + right - matrix[i][j];
			}
		}
	}
	
}
