import java.io.File;
import java.io.IOException;

public class FileStructureGenerator {
    public static void main(String[] args) throws IOException {
        // Create directories
        File socialMediaDir = new File("SocialMedia");
        socialMediaDir.mkdir();
        File srcDir = new File(socialMediaDir, "src/main/java/com/socialmedia");
        srcDir.mkdirs();
        File resourcesDir = new File(socialMediaDir, "src/main/resources/templates");
        resourcesDir.mkdirs();
        File testDir = new File(socialMediaDir, "src/test/java/com/socialmedia");
        testDir.mkdirs();
        new File(srcDir, "config").mkdir();
        new File(srcDir, "controller").mkdir();
        new File(srcDir, "dao").mkdir();
        new File(srcDir, "model").mkdir();
        new File(srcDir, "security").mkdir();
        new File(srcDir, "service").mkdir();
        new File(srcDir, "util").mkdir();
        new File(srcDir, "websocket").mkdir();
        new File(testDir, "controller").mkdir();
        new File(testDir, "service").mkdir();
        new File(resourcesDir, "static").mkdir();
        new File(resourcesDir, "templates").mkdir();
        
        // Create files
        new File(socialMediaDir, "pom.xml").createNewFile();
        new File(srcDir, "config/DatabaseConfig.java").createNewFile();
        new File(srcDir, "config/SecurityConfig.java").createNewFile();
        new File(srcDir, "config/WebConfig.java").createNewFile();
        new File(srcDir, "controller/AdController.java").createNewFile();
        new File(srcDir, "controller/CommentController.java").createNewFile();
        new File(srcDir, "controller/EventController.java").createNewFile();
        new File(srcDir, "controller/GroupController.java").createNewFile();
        new File(srcDir, "controller/HashtagController.java").createNewFile();
        new File(srcDir, "controller/LikeController.java").createNewFile();
        new File(srcDir, "controller/PostController.java").createNewFile();
        new File(srcDir, "controller/UserController.java").createNewFile();
        new File(srcDir, "dao/AdDao.java").createNewFile();
        new File(srcDir, "dao/CommentDao.java").createNewFile();
        new File(srcDir, "dao/EventDao.java").createNewFile();
        new File(srcDir, "dao/GroupDao.java").createNewFile();
        new File(srcDir, "dao/HashtagDao.java").createNewFile();
        new File(srcDir, "dao/LikeDao.java").createNewFile();
        new File(srcDir, "dao/PostDao.java").createNewFile();
        new File(srcDir, "dao/UserDao.java").createNewFile();
        new File(srcDir, "model/Ad.java").createNewFile();
        new File(srcDir, "model/Comment.java").createNewFile();
        new File(srcDir, "model/Event.java").createNewFile();
        new File(srcDir, "model/Group.java").createNewFile();
        new File(srcDir, "model/Hashtag.java").createNewFile();
        new File(srcDir, "model/Like.java").createNewFile();
        new File(srcDir, "model/Post.java").createNewFile();
        new File(srcDir, "model/User.java").createNewFile();
        new File(srcDir, "security/CustomUserDetailsService.java").createNewFile();
        new File(srcDir, "security/JwtTokenProvider.java").createNewFile();
        new File(srcDir, "service/AdService.java").createNewFile();
        new File(srcDir, "service/CommentService.java").createNewFile();
        new File(srcDir, "service/EventService.java").createNewFile();
        new File(srcDir, "service/GroupService.java").createNewFile();
        new File(srcDir, "service/HashtagService.java").createNewFile();
        new File(srcDir, "service/LikeService.java").createNewFile();
        new File(srcDir, "service/PostService.java").createNewFile();
        new File(srcDir, "service/UserService.java").createNewFile();
        new File(srcDir, "util/AnalyticsUtil.java").createNewFile();
        new File(srcDir, "websocket/LiveStreamSocket.java").createNewFile();
        new File(resourcesDir, "ad.html").createNewFile();
        new File(resourcesDir, "event.html").createNewFile();
        new File(resourcesDir, "group.html").createNewFile();
        new File(resourcesDir, "index.html").createNewFile();
        new File(resourcesDir, "login.html").createNewFile();
        new File(resourcesDir, "post.html").createNewFile();
        new File(resourcesDir, "profile.html").createNewFile();
        new File(resourcesDir, "register.html").createNewFile();
        
        new File(testDir, "controller/AdControllerTest.java").createNewFile();
        new File(testDir, "controller/CommentControllerTest.java").createNewFile();
        new File(testDir, "controller/EventControllerTest.java").createNewFile();
        new File(testDir, "controller/GroupControllerTest.java").createNewFile();
        new File(testDir, "controller/HashtagControllerTest.java").createNewFile();
        new File(testDir, "controller/LikeControllerTest.java").createNewFile();
        new File(testDir, "controller/PostControllerTest.java").createNewFile();
        new File(testDir, "controller/UserControllerTest.java").createNewFile();
        new File(testDir, "service/AdServiceTest.java").createNewFile();
        new File(testDir, "service/CommentServiceTest.java").createNewFile();
        new File(testDir, "service/EventServiceTest.java").createNewFile();
        new File(testDir, "service/GroupServiceTest.java").createNewFile();
        new File(testDir, "service/HashtagServiceTest.java").createNewFile();
        new File(testDir, "service/LikeServiceTest.java").createNewFile();
        new File(testDir, "service/PostServiceTest.java").createNewFile();
        new File(testDir, "service/UserServiceTest.java").createNewFile();
        new File(resourcesDir, "static/app.js").createNewFile();
        new File(resourcesDir, "static/style.css").createNewFile();
        new File(resourcesDir, "templates/ad.html").createNewFile();
        new File(resourcesDir, "templates/event.html").createNewFile();
        new File(resourcesDir, "templates/group.html").createNewFile();
        new File(resourcesDir, "templates/index.html").createNewFile();
        new File(resourcesDir, "templates/login.html").createNewFile();
        new File(resourcesDir, "templates/post.html").createNewFile();
        new File(resourcesDir, "templates/profile.html").createNewFile();
        new File(resourcesDir, "templates/register.html").createNewFile();
        // Print success message
        System.out.println("File structure generated successfully!");
    }
}
