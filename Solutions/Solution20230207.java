class Solution {
    public int solution(int[] A, int[] B) {
        long sum = sum(A);
        if (sum != sum(B) || sum % 2 != 0) {
            return 0;
        }

        long count1 = 0;
        long count2 = 0;
        int count = 0;
        for (int i = 0; i < A.length - 1; i++) {
            count1 += (long) A[i];
            count2 += (long) B[i];
            if (count1 == count2 && sum/2 == count1) {
                count++;
            }
        }
        return count;
    }
    
    private long sum(int[] array) {
        long sum = 0;
        for (int num : array) {
            sum += (long) num;
        }
        return sum;
    }
}
