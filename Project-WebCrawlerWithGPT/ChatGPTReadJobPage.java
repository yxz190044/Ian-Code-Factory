
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChatGPTReadJobPage {
    
    String content;
    String queries;
    String apiKey = "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";
    String model = "gpt-3.5-turbo-0301";
    String role = "user";
    
    String answers;
    boolean[] processedAnswers;
    String[] questions;
    
    public ChatGPTReadJobPage(String content, String queries) {
        this.content = content;
        this.queries = queries;
        questions = queries.split(";");
        processedAnswers = new boolean[questions.length];
        chatgpt();
    }
    
    public void chatgpt() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(queries);
            sb.append("Please only answer the previous questions with Yes or No, or only some keywords without anything else, separate each answer with ';' and number the answers like 1. 2. 3. etc., based on the content as following: ");
            sb.append(content);
            String chatgptContent = sb.toString();
            System.out.println("query string length is" + chatgptContent.length());
//            chatgptContent = "1 + 1 = ?";
            HttpResponse<String> response = Unirest.post("https://api.openai.com/v1/chat/completions")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .body("{\"model\":\"" + model + "\",\"messages\":[{\"role\":\"" + role + "\",\"content\":\"" + chatgptContent + "\"}]" + "}")
            .asString();
            String res = response.getBody();
            
            answers = res;
           
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}














































