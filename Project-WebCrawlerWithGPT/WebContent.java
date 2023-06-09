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

public class WebContent {
    
    public String url;
    public List<String> nexts;
    public Document document;
    
    public WebContent(String url) {
        this.url = url;
        nexts = new ArrayList<>();
        crawl();
    }
    
    public void crawl() {
        try {
            document = Jsoup.connect(url).get();
            //System.out.println(document.toString());
            String html = document.html();  // get the HTML content as a string
            
//            Document doc = Jsoup.parse(html);
//            document = doc;
            
            Elements linksOnPage = document.select("a[href]");
            //System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage) {
                nexts.add(link.absUrl("href"));
            }
        } catch(IOException ioe) {
            // We were not successful in our HTTP request
            System.out.println("WebContent: Error in out HTTP request " + ioe);
        } catch (IllegalArgumentException e) {
            // Exception handling code goes here
            System.out.println("WebContent: An error occurred: " + e.getMessage());
        }
    }
    
}


