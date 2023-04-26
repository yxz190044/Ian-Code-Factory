import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	static TreeNode[] tree;
	static StringBuilder sb;
	static String[] pairs;
	static TreeNode root;
	static boolean zeroRoot;
	static String input;
	
	public static void main(String args[]) throws Exception {
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		input = reader.readLine();
		String input = "(D,A) (D,B) (A,C) (A,F) (B,E) (F,G) (F,H) (G,Q) (H,I) (I,M) (M,N)";
		int processResult = process(input);
		if (processResult == 0) {
			System.out.println(sb.toString());
		} else {
			System.out.println("E" + processResult);
		}
		
	}
	
	private static int process(String input) {
		sb = new StringBuilder();
		if (input == null || input.length() == 0) {
			sb.append("()");
			return 0;
		}
		pairs = input.split(" ");
		
		if (invalidFormat()) {
			return 1;
		}
		if (duplicatePair()) {
			return 2;
		}
		if (moreThanTwoChildren()) {
			return 3;
		}
		if (multipleRoots()) {
			return 4;
		}
		if (cyclicTree()) {
			return 5;
		}
		serializeTree();
		return 0;
	}
	
	private static void serializeTree() {
		serializeTree(root);
	}
	
	private static void serializeTree(TreeNode cur) {
		if (cur == null) {
			return;
		}
		sb.append("(");
		sb.append(cur.ch);
		serializeTree(cur.left);
		serializeTree(cur.right);
		sb.append(")");
	}
	
	
	private static boolean invalidFormat() {
		for (String pair : pairs) {
			if (pair.length() != 5) {
				return true;
			}
			char[] array = pair.toCharArray();
			if (array[0] != '(' || array[4] != ')' || array[2] != ',') {
				return true;
			}
			if (array[1] < 'A' || array[1] > 'Z') {
				return true;
			}
			if (array[3] < 'A' || array[3] > 'Z') {
				return true;
			}
		}
		return false;
	}
	
	private static boolean duplicatePair() {
		HashSet<Integer> seen = new HashSet<>();
		for (String pair : pairs) {
			char[] array = pair.toCharArray();
			int num = (array[1] - 'A') * 26 + array[3] - 'A';
			if (seen.contains(num)) {
				return true;
			}
			seen.add(num);
		}
		return false;
	}
	
	private static boolean moreThanTwoChildren() {
		//this error check is done during building the tree
		tree = new TreeNode[26];
		for (String pair : pairs) {
			char[] array = pair.toCharArray();
			char p = array[1];
			char c = array[3];
			if (tree[p - 'A'] == null) {
				tree[p - 'A'] = new TreeNode(p);
			}
			if (tree[c - 'A'] == null) {
				tree[c - 'A'] = new TreeNode(c);
			}
			TreeNode parent = tree[p - 'A'];
			TreeNode child = tree[c - 'A'];
			if (parent.left == null) {
				parent.left = child;
			} else if (parent.right == null) {
				parent.right = child;
			} else {
				return true;
			}
		}
		return false;
	}
	
	private static boolean multipleRoots() {
		//this error check is done during find the root
		//and assign the root reference
		zeroRoot = false;
		int[] parentCount = new int[26];
		int[] childCount = new int[26];
		for (String pair : pairs) {
			char[] array = pair.toCharArray();
			char p = array[1];
			char c = array[3];
			parentCount[p - 'A']++;
			childCount[c - 'A']++;
		}
		
		char r = '#';
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			if (parentCount[ch - 'A'] > 0 && childCount[ch - 'A'] == 0) {
				if (r != '#') {
					return true;
				}
				r = ch;
			}
		}
		if (r == '#') {
			zeroRoot = true;
		} else {
			root = tree[r - 'A'];
		}
		return false;
	}
	
	private static boolean cyclicTree() {
		if (zeroRoot) {
			return true;
		}
		boolean[] seen = new boolean[26];
		Queue<TreeNode> bfs = new ArrayDeque<>();
		bfs.offer(root);
		while (bfs.size() != 0) {
			TreeNode cur = bfs.poll();
			if (seen[cur.ch - 'A']) {
				return true;
			}
			seen[cur.ch - 'A'] = true;
			if (cur.left != null) {
				bfs.offer(cur.left);
			}
			if (cur.right != null) {
				bfs.offer(cur.right);
			}
		}
		return false;
	}
}


class TreeNode {
	TreeNode left;
	TreeNode right;
	char ch;
	public TreeNode(char ch) {
		this.ch = ch;
	}
}



class Solution {
	// String Compression II
	int dp[][];
	
