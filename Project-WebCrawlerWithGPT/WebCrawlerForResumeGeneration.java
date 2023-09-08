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

public class WebCrawlerForResumeGeneration {
    
    List<String> jobLinksToApply = readFileToList("careerLinks/sdeJobLinks/gptProcessedJobLinks.txt", "\n");
    ConcurrentHashSet appliedJobLinks = new ConcurrentHashSet(readFileToList("careerLinks/sdeJobLinks/appliedJobLinks.txt", "\n"));
    
    
    // add your projects in the method myProjects();
    List<String> myProjects = myProjects();
    String myProjectsCombinations = myProjectsCombinations();
    String queries = " Question to answer: please select 4 best projects to put on the resume ranked by importance, in the format such as 2. 1. 4. 6., being used to apply for the position described in the following page: ";
    
    int projectSize = 4;
    
    public static void main(String[] args) {
        WebCrawlerForResumeGeneration crawler = new WebCrawlerForResumeGeneration();
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
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                continue;
            }
            String content = document.text();
            if (content.length() == 0) {
                System.out.println("Content length 0 encountered for " + url);
                continue;
            }
            
            String processContent = processContent(content);
            ChatGPTGenerateResume gr = new ChatGPTGenerateResume(myProjects, queries, processContent);
            
            System.out.println(gr.answers);
            if (gr.answers == null) {
                continue;
            }
            if (gr.answers.contains("currently overloaded")) {
                continue;
            }
            
            
            
            
            String[] processAnswers = processAnswers(rjp.answers);
            if (processAnswers == null) {
                continue;
            }
            write(url + "\n", "careerLinks/sdeJobLinks/gptProcessedJobLinks.txt");
            gptProcessedUrls.add(url);
            if (processAnswers[0].equals("No")) {
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                write(jobLink.asString(), "careerLinks/sdeJobLinks/gptProcessedJobLinksLog.txt");
                continue;
            }
            jobLink.isJobPost = true;
            if (processAnswers[1].equals("No")) {
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                write(jobLink.asString(), "careerLinks/sdeJobLinks/gptProcessedJobLinksLog.txt");
                continue;
            }
            jobLink.inUS = true;
            if (processAnswers[2].equals("No")) {
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                write(jobLink.asString(), "careerLinks/sdeJobLinks/gptProcessedJobLinksLog.txt");
                continue;
            }
            jobLink.isSDE = true;
            jobLink.jobTitle = processAnswers[3];
            if (processAnswers[4].equals("Yes")) {
                jobLink.isEntryLevel = true;
            }
            int num = 0;
            for (char ch : processAnswers[5].toCharArray()) {
                if (ch <= '9' && ch >= '0') {
                    num *= 10;
                    num += ch - '0';
                } else {
                    break;
                }
            }
            jobLink.years = num;
            jobLink.techFrames = processAnswers[6];
            write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
            processedUrls.add(url);
            write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
            write(jobLink.asString(), "careerLinks/sdeJobLinks/gptProcessedJobLinksLog.txt");
            if (num == 0 && !jobLink.jobTitle.contains("Senior") && !jobLink.jobTitle.contains("Staff")) {
                write(jobLink.jobTitle + "\n" + url + "\n", "careerLinks/sdeJobLinks/newgrad.txt");
            } else {
                write(jobLink.jobTitle + "     experiece of at least" + num + " years" + "\n" + url + "\n", "careerLinks/sdeJobLinks/experienced.txt");
            }
        }
        
        Collections.sort(LJL, new Comparator<> () {
            @Override
            public int compare(JobLink j1, JobLink j2) {
                if (j1.matchedTechFramesCount == j2.matchedTechFramesCount) {
                    return 0;
                }
                return j1.matchedTechFramesCount > j2.matchedTechFramesCount ? -1 : 1;
            }
        });
        for (JobLink jobLink : LJL) {
            write(jobLink.matchedTechFramesCount + " " + jobLink.url, "careerLinks/sdeJobLinks/processedJobLinksLogTest.txt");
        }
    }
    
    public List<String> sdeJobPostingMatching(String webpageContent) {
        // Count the occurrences of keywords that indicate it's an SDE job posting page
        if (!webpageContent.contains("Apply")) {
            return new ArrayList<>();
        }
        int keywordCount = 0;
        List<String> matched = new ArrayList<>();
        for (String keyword : keywords) {
            if (webpageContent.contains(keyword)) {
                matched.add(keyword);
                keywordCount++;
            }
        }
        return matched;
    }
    
    private String[] processAnswers(String answers) {
        
        String[] res = new String[querySize];
        String[] array = answers.split("\"");
        String answers2 = "";
        for (String s : array) {
            if (s.length() <= 3) {
                continue;
            }
            if (s.substring(0, 3).equals("1. ")) {
                answers2 = s;
                break;
            }
        }
//        System.out.println(answers2);
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answers2.length(); i++) {
            char ch = answers2.charAt(i);
            if (index >= res.length) {
                return null;
            }
            if (ch != '\\' && ch != ';') {
                sb.append(ch);
            } else {
                res[index++] = sb.toString();
                i++;
                sb = new StringBuilder();
            }
        }
        if (index < res.length) {
            res[index++] = sb.toString();
        }
        for (index = 0; index < res.length; index++) {
            if (res[index] == null) {
                return null;
            }
            if (res[index].length() > 3) {
                res[index] = res[index].substring(3, res[index].length());
            }
        }
        
        System.out.print("Processed Answers are: ");
        for (String s : res) {
            System.out.print(s + " ");
        }
        System.out.println();
        return res;
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
    
    private List<String> myProjects() {
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
    
    
    
    private String myProjectsCombinations() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are my SDE projects I have done labelled with 1. 2. 3....: ");
        for (int i = 0; i < myProjects.size(); i++) {
            sb.append((i + 1) + ". ");
            sb.append(myProjects.get(i));
            sb.append(";");
        }
        return processContent(sb.toString());
    }
    
}


