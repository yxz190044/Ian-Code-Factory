package com.socialmedia.dao;

import java.util.List;
import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDao extends JpaRepository<Like, Long> {
    List<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}

