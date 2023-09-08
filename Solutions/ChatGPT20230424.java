import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.*;

public class ChatGPT20230424 {
    public static void main(String[] args) {
        String apiKey = "";
        String model = "gpt-3.5-turbo";
        String role = "user";
        List<String> contents = new ArrayList<>();
        contents.add("Let's make conversation together");
        contents.add("expand step 1");
        contents.add("expand step 2");
        contents.add("expand step 3");
        double temperature = 0.7;
        
        
        try {
            for (String content : contents) {
                HttpResponse<String> response = Unirest.post("https://api.openai.com/v1/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body("{\"model\":\"" + model + "\",\"messages\":[{\"role\":\"" + role + "\",\"content\":\"" + content + "\"}]" + "}")
                .asString();
                System.out.println(response.getBody());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
