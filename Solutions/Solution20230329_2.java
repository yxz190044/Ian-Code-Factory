import java.util.*;
import java.math.*;

class Solution {
    String solution(String message) {
        char[] array = message.toCharArray();
        char pre = '0';
        int index = array.length - 1;
        while (index >= 0) {
            if (isAEIOU(array[index])) {
                pre = array[index];
                break;
            }
            index--;
        }
        if (index < 0) {
            System.out.println("<0");
            return message;
        }
        
        for (int i = 0; i < array.length; i++) {
            if (isAEIOU(array[i])) {
                char temp = array[i];
                array[i] = pre;
                pre = temp;
            }
        }
        return new String(array);
    }
    
    boolean isAEIOU(char ch) {
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
            return true;
        }
        return false;
    }
}

public class Solution20230329_2 {
    public static void main(String args[]) throws Exception {
        String s = "codesignal";
        Solution A = new Solution();
        System.out.println(A.solution(s));
    }
}



