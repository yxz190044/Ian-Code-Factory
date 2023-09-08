import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SortEmail {
    
    private static String fileToWrite = "crawlerEmailsPreProcessed.txt";
    private static int oneBatchNum = 1000000;
    private static int total = 1000000;
    
    public static void main(String[] args) {
        
        List<String> emails = new ArrayList<>();
        String fileName = "crawlerEmailsCopy.txt"; // Replace with your file name
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        int num7 = 0;
        int num8 = 0;
        int num9 = 0;
        int num10 = 0;
        int num11 = 0;
        
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(",");
            System.out.println("entire size was " + array.length);
            for (String s : array) {
                
                String[] arr = s.split("@");
                String[] arr2 = arr[1].split("\\.");
                
                // corner Case not .com
                if (!arr2[arr2.length - 1].equals("com") && !arr2[arr2.length - 1].equals("COM")) {
                    num1++;
                    continue;
                }
                if (arr[0].equals("privacy")) {
                    num2++;
                    continue;
                }
                if (arr2.length >= 2 && arr2[arr2.length - 2].equals("example")) {
                    //                    System.out.println(arr[1]);
                    num3++;
                    continue;
                }
                if (arr2.length >= 2 && arr2[arr2.length - 2].equals("gmail")) {
                    //                    System.out.println(arr[1]);
                    num4++;
                    continue;
                }
                if (arr2.length >= 2 && arr2[arr2.length - 2].equals("email")) {
                    //                    System.out.println(arr[1]);
                    num5++;
                    continue;
                }
                // too long
                if (arr[0].length() > 24 || arr[1].length() > 24) {
                    num6++;
                    continue;
                }
                // begin with .
                if (arr[0].charAt(0) == '.') {
                    num7++;
                    continue;
                }
                // contains ...
                String[] arr3 = arr[0].split("\\.\\.");
                if (arr3.length != 1) {
                    //                    System.out.println(arr[0]);
                    num8++;
                    continue;
                }
                arr3 = s.split("\\.@");
                if (arr3.length != 1) {
                    //                    System.out.println(arr[0]);
                    num9++;
                    continue;
                }
                arr3 = s.split("@\\.");
                if (arr3.length != 1) {
                    //                    System.out.println(arr[0]);
                    num10++;
                    continue;
                }
                if (s.substring(0, 4).equals("u003")) {
                    //                    System.out.println(arr[0]);
                    num11++;
                    continue;
                }
                emails.add(s);
            }
            System.out.println("" + num1 + " " + num2 + " " + num3 + " " + num4 + " " + num5);
            System.out.println("" + num6 + " " + num7 + " " + num8 + " " + num9 + " " + num10);
            System.out.println("" + num11);
            System.out.println("after filter " + emails.size());
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        
        Collections.sort(emails, new Comparator<>() {
            @Override
            public int compare(String s1, String s2) {
                String[] arr1 = s1.split("@");
                String[] arr2 = s2.split("@");
                return arr1[1].compareTo(arr2[1]);
            }
        });
        int count = 0;
        String suff = "";
        for (String s : emails) {
            String[] arr = s.split("@");
            if (arr[1].equals(suff)) {
                count++;
            } else {
//                System.out.println(suff + "    " + count);
                suff = arr[1];
                count = 1;
            }
        }
//        System.out.println(suff + "    " + count);
        Collections.shuffle(emails);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total && i < emails.size(); i++) {
            sb.append(emails.get(i));
            sb.append(",");
//            if (i % oneBatchNum == (oneBatchNum - 1)) {
//                sb.append("\n");
//                write(sb.toString());
//                sb = new StringBuilder();
//            }
        }
        sb.append("\n");
        write(sb.toString());
        sb = new StringBuilder();
    }
    
    private static void write(String s) {
        try {
            FileOutputStream fos = new FileOutputStream(fileToWrite, true); // true indicates append mode
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(s);
            //            bw.newLine(); // add a new line after the text
            bw.close(); // close the BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

