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


public class WebCrawlerForJobLinksTest {
    
    static String queries = "1. is this page a job posting page? 2. Does this job locates in US? 3. Is this job a software engineer job? 4. Is this job a data engineer job? 5. Should new grad apply to this position? 6. Should applicant with 0-year experience apply to this position? 7. Should applicant with under 2-year experience applying to this position? 8. Should applicant with under 4-year experience applying to this position? 9. What are the demanded tech frames, ranked by importance?";
    
    
    
    public static void main(String[] args) {
        
        String url = args[0];
        
        WebContent wc = new WebContent(url);
//        String content = wc.document.toString();
        System.out.println(wc.document.toString());
        String content = wc.document.text();
        System.out.println(content);
        
        String[] array = content.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        
        ChatGPTReadJobPage rjp = new ChatGPTReadJobPage(sb.toString(), queries);
        System.out.println(rjp.answers);
    }
}


