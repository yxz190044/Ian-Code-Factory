import java.util.*;
public class LeetcodeArrayAndString {
	
	//    // "static void main" must be defined in a public class.
	public static void main(String[] args) {
		//        // 1. Initialize
		//        int[] a0 = new int[5];
		//        int[] a1 = {1, 2, 3};
		//        // 2. Get Length
		//        System.out.println("The size of a1 is: " + a1.length);
		//        // 3. Access Element
		//        System.out.println("The first element is: " + a1[0]);
		//        // 4. Iterate all Elements
		//        System.out.print("[Version 1] The contents of a1 are:");
		//        for (int i = 0; i < a1.length; ++i) {
		//            System.out.print(" " + a1[i]);
		//        }
		//        System.out.println();
		//        System.out.print("[Version 2] The contents of a1 are:");
		//        for (int item: a1) {
		//            System.out.print(" " + item);
		//        }
		//        System.out.println();
		//        // 5. Modify Element
		//        a1[0] = 4;
		//        // 6. Sort
		//        Arrays.sort(a1);
		//
		//
		//        // 1. initialize
		//        List<Integer> v0 = new ArrayList<>();
		//        List<Integer> v1;                           // v1 == null
		//        // 2. cast an array to a vector
		//        Integer[] a = {0, 1, 2, 3, 4};
		//        v1 = new ArrayList<>(Arrays.asList(a));
		//        // 3. make a copy
		//        List<Integer> v2 = v1;                      // another reference to v1
		//        List<Integer> v3 = new ArrayList<>(v1);     // make an actual copy of v1
		//        // 3. get length
		//        System.out.println("The size of v1 is: " + v1.size());
		//        // 4. access element
		//        System.out.println("The first element in v1 is: " + v1.get(0));
		//        // 5. iterate the vector
		//        System.out.print("[Version 1] The contents of v1 are:");
		//        for (int i = 0; i < v1.size(); ++i) {
		//            System.out.print(" " + v1.get(i));
		//        }
		//        System.out.println();
		//        System.out.print("[Version 2] The contents of v1 are:");
		//        for (int item : v1) {
		//            System.out.print(" " + item);
		//        }
		//        System.out.println();
		//        // 6. modify element
		//        v2.set(0, 5);       // modify v2 will actually modify v1
		//        System.out.println("The first element in v1 is: " + v1.get(0));
		//        v3.set(0, -1);
		//        System.out.println("The first element in v1 is: " + v1.get(0));
		//        // 7. sort
		//        Collections.sort(v1);
		//        // 8. add new element at the end of the vector
		//        v1.add(-1);
		//
		//        v1.add(1, 6); // this insert '6' at index 1.
		//        System.out.print("[Version 3] The contents of v1 are:");
		//        for (int i = 0; i < v1.size(); ++i) {
		//            System.out.print(" " + v1.get(i));
		//        }
		//        System.out.println();
		//        // 9. delete the last element
		//
		//
		//        System.out.println();
		//
		//        Student A = new Student();
		//        int [] B = {2,1,-1};
		//        System.out.println(A.pivotIndex(B));
		
		
		//		Student P = new Student();
		//
		//		int[] A;
		//		int[] B = new int[5];
		//		int[] C = {1,2,3};
		//		A = C;
		//		//B = {1,2,3,4,5};  or A = {1,2,3}; is wrong.
		//		System.out.println(A);
		//		System.out.println(C);
		//
		//		C[1] = 0;
		//		P.printArray1D(A);
		//		P.printArray1D(C);
		
		
		
		//		int[][] A = new int[4][5];
		//		for (int i= 0; i<A.length; i++)
		//		{
		//			System.out.println(A[i]);
		//		}
		//
		//		for (int i= 0; i<A.length; i++)
		//		{
		//			for (int j = 0; a[i] != null && j < a[i].length; ++j)
		//			{
		//				System.out.print(a[i][j] + " ");
		//			}
		//			System.out.println();
		//		}
		
		
		//		int[][] a = new int[2][5];
		//		int[][] b = new int[2][];
		//		System.out.println(b[0]);
		//		System.out.println(b[1]);
		//		b[0] = new int[3];
		//		b[1] = new int[5];
		
		Solution A = new Solution();
		
		//		int [][] a = {{1,2,3},{4,5,6} ,{7,8,9}};
		//		System.out.println(A.findDiagonalOrder(a));
		//		System.out.println(A.generate(5));
		
		// initialize
		//		String s1 = "Hello World";
		//		System.out.println("s1 is \"" + s1 + "\"");
		//		String s2 = s1;
		//		System.out.println("s2 is another reference to s1.");
		//		String s3 = new String(s1);
		//		System.out.println("s3 is a copy of s1.");
		//		// compare using '=='
		//		System.out.println("Compared by '==':");
		//		// true since string is immutable and s1 is binded to "Hello World"
		//		System.out.println("s1 and \"Hello World\": " + (s1 == "Hello World"));
		//		// true since s1 and s2 is the reference of the same object
		//		System.out.println("s1 and s2: " + (s1 == s2));
		//		// false since s3 is refered to another new object
		//		System.out.println("s1 and s3: " + (s1 == s3));
		//		// compare using 'equals'
		//		System.out.println("Compared by 'equals':");
		//		System.out.println("s1 and \"Hello World\": " + s1.equals("Hello World"));
		//		System.out.println("s1 and s2: " + s1.equals(s2));
		//		System.out.println("s1 and s3: " + s1.equals(s3));
		//		// compare using 'compareTo'
		//		System.out.println("Compared by 'compareTo':");
		//		System.out.println("s1 and \"Hello World\": " + (s1.compareTo("Hello World") == 0));
		//		System.out.println("s1 and s2: " + (s1.compareTo(s2) == 0));
		//		System.out.println("s1 and s3: " + (s1.compareTo(s3) == 0));
		
		
		//		String s1 = "Hello World";
		////		s1[5] = ',';  // error: array required, but String found
		//		s1 = "Heleo Worlf";
		//		s1 = s1 +" "+2;
		//		System.out.println(s1);
		//
		//		// 2. find
		//		System.out.println("The position of first 'o' is: " + s1.indexOf('o'));
		//		System.out.println("The position of last 'd' is: " + s1.lastIndexOf('d'));
		//		System.out.println("The position of last 'o' is: " + s1.lastIndexOf('o'));
		//		// 3. get substring
		//		System.out.println(s1.substring(6, 13));  //6<= substring < 13.
		//For instance, string length N, the time complexity of .finding and .substring is O(N).
		
		//		String s = "Hello World";
		//		char[] str = s.toCharArray();
		//		str[5] = ',';
		//		str[10] = '0';
		//		System.out.println(str);
		
		//		int n = 10000;
		//		StringBuilder str = new StringBuilder();
		//		for (int i = 0; i < n; i++) {
		//			str.append("hello");
		//		}
		//		String s = str.toString();
		//		System.out.println(str);
		
		//		String s="200";
		//		//converting String into Integer using Integer.valueOf() method
		//		Integer i=Integer.valueOf(s);
		//		System.out.println(i);
		//
		//		String binaryString="1010";
		//		int decimal=Integer.parseInt(binaryString,2);
		//		System.out.println(decimal);
		
		
		//		String a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
		//		String b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
		//		System.out.println(A.addBinary(a,b));
		
		String a = "hello";
		String b = "he";
		System.out.println(a.substring(0,2));
		System.out.println(a.substring(0,2).equals(b));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}



class Student
{
	public int i;
	public String getname()
	{
		return "Yiran Zheng!";
	}
	//    public Student(int i)
	//    {
	//        this.i = i;
	//    }
	
