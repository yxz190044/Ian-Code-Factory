import java.util.*;

public class Solution4 {
	public static void main(String[] args) {
		Solution sol = new Solution();
	}
	
}

public class Solution {
	static int disappear(int[][] grid, int row, int col) {
		if (grid == null || grid.length == 0) {
			return 0;
		}
		if (grid[0] == null || grid[0].length == 0) {
			return 0;
		}
		if (row < 0 || row >= grid.length) {
			return 0;
		}
		if (col < 0 || col >= grid[0].length) {
			return 0;
		}
		int[] count = new int[1];
		dfs(grid, row, col, grid[row][col], count);
		return count[0];
	}
	
	private static void dfs(int[][] grid, int row, int col, int target, int[] count) {
		if (row < 0 || row >= grid.length) {
			return;
		}
		if (col < 0 || col >= grid[0].length) {
			return;
		}
		if (grid[row][col] != target) {
			return;
		}
		grid[row][col] = 10;
		count[0]++;
		dfs(grid, row - 1, col, target, count);
		dfs(grid, row + 1, col, target, count);
		dfs(grid, row, col - 1, target, count);
		dfs(grid, row, col + 1, target, count);
	}
}
