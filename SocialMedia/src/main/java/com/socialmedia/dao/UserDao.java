package com.socialmedia.dao;

import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    List<User> findByUsernameContainingIgnoreCase(String keyword);
    
    List<User> findByEmailContainingIgnoreCase(String keyword);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByUsername(@Param("keyword") String keyword);
    
    @Query("SELECT u FROM User u JOIN u.following f WHERE f.id = :userId")
    List<User> findFollowedUsers(@Param("userId") Long userId);
    
    List<User> findByPostsHashtagsNameContainingIgnoreCase(String keyword);
    
    List<User> findByLikesPostHashtagsNameContainingIgnoreCase(String keyword);
    
    List<User> findByPostsOrderByCreatedAtDesc(Post post);
    
    List<User> findByLikesOrderByCreatedAtDesc(Like like);
    
    List<User> findByCommentsOrderByCreatedAtDesc(Comment comment);
    
    List<User> findByPostsTagsNameContainingIgnoreCase(String keyword);
    
    List<User> findByLikesPostTagsNameContainingIgnoreCase(String keyword);
    
    List<User> findByPostsTitleContainingIgnoreCase(String keyword);
    
    List<User> findByPostsDescriptionContainingIgnoreCase(String keyword);
}