	public void printArray1D(int[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
	
	public void printArray2D(int[][] a)
	{
		for (int i = 0; i < a.length; ++i)
		{
			for (int j = 0; a[i] != null && j < a[i].length; ++j)
			{
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}


class Solution {
	public int pivotIndex(int[] nums) {
		int sum = 0;
		for(int i = 0; i<nums.length; i++)
		{
			sum = sum+ nums[i];
		}
		double sum1 = 0;
		for(int i = 0; i<nums.length; i++)
		{
			if(sum1 == (double)(sum - nums[i])/2)
			{
				return i;
			}
			sum1 = sum1 + nums[i];
		}
		return -1;
	}
	
	
	
	public int dominantIndex(int[] nums) {
		if(nums.length == 1)
		{
			return 0;
		}
		int max1 = nums[0] > nums[1]? nums[0] : nums[1];
		int max2 = nums[0] > nums[1]? nums[1] : nums[0];
		int j = nums[0] > nums[1]? 0 : 1;
		for (int i = 2; i<nums.length; i++)
		{
			if(nums[i] > max1)
			{
				max2 = max1;
				max1 = nums[i];
				j = i;
				continue;
			}
			if(nums[i] > max2)
			{
				max2 = nums[i];
			}
		}
		if(max1>=2*max2)
		{
			return j;
		}
		else
		{
			return -1;
		}
	}
	
	
	public int[] plusOne(int[] digits) {
		
		int i = digits.length-1;
		digits[i] = digits[i] + 1;
		while(digits[i]==10 && i>0)
		{
			digits[i] = 0;
			digits[i-1]++;
			i--;
		}
		if(digits[i] == 10)
		{
			int[] digits1 = new int[digits.length+1];
			digits1[0] = 1;
			return digits1;
		}
		else
		{
			return digits;
		}
	}
	
	
	public int[] findDiagonalOrder(int[][] mat) {
		int m = mat.length; //3
		int maxn = mat[0].length; //3
		int summn = 0;
		for(int k = 0; k<mat.length && mat[k] != null ; k++)
		{
			maxn = maxn>mat[k].length? maxn:mat[k].length;
			summn = summn + mat[k].length;
		}
		
		int maxmn = m> maxn? m: maxn;
		int i = 0;
		int j = 0;
		int [] answer = new int[summn];
		int sumij = 0;
		
		int ani = 0;
		for(sumij = 0; sumij < maxmn; sumij++)
		{
			if(sumij%2 == 0)
			{
				for(j = 0; j<=sumij; j++)
				{
					i = sumij - j;
					if(i<mat.length && j<mat[i].length)
					{
						answer[ani] = mat[i][j];
						ani++;
					}
				}
			}
			else
			{
				for(i = 0; i<=sumij; i++)
				{
					j = sumij - i;
					if(i<mat.length && j<mat[i].length)
					{
						answer[ani] = mat[i][j];
						ani++;
					}
				}
			}
		}
		
		for(sumij = maxmn; sumij <= 2*(maxmn-1); sumij++)
		{
			if(sumij%2 == 0)
			{
				for(i = maxmn - 1; i>= 1; i--)
				{
					j = sumij - i;
					if(i<mat.length && j<mat[i].length)
					{
						answer[ani] = mat[i][j];
						ani++;
					}
				}
			}
			else
			{
				for(j = maxmn - 1; j>= 1; j--)
				{
					i = sumij - j;
					if(i<mat.length && j<mat[i].length)
					{
						answer[ani] = mat[i][j];
						ani++;
					}
				}
			}
		}
		
		return answer;
		
	}
	
	//	public List<Integer> spiralOrder(int[][] matrix) {
	//		int m = matrix.length;
	//		int n = matrix[0].length;
	//		int nc = m<n? (m+1)/2 : (n+1)/2;
	//		List newList = new ArrayList();
	//		int j = 0;
	//
	//		for(int i = 0; i< nc; i++)
	//		{
	//			for(j = i; j< n-i; j++)
	//			{
	//				newList.add(matrix[i][j]);
	//			}
	//
	//			for(j = i+1; j<m-1-i; j++)
	//			{
	//				newList.add(matrix[j][n-1-i]);
	//			}
	//
	//			if(m-1-i > i)
	//			{
	//				for(j = n-i-1;j>= i; j-- )
	//				{
	//					newList.add(matrix[m-1-i][j]);
	//				}
	//			}
	//
	//			if(n-i-1 > i)
	//			{
	//				for(j = m-i-2; j> i; j--)
	//				{
	//					newList.add(matrix[j][i]);
	//				}
	//			}
	//		}
	//		return newList;
	//
	//	}
	
	
	// this problem I forgot ArrayList first element begins from get(0) instead of get(1)
	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> A = new ArrayList<List<Integer>>();
		List<Integer> B = new ArrayList<>();
		List<Integer> C = new ArrayList<>();
		
		B.add(1);
		A.add(B);
		if(numRows ==1)
		{
			return A;
		}
		C.add(1); C.add(1);
		A.add(C);
		if(numRows ==2)
		{
			return A;
		}
		
		for(int i = 3; i<= numRows; i++)
		{
			List<Integer> D = new ArrayList<>();
			D.add(1);
			for(int j = 2;j<i;j++)
			{
				D.add(A.get(i-2).get(j-2)+A.get(i-2).get(j-1));
			}
			D.add(1);
			A.add(D);
		}
		return A;
	}
	
	
	public String addBinary(String a, String b) {
		
		int la = a.length();
		int lb = b.length();
		int maxlab = la>lb? la:lb;
		int ai = maxlab - la;
		int bi = maxlab - lb;
		while(ai>0)
		{
			a = "0"+a;
			ai--;
		}
		while(bi>0)
		{
			b = "0"+b;
			bi--;
		}
		String s = "";
		
		int remember = 0;
		
		for(int i = 0; i<maxlab; i++)
		{
			int a1 = Integer.parseInt(a.substring(maxlab-i-1,maxlab-i),2);
			int b1 = Integer.parseInt(b.substring(maxlab-i-1,maxlab-i),2);
			int c = a1 + b1 + remember;
			if(c > 1)
			{
				c = c-2;
				remember = 1;
			}
			else
			{
				remember = 0;
			}
			s = c + s;
		}
		if(remember == 1)
		{
			s = remember + s;
		}
		return s;
		
	}
	
	public int strStr(String haystack, String needle) {
		if(needle == "")  return 0;
		int a = haystack.length();
		int b = needle.length();
		for(int i = 0; i<= a-b; i++ )
		{
			if(haystack.substring(i,i+b).equals(needle)) return i;
		}
		return -1;
		
	}
	
	
	public String longestCommonPrefix(String[] strs) {
			if (strs.length == 0) {
				return "";
			}
			String prefix = strs[0];
			for (int i = 0; i < strs.length; i++) {
				while(strs[i].indexOf(prefix) != 0) {
					prefix = prefix.substring(0, prefix.length() -1);
					if (prefix.isEmpty()) {
						return "";
					}
				}
			}
			return prefix;
		}
	
	public String yiranlongestCommonPrefix(String[] strs) {
			int l = strs.length;
			if(l == 1)  return strs[0];
			for(int i = 0; i<l; i++)
			{
				if(strs[i].length() == 0)  return "";
			}
			
			int j = 1;
			while(j>0)
			{
				if(strs[0].length() < j) return strs[0].substring(0,j-1);
				
				for(int i = 1; i<l; i++)
				{
					if(strs[i].length() <j || !strs[i].substring(0,j).equals(strs[0].substring(0,j)))
					{
						return strs[0].substring(0,j-1);
					}
				}
				
				j++;
			}
			return "";
		}
}















