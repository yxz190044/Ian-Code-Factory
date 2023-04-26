import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawlerTest {
    private static final int MAX_PAGES_TO_SEARCH = 1;
    private static final int MAX_PAGES_TO_ADD = 2;
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
        pagesToVisit.offer(url);
        pagesAdded.add(url);
        while (pagesVisited.size() < MAX_PAGES_TO_SEARCH && pagesAdded.size() < MAX_PAGES_TO_ADD && pagesToVisit.size() > 0) {
            int numToThrowAway = numToThrowAway();
            while (numToThrowAway > 0) {
                pagesAdded.remove(pagesToVisit.poll());
                numToThrowAway--;
            }
            search(pagesToVisit.poll());
        }
        
        System.out.println(pagesVisited.size());
        System.out.println(pagesToVisit.size());
    }
    
    private int numToThrowAway() {
        int size = pagesToVisit.size();
        int num = size/10;
        if (size > 100) {
            num = 10;
        }
        return (int) (num * Math.random());
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
            System.out.println(document.toString());
            Pattern pattern = Pattern.compile("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,4}\\b");  // email pattern
            String html = document.html();  // get the HTML content as a string
            System.out.println(html);
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
                }
                emailsFound.add(email);
            }
            Elements linksOnPage = document.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage) {
                L.add(link.absUrl("href"));
            }
        } catch(IOException ioe) {
            // We were not successful in our HTTP request
            System.out.println("Error in out HTTP request " + ioe);
        } catch (IllegalArgumentException e) {
            // Exception handling code goes here
            System.out.println("An error occurred: " + e.getMessage());
        }
        return L;
    }
    
    public static void main(String[] args) {
        WebCrawlerTest crawler = new WebCrawlerTest();
        //        crawler.beginWith("https://www.linkedin.com");
        crawler.beginWith("https://www.google.com/search?client=safari&rls=en&q=software+Engineer+recruiters&spell=1&sa=X&ved=2ahUKEwji3orK7ND9AhWcnGoFHSnnBH8QkeECKAB6BAgcEAE");
    }
}