	public int getLengthOfOptimalCompression(String ss, int k) {
		char[] s = ss.toCharArray();
		dp = new int[s.length][k + 1];
		for(int[] row : dp) Arrays.fill(row, -1);
		
		int res = dfs(s, 0, k);
		// for(int[] row : dp) System.out.println(Arrays.toString(row));
		return res;
	}
	
	private int dfs(char[] s, int curIdx, int rest) {
		// reach end or we can simply delete all
		if(curIdx == s.length || s.length - curIdx <= rest) return 0;
		if(dp[curIdx][rest] != -1) return dp[curIdx][rest];
		int[] fre = new int[26];
		int most = 0, res = Integer.MAX_VALUE; // most is the highest frequency
		// i - curIdx + 1 - most. count of chars we need to delete
		// but we cannot break. Because when most change, rest could also change.
		// So we must iterate to end
		for(int i = curIdx; i < s.length; i++) {
			int idx = s[i] - 'a';
			fre[idx]++;
			most = Math.max(most, fre[idx]);
			if(rest >= i - curIdx + 1 - most) // only recurse when we can delete all other chars
				res = Math.min(res, getLen(most) + 1 + dfs(s, i + 1, rest - (i - curIdx + 1 - most)));
		}
		dp[curIdx][rest] = res;
		return res;
		
	}
	
	private int getLen(int most) {
		if(most == 1) return 0;
		if(most < 10) return 1;
		if(most < 99) return 2;
		return 3;
	}
}


class Solution {
	
	// 1307. Verbal Arithmetic Puzzle
	
	int[] mapping;
	boolean[] used;
	public boolean isSolvable(String[] words, String result) {
		mapping = new int[26];
		Arrays.fill(mapping, -1);
		used = new boolean[10];
		int maxLen = 0;
		for (int i = 0; i < words.length; i++) {
			words[i] = new StringBuilder(words[i]).reverse().toString();
			maxLen = Math.max(maxLen, words[i].length());
		}
		if (maxLen > result.length() || maxLen+1 < result.length()) return false;
		result = new StringBuilder(result).reverse().toString();
		return solve(words, result, 0, 0, 0);
	}
	
	boolean solve(String[] words, String result, int i, int j, int sum) {
		if (j == result.length()) {
			if (sum != 0)
				return false;
			for (int k = 0; k < words.length; k++) {
				if (mapping[words[k].charAt(words[k].length()-1)-'A'] == 0 && words[k].length() > 1)
					return false;
			}
			if (mapping[result.charAt(result.length()-1)-'A'] == 0 && result.length() > 1)
				return false;
			return true;
		}
		
		if (i == words.length) {
			int idx = result.charAt(j)-'A';
			if (mapping[idx] != -1) {
				if (mapping[idx] == sum%10) {
					return solve(words, result, 0, j+1, sum/10);
				}
				else
					return false;
			} else {
				int val = sum%10;
				if (used[val]) return false;
				mapping[idx] = val;
				used[val] = true;
				boolean res = solve(words, result, 0, j+1, sum/10);
				used[val] = false;
				mapping[idx] = -1;
				return res;
			}
		}
		
		if (j >= words[i].length())
			return solve(words, result, i+1, j, sum);
		
		char c = words[i].charAt(j);
		int idx = c-'A';
		if (mapping[idx] != -1) {
			return solve(words, result, i+1, j, sum+mapping[idx]);
		}
		
		boolean flag = false;
		for (int k = 0; k < 10; k++) {
			if (used[k]) continue;
			used[k] = true;
			mapping[idx] = k;
			flag = solve(words, result, i+1, j, sum+k);
			if (flag) return true;
			mapping[idx] = -1;
			used[k] = false;
		}
		return false;
	}
}


class Solution {
	
	//Substring With Largest Variance
	public int largestVariance(String s) {
		int max = 0;
		for (char ch1 = 'a'; ch1 <= 'z'; ch1++) {
			for (char ch2 = 'a'; ch2 <= 'z'; ch2++) {
				if (ch1 == ch2) {
					continue;
				}
				max = Math.max(max, maxDiff(s, ch1, ch2));
			}
		}
		return max;
	}
	
	private int maxDiff(String s, char ch1, char ch2) {
		int count = 0;
		boolean hasTwo = false;
		int max = 0;
		int preMin = 0;
		
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == ch1) {
				count++;
			} else if (ch == ch2) {
				if (hasTwo) {
					count -= preMin;
					preMin = 0;
				} else {
					hasTwo = true;
				}
				count--;
			}
			preMin = Math.min(preMin, count);
			if (hasTwo) {
				max = Math.max(max, count);
			}
		}
		if (!hasTwo) {
			return 0;
		}
		return max;
	}
}

