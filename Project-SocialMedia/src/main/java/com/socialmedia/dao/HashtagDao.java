package com.socialmedia.dao;

import com.socialmedia.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagDao extends JpaRepository<Hashtag, Long> {
    
}
