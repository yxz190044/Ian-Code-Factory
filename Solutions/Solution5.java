import java.io.*;
import java.util.*;
//import java.text.*;
//import java.math.*;
//import java.util.regex.*;

public class Solution5 {
	public static void main(String[] args) throws IOException {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String line;
		String s1 = in.readLine();
		String s2 = in.readLine();
		System.out.println(findCommon(s1, s2));
	}
	
	public static String findCommon(String s1, String s2) {
		String[] array1 = s1.split(" ");
		String[] array2 = s2.split(" ");
		HashMap<String, Integer> map = new HashMap<>();
		List<String> L = new ArrayList<>();
		for (String s : array1) {
			map.put(s, map.getOrDefault(s, 0) + 1);
		}
		StringBuilder sb = new StringBuilder();
		for (String s : array2) {
			if (map.containsKey(s)) {
				int count = map.get(s);
				if (count == 1) {
					map.remove(s);
				} else {
					map.put(s, count - 1);
				}
				L.add(s);
				sb.append(s);
				sb.append(" ");
			}
		}
		if (sb.length() == 0) {
			return "NULL";
		}
		sb.setLength(sb.length() - 1);
		Collections.sort(L);
		return "";
	}
}


