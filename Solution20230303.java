import java.util.*;
//利用单调栈可以实现O(1)时间获得前一天的最大温度值
//以下是一个OOD实例，在class TempRecorder中需要实现两个method
//由recordTemp()来记录某时间time的温度temp，时间单调不减
//由maxTempPrevDay()来获得前24小时内的最大温度

//做法是利用一个温度的单调栈，tempStack来记录单调递减的温度，递减顺序为ArrayDeque中Last递减到First
//并增加timeStack与tempStack同压同弹，记录每个tempStack中温度对应的时间
//此做法可以使得24小时内的温度最大值永远保持在tempStack的Last位置
//并且由于每个数值最多进出stack一次，可以使得maxTempPrevDay()和recordTemp()两个method只具有averaged O(1)的时间复杂度

class TempRecorder {
    
    Deque<Integer> tempStack;
    Deque<Integer> timeStack;
    
    public TempRecorder() {
        tempStack = new ArrayDeque<>();
        timeStack = new ArrayDeque<>();
    }
    
    public int maxTempPrevDay() {
        if (tempStack.size() == 0) {
            return Integer.MIN_VALUE;
        }
        return tempStack.peekLast();
    }
    
    public void recordTemp(int temp, int time) {
        if (tempStack.size() == 0) {
            tempStack.offerFirst(temp);
            timeStack.offerFirst(time);
            return;
        }
        
        while (tempStack.size() > 0 && tempStack.peekFirst() <= temp) { //维持单调递减，如果First端<=当前温度，poll
            tempStack.pollFirst();
            timeStack.pollFirst();
        }
        tempStack.offerFirst(temp); //把当前温度放入单调栈
        timeStack.offerFirst(time);
        
        while (timeStack.peekFirst() - timeStack.peekLast() > 24) {
            //这里是维持单调栈里时差不超过24
            timeStack.pollLast();
            tempStack.pollLast();
        }
    }
}


//public class Solution20230303 {
//    public static void main(String[] args) {
//        TempRecorder A = new TempRecorder();
//        String input = "0,1;3,5;5,2;7,3;10,4;15,7;19,10;23,2;26,8;29,11;39,15;42,5;49,7;59,9;64,8;72,11;79,12;87,16;99,12";
//        String[] array = input.split(";");
//        for (String s : array) {
//            String[] array2 = s.split(",");
//            A.recordTemp(Integer.valueOf(array2[1]), Integer.valueOf(array2[0]));
//            System.out.println(A.maxTempPrevDay());
//        }
//    }
//}
