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

public class WebCrawlerForResumeGenerationTest {
    
    // add your projects in the method myProjects();
    List<String> jobLinksToApply = new ArrayList<>();
    ConcurrentHashSet appliedJobLinks = new ConcurrentHashSet();
    
    List<String> myProjects = myProjects();
    String myProjectsCombinations = myProjectsCombinations();
    String queries = " Question to answer: please select 4 best projects to put on the resume ranked by importance, make the output only using my label number (only number labels, answer within 8 bytes) of my projects such as '10 4 5 6', '2 5 1 3', '3 2 1 4', etc., being used to apply for the position described in the following page: ";
    int projectSize = 4;
    
    
    
    public static void main(String[] args) {
        WebCrawlerForResumeGenerationTest crawler = new WebCrawlerForResumeGenerationTest();
        crawler.jobLinksToApply.add(args[0]);
        System.out.println("------------------------------------------------------------");
        System.out.println("Added url:");
        System.out.println(args[0]);
        crawler.initiate();
    }
    
    public void initiate() {
        for (String url : jobLinksToApply) {
            if (url.length() == 0) {
                continue;
            }
            if (url.charAt(url.length() - 1) == '\\') {
                url = url.substring(0, url.length() - 1);
            }
            if (appliedJobLinks.contains(url)) {
                continue;
            }
            
            WebContent wc = new WebContent(url);
            Document document = wc.document;
            if (document == null) {
                System.out.println("Null content encountered for " + url);
                continue;
            }
            String content = document.text();
            if (content.length() == 0) {
                System.out.println("Content length 0 encountered for " + url);
                continue;
            }
            
            String processContent = processContent(content);
            
            System.out.println("------------------------------------------------------------");
            System.out.println("This is projects combined: ");
            System.out.println(myProjectsCombinations);
            System.out.println("------------------------------------------------------------");
            System.out.println("This is processed webcontent: ");
            System.out.println(processContent);
            System.out.println("------------------------------------------------------------");
            System.out.println("Sending queries to GPT: ");
            System.out.println(queries);
            
            ChatGPTGenerateResume gr = new ChatGPTGenerateResume(myProjectsCombinations, queries, processContent);
            
            System.out.println(gr.answers);
            if (gr.answers == null) {
                continue;
            }
            if (gr.answers.contains("currently overloaded")) {
                continue;
            }
        }
    }
    
    
    private String processContent(String content) {
        
        content = content.replaceAll("[\\p{So}]", "");
        char[] array = new char[] {(char) 0,(char) 1, (char) 2, (char) 3};
        for (char ch : array) {
            content = processContent(content, "" + ch);
        }
        content = processContent(content, "[$&%\n#\"']");
        content = processContent(content, "[/{\\}]");
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
    
    private static List<String> myProjectsLatexVersion() {
        List<String> L = new ArrayList<>();
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Second-Hand Market Web Application} \\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Designed and implemented a second-hand market app with RESTful APIs that allows users to buy and sell used items online, with features including item listing, searching, messaging, payment, etc.\n" +
              "            \\item[--] Implemented user registration and login, authentication and authorization via Shiro-Guice for safety and performance.\n" +
              "            \\item[--] Implemented AI-powered techniques utilizing OpenAI APIs, specifically Ada and GPT-3.5-Turbo, for product evaluation, personalized product matching and recommendation, and content abuse detection.\n" +
              "            \\item[--] Connected the application to a MySQL database on GCP and deployed it on GCP App Engine.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{OpenAI-boosted Social Media Platform}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Developed a social media platform with features including privacy settings, search functionality, analytics and insights, events and groups, advertising, etc, with AI-driven tools for improving the functionality and safety.\n" +
              "            \\item[--] Leveraged trained GPT-3 model to generate personalized responses to user queries and automated content censorship.\n" +
              "            \\item[--] Deployed DALL-E to generate custom visuals and graphics for social media posts, while applied Codex to improve the accuracy and efficiency of chatbots and virtual assistants.\n" +
              "            \\item[--] Utilized React for the frontend, and Spring, Hibernate, and Spring Security for the backend to handle data.\n" +
              "            \\item[--] Stored user profiles, posts, comments, and multimedia content in a MySQL database hosted on AWS, with data access managed by Spring Data JPA, JpaRepository and CRUD operations.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{GPT-analyzed Web-crawler}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Created a Web Crawler tool with the Jsoup library to capture targeted data from an assortment of web resources.\n" +
              "            \\item[--] Built a Client Application for OpenAI API which utilizes Apache HTTP, and Unirest libraries for streamlining communication with OpenAI's GPT-3.5-turbo model, including sending HTTP requests and parsing JSON responses.\n" +
              "            \\item[--] Devised an effective Web Data Filtration system that eliminates 90\\% of irrelevant data, significantly reducing the frequency of API requests to OpenAI, resulting in considerable savings in time and resources.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Automated Stock Trading System}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Developed an automated stock trading system using historical market data and technical indicators.\n" +
              "            \\item[--] Integrated machine learning algorithms, such as Random Forest and LSTM, for predicting stock price movements.\n" +
              "            \\item[--] Implemented risk management strategies and executed trades using broker APIs.\n" +
              "            \\item[--] Built a user-friendly dashboard to monitor portfolio performance and trading activity.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Language Learning App}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Created a mobile app to facilitate language learning through interactive lessons, quizzes, and flashcards.\n" +
              "            \\item[--] Incorporated speech recognition and pronunciation evaluation using Google Cloud Speech-to-Text API.\n" +
              "            \\item[--] Implemented gamification elements, such as badges and leaderboards, to motivate users.\n" +
              "            \\item[--] Designed an intuitive user interface with React Native for seamless cross-platform performance.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Smart Home Automation System}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Developed a centralized smart home automation system that controls lighting, appliances, security, and climate.\n" +
              "            \\item[--] Utilized IoT devices, such as smart plugs and sensors, to monitor and control various aspects of the home.\n" +
              "            \\item[--] Integrated voice control using Amazon Alexa and Google Assistant APIs.\n" +
              "            \\item[--] Created a mobile app and web dashboard for remote access and real-time monitoring.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Health and Fitness Tracker}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Built a health and fitness tracking application that records user activities, nutrition, and biometrics.\n" +
              "            \\item[--] Integrated wearable devices and fitness trackers to automatically sync data with the app.\n" +
              "            \\item[--] Implemented data visualization and analysis features to help users track their progress over time.\n" +
              "            \\item[--] Provided personalized workout and meal recommendations based on user goals and preferences.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{Virtual Reality Museum Tour}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Developed a virtual reality experience that allows users to explore museums and exhibits remotely.\n" +
              "            \\item[--] Created 3D models of artworks and historical artifacts for an immersive museum environment.\n" +
              "            \\item[--] Implemented interactive elements, such as audio guides and information pop-ups, to enhance the learning experience.\n" +
              "            \\item[--] Supported various VR platforms, including Oculus Rift, HTC Vive, and mobile VR devices.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{E-Commerce Analytics Dashboard}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Designed and developed an analytics dashboard for an e-commerce platform to monitor sales, inventory, and customer behavior.\n" +
              "            \\item[--] Integrated with the e-commerce platform's database to fetch real-time data for analysis.\n" +
              "            \\item[--] Created data visualizations, such as charts and graphs, to provide insights into key performance indicators.\n" +
              "            \\item[--] Implemented user authentication and role-based access control to ensure data security.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        L.add("\\begin{tabularx}{\\linewidth}{ @{}l r@{} }\n" +
              "\\textbf{AI-Powered Language Translation}\\\\[3.5pt]\n" +
              "\\multicolumn{2}{@{}X@{}}{\n" +
              "    \\begin{minipage}[t]{\\linewidth}\n" +
              "        \\begin{itemize}[nosep,after=\\strut, leftmargin=1em, itemsep=3pt]\n" +
              "            \\item[--] Created a language translation application using machine learning models for accurate and context-aware translations.\n" +
              "            \\item[--] Utilized transformer-based architectures, such as BERT and T5, to achieve high-quality translations.\n" +
              "            \\item[--] Integrated automatic language detection and supported a wide range of languages.\n" +
              "            \\item[--] Designed a user-friendly interface for text input and displaying translated results.\n" +
              "        \\end{itemize}\n" +
              "    \\end{minipage}\n" +
              "}" +
              "\\end{tabularx}");
        
