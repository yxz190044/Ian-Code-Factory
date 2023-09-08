# WebCrawler_GPT_JobSeek

At this moment the webcrawler is composed of ChatGPT connecter (ChatGPTReadJobPage), HTTP connecter (WebContent), WebCrawler for Career Links (WebCrawlerForCareerLinks), WebCrawler for Job Links (WebCrawlerForJobLinks) and other data structure, utility and test files. 

To get started with the project, one should:

1. copy the career links into careerLinks/handCopiedCareerLinks.txt. This is already done when students receive the files.
2. run WebCrawlerForCareerLinks:
    cd WebCrawler_GPT_JobSeek; javac WebCrawlerForCareerLinks.java; java WebCrawlerForCareerLinks;
3. run WebCrawlerForJobLinks;
    cd WebCrawler_GPT_JobSeek; javac WebCrawlerForJobLinks.java; java WebCrawlerForJobLinks;

If in the future one find there's too many duplicated urls in output files, use DedupUrls.java for deduplication. Read the file for more details.

BFSWebCrawler and Resume generation functionality has not been implemented completely. Stay connected with us, we will send out resources and/or a meeting schedule for a follow up when there's update on the project. These files include WebCrawlerForResumeGeneration.java, WebCrawlerForResumeGenerationTest.java, ChatGPTGenerateResume.java.
