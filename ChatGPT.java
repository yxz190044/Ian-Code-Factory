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

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChatGPT {
    public static void main(String[] args) throws IOException {
        // Set up the OpenAI API endpoint and your API key
        String endpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
        String apiKey = "";
        
        // Set up the HTTP client and the HTTP request
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        httpPost.setHeader("Content-Type", "application/json");
        
        // Set up the input text and the API parameters
        JSONObject input = new JSONObject();
        input.put("prompt", "Hello, ChatGPT!");
        input.put("max_tokens", 1024);
        input.put("temperature", 0.5);
        input.put("stop", "");
        
        // Convert the input to a JSON string and set it as the HTTP request entity
        StringEntity entity = new StringEntity(input.toString());
        httpPost.setEntity(entity);
        
        // Send the HTTP request and get the response
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity);
        
        // Parse the JSON response and extract the output text
        JSONObject output = new JSONObject(responseString);
        String outputText = output.getJSONArray("choices").getJSONObject(0).getString("text");
        
        // Print the output text
        System.out.println(outputText);
    }
}

