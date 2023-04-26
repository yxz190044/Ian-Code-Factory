
package com.socialmedia.service;

import com.socialmedia.dao.LikeDao;
import com.socialmedia.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    
    private LikeDao likeDao;
    
    @Autowired
    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }
    
    public Like getLikeById(Long id) {
        return likeDao.findById(id).orElse(null);
    }
    
    public Like createLike(Like like) {
        return likeDao.save(like);
    }
    
    public Like updateLike(Long id, Like like) {
        like.setId(id);
        return likeDao.save(like);
    }
    
    public void deleteLike(Long id) {
        likeDao.deleteById(id);
    }
}
