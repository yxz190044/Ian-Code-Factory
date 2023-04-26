import java.util.*;
import java.math.*;

class Solution {
    
    int[] solution(String[] queries, int diff) {
        int[] result = new int[queries.length];
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int index = 0;
        for (String query : queries) {
            int num = getNum(query);
            
            int eff = 0;
            if (map.containsKey(num - diff) && map.containsKey(num - 2 * diff)) {
                eff += map.get(num - diff) * map.get(num - 2 * diff);
            }
            if (map.containsKey(num + diff) && map.containsKey(num - diff)) {
                eff += map.get(num + diff) * map.get(num - diff);
            }
            if (map.containsKey(num + diff) && map.containsKey(num + 2 * diff)) {
                eff += map.get(num + diff) * map.get(num + 2 * diff);
            }
            
            if (query.charAt(0) == '+') {
                count += eff;
                map.put(num, map.getOrDefault(num, 0) + 1);
            } else {
                count -= map.getOrDefault(num, 0) * eff;
                map.remove(num);
            }
            result[index++] = count;
        }
        return result;
    }
    
    int getNum(String query) {
        int num = 0;
        for (int i = 1; i < query.length(); i++) {
            num *= 10;
            num += query.charAt(i) - '0';
        }
        return num;
    }
}

public class Solution20230329_4 {
    public static void main(String args[]) throws Exception {
        String[] q = {"+4", "+5", "+6", "+4", "+3", "-4"};
        Solution A = new Solution();
        int[] result =A.solution(q, 1);
        for (int num : result) {
            System.out.print(num + " ");
        }
    }
}



