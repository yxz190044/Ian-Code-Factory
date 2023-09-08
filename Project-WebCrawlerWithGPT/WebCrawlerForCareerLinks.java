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

public class WebCrawlerForCareerLinks {
    
    List<String> urls = readFileToList("careerLinks/handCopiedCareerLinks.txt", "\n");
    ConcurrentHashSet processedUrls = new ConcurrentHashSet(readFileToList("careerLinks/processedCareerLinks.txt", "\n"));
    private String queries = "1. Does this look like a career site page? 2. Does the page mention any software engineer developer jobs? 3. Does the software engineer developer jobs all mention where they locate? 4. Does any of the software engineer developer jobs locate in US? ";
    int querySize = 4;

    public static void main(String[] args) {
        WebCrawlerForCareerLinks crawler = new WebCrawlerForCareerLinks();
        crawler.initiate();
    }
    
    public void initiate() {
        
        for (String url : urls) {
            if (processedUrls.contains(url)) {
                continue;
            }
            if (url.contains("www.linkedin.com")) {
                write(url + "\n", "careerLinks/LinkedInCareerLinks.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            WebContent wc = new WebContent(url);
            Document document = wc.document;
            if (document == null) {
                write(url + "\n", "careerLinks/CareerLinksNotIncludeSDE.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            String content = document.text();
            if (content.length() == 0) {
                write(url + "\n", "careerLinks/CareerLinksNotIncludeSDE.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            if (content.length() > 10000) {
                System.out.println("Too long content of length: " + content.length());
                write(url + "\n", "careerLinks/careerPageTooLongLinks.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            List<String> nexts = wc.nexts;
            String processContent = processContent(content);
            System.out.println(processContent);
            
            ChatGPTReadJobPage rjp = new ChatGPTReadJobPage(processContent, queries);
            System.out.println(rjp.answers);
            if (rjp.answers.contains("currently overloaded")) {
                continue;
            }
            int[] processAnswers = processAnswers(rjp.answers);
            
            if (processAnswers[0] != 1) {
                write(url + "\n", "careerLinks/CareerLinksNotIncludeSDE.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            if (processAnswers[1] != 1) {
                    write(url + "\n", "careerLinks/CareerLinksNotIncludeSDE.txt");
                    write(url + "\n", "careerLinks/processedCareerLinks.txt");
                    continue;
            }
            if (processAnswers[2] == 1 && processAnswers[3] == 0) {
                write(url + "\n", "careerLinks/CareerLinksNotIncludeSDE.txt");
                write(url + "\n", "careerLinks/processedCareerLinks.txt");
                continue;
            }
            write(url + "\n", "careerLinks/CareerLinksIncludeSDE.txt");
            write(url + "\n", "careerLinks/processedCareerLinks.txt");
            for (String next : nexts) {
                write(next + "\n", "careerLinks/sdeJobLinks/possibleJobLinks.txt");
            }
        }
    }
    
    private int[] processAnswers(String answers) {
        int[] res = new int[querySize];
        int num = 1;
        for (int i = 0; i < answers.length() - 10; i++) {
            if (answers.charAt(i) == (char) (num + '0') &&
                answers.charAt(i + 1) == '.' &&
                answers.charAt(i + 2) == ' ') {
                if (answers.substring(i + 3, i + 6).equals("Yes")) {
                    res[num - 1] = 1;
                } else if (answers.substring(i + 3, i + 5).equals("No")) {
                    res[num - 1] = 0;
                } else {
                    res[num - 1] = 2;
                }
                num++;
            }
        }
        
        System.out.print("Processed Answers are: ");
        for (int i : res) {
            if (i == 0) {
                System.out.print("No ");
            } else if (i == 1) {
                System.out.print("Yes ");
            } else {
                System.out.print("N/A ");
            }
        }
        System.out.println();
        return res;
    }
    
    private String processContent(String content) {
        
        char[] array = new char[] {(char) 0,(char) 1, (char) 2, (char) 3, '$', '&', '%', '\n', '#', '"'};
        
        for (char ch : array) {
            
            content = processContent(content, "" + ch);
        }
        return content;
    }
    
    private String processContent(String content, String ch) {
        String[] array = content.split(ch);
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }
    
    private void write(String s, String fileName) {
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


