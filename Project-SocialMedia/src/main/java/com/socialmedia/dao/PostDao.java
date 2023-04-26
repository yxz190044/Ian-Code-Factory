
package com.socialmedia.dao;

import java.util.List;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    Post findByIdAndUser(Long id, User user);
    List<Post> findByHashtagsOrderByCreatedAtDesc(Hashtag hashtag);
    Post save(Post post);
}
