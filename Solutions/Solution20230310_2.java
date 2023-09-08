import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Solution20230310_2 {
    public static void main(String[] args) {
        String fileName = "crawlerEmails.txt"; // Replace with your file name
        
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            StringBuilder sb = new StringBuilder();
            String[] array = fileContents.split(",");
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                sb.append(",");
                if (i % 100 == 99) {
                    System.out.println(sb);
                    sb = new StringBuilder();
                }
            }
            System.out.println(sb);
//            System.out.println(fileContents); // Print file contents to console
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        
        
    }
    
//    private static String getString() {
//
//        return input;
//    }
}
