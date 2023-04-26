
package com.socialmedia.service;

import com.socialmedia.dao.PostDao;
import com.socialmedia.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    
    @Autowired
    private PostDao postDao;
    
    public List<Post> getAllPosts() {
        return postDao.findAll();
    }
    
    public Post getPostById(Long id) {
        return postDao.findById(id).orElse(null);
    }
    
    public Post createPost(Post post) {
        return postDao.save(post);
    }
    
    public Post updatePost(Long id, Post post) {
        Post existingPost = getPostById(id);
        if (existingPost != null) {
            post.setId(id);
            return postDao.save(post);
        } else {
            return null;
        }
    }
    
    public void deletePost(Long id) {
        postDao.deleteById(id);
    }
}
