import java.util.*;
public class abc
{
	public static void main(String args[])
	{
		A a = new A();
		A b = new A();
		System.out.println(A.type);
		System.out.println(a.type);
		System.out.println(b.type);
		A.showMessgae();
		a.type = 0;
		System.out.println(A.type);
		System.out.println(a.type);
		System.out.println(b.type);
		A.showMessgae();
		A.type = 2;
		System.out.println(A.type);
		System.out.println(a.type);
		System.out.println(b.type);
		A.showMessgae();
	}
}

class A {
	static int type = 1;
	static void showMessgae() {
		System.out.println("...");
	}
}
// a shared static object!
//member (fields, method)


class Solution {
	
	public int largestGroupingScore(String[] words, int k) {
		TrieNode root = toTrie(words);
		root.count = words.length;
		int[] count = new int[1];
		dfs(root, k, count);
		return count[0] - root.count/k;
	}
	
	private void dfs(TrieNode root, int k, int[] count) {
		if (root == null) {
			return;
		}
		if (root.count < k) {
			return;
		}
		for (int i = 0; i < 26; i++) {
			dfs(root.children[i], k, count);
		}
		count[0] += root.count / k;
	}
	
	private TrieNode toTrie(String[] words) {
		TrieNode root = new TrieNode();
		for (String word : words) {
			TrieNode cur = root;
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				if (cur.children[ch - 'a'] == null) {
					cur.children[ch - 'a'] = new TrieNode();
				}
				cur = cur.children[ch - 'a'];
				cur.count++;
				cur.depth = i + 1;
			}
		}
		return root;
	}
	
}

class TrieNode {
	TrieNode[] children = new TrieNode[26];
	int count;
	int depth;
}


