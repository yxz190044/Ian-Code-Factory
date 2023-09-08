import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// This file is not ready to use yet.

public class WebCrawlerBFSForJobLinks {
    
    // BFS with Queue and map
    // stopped working on it since 20230503
    // The web crawlers I do right now only iterate the current page
    
    private ConcurrentHashSet pagesCrawledSet = new ConcurrentHashSet();
    private ConcurrentHashSet pagesAddedSet = new ConcurrentHashSet();
    private Queue<String> pagesToVisitQueue = new ArrayDeque<>();
    
    private String pagesCrawledFileName = "urlCrawled.txt";
    private String pagesAddedFileName = "urlAdded.txt";
    private String pagesFoundFileName = "urlFound.txt";
    private String queries = "1. is this page a job posting page? 2. Does this job locates in US? 3. Is this job a software engineer job? 4. Is this job a data engineer job? 5. Should new grad apply to this position? 6. Should applicant with 0-year experience apply to this position? 7. Should applicant with under 2-year experience applying to this position? 8. Should applicant with under 4-year experience applying to this position? 9. What are the demanded tech frames, ranked by importance?";

    public static void main(String[] args) {
        
        WebCrawlerForJobLinks crawler = new WebCrawlerForJobLinks();
        crawler.configure();
        crawler.initiate();
    }
    
    public void initiate() {
        
        while (pagesToVisitQueue.size() > 0) {
            String url = pagesToVisitQueue.poll();
            if (!urlCornorCasePass(url)) {
                continue;
            }
            WebContent wc = new WebContent(url);
            Document document = wc.document;
            String content = document.text();
            if (!webContentCornorCasePass(document, content)) {
                continue;
            }
            
//            System.out.println(content);
            pagesCrawledSet.add(url);
            List<String> nexts = wc.nexts;
            
            String[] array = content.split("\n");
            StringBuilder sb = new StringBuilder();
            for (String s : array) {
                sb.append(s);
            }
            
            for (String next : nexts) {
                if (pagesAddedSet.contains(next)) {
                    continue;
                }
                pagesAddedSet.add(next);
                pagesToVisitQueue.offer(next);
                //            System.out.println(num + " " + next);
            }
            
            ChatGPTReadJobPage rjp = new ChatGPTReadJobPage(sb.toString(), queries);
            System.out.println(rjp.answers);
        }
    }
    
    private boolean urlCornorCasePass(String url) {
        if (pagesCrawledSet.contains(url)) {
            return false;
        }
        if (url.contains("www.linkedin.com")) {
            return false;
        }
        return true;
    }
    
    private boolean webContentCornorCasePass(Document document, String content) {
        if (pagesCrawledSet.contains(url)) {
            return false;
        }
        if (url.contains("www.linkedin.com")) {
            return false;
        }
        return true;
    }
    
    public void configure() {

        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(pagesAddedFileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(",");
            for (String s : array) {
                pagesToVisitQueue.offer(s);
                PagesAddedSet.add(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + pagesAddedFileName + " " + e.getMessage());
        }
        
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(pagesCrawledFileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(",");
            for (String s : array) {
                pagesCrawledSet.add(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + pagesCrawledFileName + " " + e.getMessage());
        }
        
    }
    
    private void write(String s, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true); // true indicates append mode
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(s + ",");
//            bw.newLine(); // add a new line after the text
            bw.close(); // close the BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<String> readFileToList(String fileName, String spliter) {
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


