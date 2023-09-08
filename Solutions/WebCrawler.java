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

public class WebCrawler {
    private static final int MAX_PAGES_TO_SEARCH = 100000000;
    private static final int MAX_PAGES_TO_ADD = 1000000000;
    private static final int EMAILS_TO_ADD = 1000000;
    private HashSet<String> pagesVisited = new HashSet<>();
    private HashSet<String> pagesAdded = new HashSet<>();
    private HashSet<String> emailsFound = new HashSet<>();
    private Queue<String> pagesToVisit = new ArrayDeque<>();
    private int num;
    
    /**
     * This implements the search algorithm.
     */
    public void search(String url) {
        
        if (pagesVisited.contains(url)) {
            return;
        }
        pagesVisited.add(url);
        List<String> nexts = crawl(url);
        
        for (String next : nexts) {
            if (pagesAdded.contains(next)) {
                continue;
            }
            pagesAdded.add(next);
            pagesToVisit.offer(next);
            //            System.out.println(num + " " + next);
            num++;
        }
        //        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }
    
    public void beginWith(String url) {
        
        String fileName = "crawlerEmails.txt"; // Replace with your file name
        
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(",");
            for (String s : array) {
                emailsFound.add(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        
        pagesToVisit.offer(url);
        pagesAdded.add(url);
        while (pagesVisited.size() < MAX_PAGES_TO_SEARCH && pagesAdded.size() < MAX_PAGES_TO_ADD && pagesToVisit.size() > 0 && emailsFound.size() < EMAILS_TO_ADD) {
            int numToThrowAway = numToThrowAway();
            while (numToThrowAway > 0) {
                pagesAdded.remove(pagesToVisit.poll());
                numToThrowAway--;
            }
            search(pagesToVisit.poll());
        }
        
        
        System.out.println("emailsFound " + emailsFound.size());
        System.out.println("pagesVisited " + pagesVisited.size());
        System.out.println("pagesToVisit " + pagesToVisit.size());
    }
    
    private int numToThrowAway() {
        return 0;
//        int size = pagesToVisit.size();
//        int num = size/10;
//        if (size > 100) {
//            num = 10;
//        }
//        return (int) (num * Math.random());
    }
    
    /**
     * Returns the next URL to visit (in the order that they were found). We also do
     * a check to make sure this method doesn't return a URL that has already been
     * visited.
     *
     * @return
     */
    /**
     * Connects to the URL of the page and gets all links.
     *
     * @param url
     * @return
     */
    public List<String> crawl(String url) {
        List<String> L = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            //            System.out.println(document.toString());
            Pattern pattern = Pattern.compile("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,4}\\b");  // email pattern
            String html = document.html();  // get the HTML content as a string
            Matcher matcher = pattern.matcher(html);  // match the email pattern in the HTML
            while (matcher.find()) {
                String email = matcher.group();  // get the matched email
                if (email.substring(email.length() - 4, email.length()).equals(".jpg")) {
                    continue;
                }
                if (email.substring(email.length() - 4, email.length()).equals(".png")) {
                    continue;
                }
                if (!emailsFound.contains(email)) {
                    System.out.println(email);  // print the email
                    write(email);
                }
                emailsFound.add(email);
            }
            Elements linksOnPage = document.select("a[href]");
            //            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage) {
                L.add(link.absUrl("href"));
            }
        } catch(IOException ioe) {
            // We were not successful in our HTTP request
            //            System.out.println("Error in out HTTP request " + ioe);
        } catch (IllegalArgumentException e) {
            // Exception handling code goes here
//            System.out.println("An error occurred: " + e.getMessage());
        }
        return L;
    }
    
    private void write(String s) {
        try {
            FileOutputStream fos = new FileOutputStream("crawlerEmails.txt", true); // true indicates append mode
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(s + ",");
//            bw.newLine(); // add a new line after the text
            bw.close(); // close the BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        //        crawler.beginWith("https://www.linkedin.com");
        crawler.beginWith("https://www.google.com/search?q=SDE+job+postings&ei=_XQaZPWQGe7ykPIPmeiYgA8&ved=0ahUKEwi1g_-cy-79AhVuOUQIHRk0BvAQ4dUDCA8&uact=5&oq=SDE+job+postings&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIGCAAQBxAeMgYIABAHEB4yBggAEAcQHjIGCAAQBxAeMgYIABAHEB4yBggAEAcQHjIGCAAQBxAeMgYIABAHEB4yBggAEAcQHjIGCAAQBxAeOgoIABBHENYEELADOg0IABBHENYEEMkDELADOggIABCSAxCwAzoHCAAQsAMQQzoNCAAQ5AIQ1gQQsAMYAToSCC4QxwEQ0QMQyAMQsAMQQxgCOg0IABCABBANELEDEIMBOgcIABCABBANSgQIQRgAUMAHWNQLYKkNaAFwAXgAgAGdAogBggSSAQUxLjEuMZgBAKABAcgBEMABAdoBBggBEAEYCdoBBggCEAEYCA&sclient=gws-wiz-serp");
    }
}


