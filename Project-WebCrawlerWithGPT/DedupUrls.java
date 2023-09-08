import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DedupUrls {
    
    private static String fileToWrite = "urlsVisited22.txt";
    private static String fileToProcess = "urlsVisited.txt";
    private static ConcurrentHashSet urlsToVisit = new ConcurrentHashSet();
    
    public static void main(String[] args) throws java.net.SocketTimeoutException {
        
        int count = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileToProcess))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line of the file here
                if (line.length() == 0) {
                    continue;
                }
                urlsToVisit.add(line);
                count++;
                if (count % 100000 == 0) {
                    System.out.println("Read: " + count + "; Hashed: " + urlsToVisit.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Size after hashing: " + urlsToVisit.size());
        for (String url : urlsToVisit) {
            write(url + "\n", fileToWrite);
        }
    }
    
    private static void write(String s, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true); // true indicates append mode
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(s);
            //            bw.newLine(); // add a new line after the text
            bw.close(); // close the BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<String> readFileToList(String fileName, String spliter) {
        List<String> L = new ArrayList<>();
        System.out.println("Original size: " + L.size());
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(spliter);
            for (String s : array) {
                L.add(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName + " " + e.getMessage());
        }
        return L;
    }
}
