
package com.socialmedia.service;

import com.socialmedia.dao.UserDao;
import com.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    public User getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }
    
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }
    
    public User updateUser(User user) {
        User existingUser = userDao.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(existingUser);
        }
        return null;
    }
    
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
    
    public List<User> searchUsersByUsername(String keyword) {
        return userDao.searchByUsername(keyword);
    }
    
    public List<User> getFollowedUsers(Long userId) {
        return userDao.findFollowedUsers(userId);
    }
    
    public List<User> getUsersByHashtagName(String hashtagName) {
        return userDao.findByPostsHashtagsNameContainingIgnoreCase(hashtagName);
    }
    
    public List<User> getUsersByTagname(String tagName) {
        return userDao.findByPostsTagsNameContainingIgnoreCase(tagName);
    }
    
    public List<User> getUsersByTitle(String title) {
        return userDao.findByPostsTitleContainingIgnoreCase(title);
    }
    
    public List<User> getUsersByDescription(String description) {
        return userDao.findByPostsDescriptionContainingIgnoreCase(description);
    }
    
    public List<User> getUsersByLikedPostHashtag(String hashtagName) {
        return userDao.findByLikesPostHashtagsNameContainingIgnoreCase(hashtagName);
    }
    
    public List<User> getUsersByLikedPostTag(String tagName) {
        return userDao.findByLikesPostTagsNameContainingIgnoreCase(tagName);
    }
    
    public List<User> getUsersByPost(Post post) {
        return userDao.findByPostsOrderByCreatedAtDesc(post);
    }
    
    public List<User> getUsersByLike(Like like) {
        return userDao.findByLikesOrderByCreatedAtDesc(like);
    }
    
    public List<User> getUsersByComment(Comment comment) {
        return userDao.findByCommentsOrderByCreatedAtDesc(comment);
    }
}
