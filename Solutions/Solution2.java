import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution2 {
	
	
	public static void main(String args[]) throws Exception {
		
		Solution sol = new Solution();
		System.out.println(sol.canReach(1, 1, 4, 7, 6));
	}
	
	
}







class Solution {
	
	HashSet<Integer> seen;
	HashSet<Integer> squares;
	int c;
	int x2;
	int y2;
	
	public boolean canReach(int x1, int y1, int x2, int y2, int c) {
		this.c = c;
		this.x2 = x2;
		this.y2 = y2;
		buildSquares();
		if (x1 == x2)
		if (squares.contains(x1 + y1) || squares.contains(x2 + y2)) {
			return false;
		}
		seen = new HashSet<>();
		Queue<Integer> bfs = new ArrayDeque<>();
		bfs.offer(x1 * 1001 + y1);
		seen.add(x1 * 1001 + y1);
		while (bfs.size() != 0) {
			int cur = bfs.poll();
			int x = cur / 1001;
			int y = cur % 1001;
			boolean found = false;
			found |= expand(x + y, y, bfs);
			found |= expand(x, x + y, bfs);
			found |= expand(x + c, y + c, bfs);
			if (found) {
				return true;
			}
		}
		return false;
	}
	
	private boolean expand(int x, int y, Queue<Integer> bfs) {
		if (x == x2 && y == y2) {
			return true;
		}
		if (x > x2 || y > y2) {
			return false;
		}
		if (seen.contains(x * 1001 + y)) {
			return false;
		}
		if (squares.contains(x + y)) {
			return false;
		}
		bfs.offer(x * 1001 + y);
		seen.add(x * 1001 + y);
		return false;
	}
	
	private void buildSquares() {
		squares = new HashSet<>();
		for (int i = 1; i * i < 2000; i++) {
			squares.add(i * i);
		}
	}
}