        return L;
    }
    
    private static List<String> myProjects() {
        List<String> projects = new ArrayList<>();
        projects.add("Second-Hand Market Web Application:\n" +
                     "- Designed and implemented a second-hand market app with RESTful APIs for buying and selling used items.\n" +
                     "- Implemented user registration, login, authentication, and authorization for safety.\n" +
                     "- Integrated AI-powered techniques for product evaluation, personalized product matching, and abuse detection.\n" +
                     "- Connected to a MySQL database on GCP and deployed on GCP App Engine.");
        
        projects.add("OpenAI-boosted Social Media Platform:\n" +
                     "- Developed a social media platform with privacy settings, search, analytics, events, etc.\n" +
                     "- Utilized GPT-3 model for personalized responses, content censorship, and Codex for chatbots.\n" +
                     "- Used React for frontend, Spring and Hibernate for backend, stored data on AWS.");
        
        projects.add("GPT-analyzed Web-crawler:\n" +
                     "- Created a Web Crawler with Jsoup to capture data from web resources.\n" +
                     "- Built Client App for OpenAI API using HTTP libraries, optimized data filtration.\n" +
                     "- Improved efficiency and communication with OpenAI's GPT-3.5-turbo model.");
        
        projects.add("Automated Stock Trading System:\n" +
                     "- Developed an automated stock trading system using historical market data and technical indicators.\n" +
                     "- Integrated machine learning algorithms, such as Random Forest and LSTM, for predicting stock price movements.\n" +
                     "- Implemented risk management strategies and executed trades using broker APIs.\n" +
                     "- Built a user-friendly dashboard to monitor portfolio performance and trading activity.");
        
        projects.add("Language Learning App:\n" +
                     "- Created a mobile app for language learning with interactive lessons, quizzes, and speech recognition.\n" +
                     "- Incorporated speech recognition and pronunciation evaluation using Google Cloud Speech-to-Text API.\n" +
                     "- Implemented gamification elements for motivation, designed intuitive user interface with React Native.");
        
        projects.add("Smart Home Automation System:\n" +
                     "- Developed a centralized smart home automation system for controlling lighting, appliances, security, and climate.\n" +
                     "- Utilized IoT devices like smart plugs and sensors, integrated voice control using Amazon Alexa and Google Assistant APIs.\n" +
                     "- Created mobile app and web dashboard for remote access and real-time monitoring.");
        
        projects.add("Health and Fitness Tracker:\n" +
                     "- Built a health and fitness tracking application for recording user activities, nutrition, and biometrics.\n" +
                     "- Integrated wearable devices and fitness trackers for automatic data sync.\n" +
                     "- Implemented data visualization and analysis features to track progress, provided personalized recommendations.");
        
        projects.add("Virtual Reality Museum Tour:\n" +
                     "- Developed a virtual reality experience for exploring museums and exhibits remotely.\n" +
                     "- Created 3D models of artworks and artifacts for immersive museum environment.\n" +
                     "- Implemented interactive elements like audio guides and information pop-ups.\n" +
                     "- Supported VR platforms like Oculus Rift, HTC Vive, and mobile VR devices.");
        
        projects.add("E-Commerce Analytics Dashboard:\n" +
                     "- Designed and developed an analytics dashboard for e-commerce platform to monitor sales, inventory, and customer behavior.\n" +
                     "- Integrated with platform's database for real-time data analysis, created data visualizations for insights.\n" +
                     "- Implemented user authentication and role-based access control for data security.");
        
        projects.add("AI-Powered Language Translation:\n" +
                     "- Created a language translation application using machine learning models for accurate translations.\n" +
                     "- Utilized transformer-based architectures like BERT and T5 for high-quality translations.\n" +
                     "- Integrated automatic language detection, designed user-friendly interface for text input and display of results.");
        
        return projects;
    }

    
    private String myProjectsCombinations() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are my SDE projects I have done labelled with 1. 2. 3....: ");
        for (int i = 0; i < myProjects.size(); i++) {
            sb.append("Project " + (i + 1) + ". ");
            sb.append(myProjects.get(i));
            sb.append(";");
        }
        return processContent(sb.toString());
    }
    
}


