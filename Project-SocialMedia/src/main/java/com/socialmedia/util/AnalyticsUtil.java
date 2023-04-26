package com.socialmedia.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.socialmedia.model.Post;

public class AnalyticsUtil {
    
    public static Map<String, Integer> countHashtags(List<Post> posts) {
        Map<String, Integer> hashtagCounts = new HashMap<>();
        
        for (Post post : posts) {
            String[] hashtags = post.getHashtags().split(",");
            for (String hashtag : hashtags) {
                hashtag = hashtag.trim();
                if (hashtag.length() > 0) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }
        
        return hashtagCounts;
    }
    
}

