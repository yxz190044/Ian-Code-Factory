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

public class WebCrawlerForJobLinks {
    
    List<String> urls = readFileToList("careerLinks/sdeJobLinks/possibleJobLinks.txt", "\n");
    ConcurrentHashSet processedUrls = new ConcurrentHashSet(readFileToList("careerLinks/sdeJobLinks/processedJobLinks.txt", "\n"));
    ConcurrentHashSet gptProcessedUrls = new ConcurrentHashSet(readFileToList("careerLinks/sdeJobLinks/gptProcessedJobLinks.txt", "\n"));
    
    ConcurrentHashSet careerUrls = new ConcurrentHashSet(readFileToList("careerLinks/handCopiedCareerLinks.txt", "\n"));
    ConcurrentHashSet keywords = new ConcurrentHashSet(Arrays.asList("Software Development Engineer", "SDE", "Software Engineer", "Software Developer", "Backend Engineer", "Full Stack Developer", "Java", "C++", "Python", "Node.js", "AWS", "SQL", "Agile", "Scrum", "Git", "Jenkins", "Continuous Integration", "Continuous Deployment", "React", "Angular", "Vue.js", "Ruby", "Perl", "PHP", "HTML", "CSS", "JavaScript", "TypeScript", "Swift", "Objective-C", "C#", ".NET", "ASP.NET", "REST", "SOAP", "JSON", "XML", "Docker", "Kubernetes", "CI/CD", "Maven", "Gradle", "JUnit", "TestNG", "Selenium", "JMeter", "Apache", "Nginx", "Tomcat", "Spring", "Hibernate", "JPA", "Struts", "JSF", "Node.js", "Express", "Ruby on Rails", "Django", "Flask", "Laravel", "Symfony", "Oracle", "MySQL", "Java", "Python", "Linux", "Bash", "Fortran", "Matlab", "Go", "JavaScript", "HTML", "CSS", "MySQL", "AWS", "Git", "Github", "JUnit", "GPT-3", "OpenAI", "Guice", "Apache HTTP", "Jsoup", "JSON", "React", "Spring", "JavaFX", "Numerical Simulation", "Software Programming", "Cloud Computing", "Machine Learning", "RESTful APIs", "Authentication", "Authorization", "MySQL", "Docker", "Chatbot", "WebCrawler", "Unirest", "JSON Parsing", "Computer Science", "Data post-processing", "OpenAI API client program", "HTTP Requests", "JSON Responses", "Database Schema", "Data Persistence", "Data Access", "CRUD operations", "Deployment", "Agile methodologies", "Version control", "Performance optimization", "Security monitoring", "Java", "Python", "Linux", "Bash", "Fortran", "Matlab", "Go", "JavaScript", "HTML", "CSS", "MySQL", "AWS", "Google Cloud", "Git", "Github", "JUnit", "GPT-3", "OpenAI API", "Guice", "Apache HTTP", "Jsoup", "JSON", "React", "Spring", "JavaFX", "Numerical Simulation", "Software Programming", "Cloud Computing", "Machine Learning", "RESTful APIs", "authentication", "authorization", "AI-powered techniques", "product evaluation", "content abuse detection", "personalized responses", "automated content censorship", "custom visuals", "graphics", "chatbots", "virtual assistants", "MySQL database", "Spring Data JPA", "JpaRepository", "CRUD operations", "WebCrawler", "HTTP requests", "JSON parsing", "Android", "MVVM", "MVC"));
    int keywordCountThreshold = 5;
    
    private String queries = "1. (answer Yes or No)Is this a job post page? 2. (answer Yes or No)Is the job in US? 3. (answer Yes or No)Is the job SDE kind? 4. What is the job title ? 5. (answer Yes or No)Is the job entry level? 6. (answer with a number of 0 to 10)How many years of SDE experience is required? 7. List at most 10 mostly important or mostly mentioned tech frame works (separate with ',') required in this page.";
    int querySize = 7;
    
    public static void main(String[] args) {
        WebCrawlerForJobLinks crawler = new WebCrawlerForJobLinks();
        crawler.initiate();
    }
    
    public void initiate() {
        List<JobLink> LJL = new ArrayList<>();
        for (String url : urls) {
            if (url.length() == 0) {
                continue;
            }
            if (url.charAt(url.length() - 1) == '\\') {
                url = url.substring(0, url.length() - 1);
            }
            if (processedUrls.contains(url)) {
                continue;
            }
            if (gptProcessedUrls.contains(url)) {
                continue;
            }
            if (careerUrls.contains(url)) {
                continue;
            }
            JobLink jobLink = new JobLink(url);
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
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                continue;
            }
            
            // judge the webcontent with some algorithms first
            jobLink.matchedTechFrames = sdeJobPostingMatching(content);
            jobLink.matchedTechFramesCount = jobLink.matchedTechFrames.size();
            LJL.add(jobLink);
            System.out.println(jobLink.matchedTechFramesCount + " " + jobLink.url);
            if (jobLink.matchedTechFramesCount <= keywordCountThreshold) {
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                processedUrls.add(url);
                write(jobLink.asString(), "careerLinks/sdeJobLinks/processedJobLinksLog.txt");
                continue;
            }
            
            if (content.length() > 14000) {
                System.out.println("Too long content of length: " + content.length());
                write(url + "\n", "careerLinks/sdeJobLinks/tooLongJobLinks.txt");
                processedUrls.add(url);
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                continue;
            }
            
            String processContent = processContent(content);
//                        System.out.println(processContent);
            if (processContent.contains("Pigment")) {
                write(url + "\n", "careerLinks/sdeJobLinks/processedJobLinks.txt");
                continue;
            }
            ChatGPTReadJobPage rjp = new ChatGPTReadJobPage(processContent, queries);
            System.out.println(rjp.answers);
            if (rjp.answers == null) {
                continue;
            }
            if (rjp.answers.contains("currently overloaded")) {
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
    
}


