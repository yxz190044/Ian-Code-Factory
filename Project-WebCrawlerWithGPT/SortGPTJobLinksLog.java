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

public class SortGPTJobLinksLog {
    
    private static String fileToProcess = "careerLinks/sdeJobLinks/gptProcessedJobLinksLog.txt";
    private static String fileToWrite = "careerLinks/sdeJobLinks/sortedGPTJobLinksLog.txt";
    
    public static void main(String[] args) {
        
        List<String> list = readFileToList(fileToProcess, "\n");
        System.out.println("List Size: " + list.size());
        if (list.size() % 2 != 0) {
            System.out.println("Error: last line is: " + list.get(list.size() - 1));
        }
        List<JobLink> L = new ArrayList<>();
        for (int index = 0; index < list.size(); index += 2) {
            String s = list.get(index);
            String[] array = s.split("; ");
            String url = list.get(index + 1);
            JobLink jobLink = new JobLink(url);
            jobLink.isJobPost = true;
            jobLink.inUS = true;
            jobLink.isSDE = true;
            jobLink.jobTitle = array[0];
            jobLink.isEntryLevel = array[2].charAt(0) == 'E';
            jobLink.years = getNum(array[1]);
            jobLink.matchedTechFramesCount = getNum(array[4]);
            jobLink.techFrames = array[3];
            L.add(jobLink);
        }
        
        Collections.sort(L, new Comparator<> () {
            @Override
            public int compare(JobLink j1, JobLink j2) {
                boolean isHighlvl1 = j1.jobTitle.contains("Senior") || j1.jobTitle.contains("Staff");
                boolean isHighlvl2 = j2.jobTitle.contains("Senior") || j2.jobTitle.contains("Staff");
                if (isHighlvl1 ^ isHighlvl2) {
                    return isHighlvl1? 1 : -1;
                }
                if (j1.isEntryLevel ^ j2.isEntryLevel) {
                    return j1.isEntryLevel ? -1 : 1;
                }
                if (j1.years == j2.years) {
                    return 0;
                }
                return j1.years < j2.years ? -1 : 1;
            }
        });
        
        for (JobLink j : L) {
            write(j.asString(), fileToWrite);
        }
    }
    
    private static int getNum(String s) {
        int i = 0;
        int num = 0;
        while (i < s.length()) {
            char ch = s.charAt(i++);
            if (ch >= '0' && ch <= '9') {
                num *= 10;
                num += ch - '0';
            }
        }
        return num;
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

