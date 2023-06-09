import java.util.*;

public class JobLink {
    
    String url;
    boolean isJobPost;
    boolean inUS;
    boolean isSDE;
    String jobTitle;
    boolean isEntryLevel;
    int years;
    int matchedTechFramesCount;
    String techFrames;
    List<String> matchedTechFrames;
    
    public JobLink(String url) {
        this.url = url;
    }
    
    public String asString() {
        StringBuilder sb = new StringBuilder();
        if (!isJobPost) {
            sb.append("notJobPost; \n" + url + "\n");
            return sb.toString();
        }
        if (!inUS) {
            sb.append("notInUS; \n" + url + "\n");
            return sb.toString();
        }
        if (!isSDE) {
            sb.append("notSDE; \n" + url + "\n");
            return sb.toString();
        }
        
        sb.append(jobTitle + "; ");
        sb.append("Year Experience: " + years + "; ");
        if (isEntryLevel) {
            sb.append("Entry Level" + "; ");
        } else {
            sb.append("Non-Entry Level" + "; ");
        }
        sb.append(techFrames + "; ");
        sb.append("matchedTechFramesCount: " + matchedTechFramesCount + "; ");
        
        sb.append("\n");
        sb.append(url + "\n");
        return sb.toString();
    }
}


