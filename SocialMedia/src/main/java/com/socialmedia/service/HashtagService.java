
package com.socialmedia.service;

import com.socialmedia.dao.HashTagDao;
import com.socialmedia.model.Hashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashTagService {
    
    private HashTagDao hashTagDao;
    
    @Autowired
    public HashTagService(HashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }
    
    public Hashtag getHashTagById(Long id) {
        return hashTagDao.findById(id).orElse(null);
    }
    
    public Hashtag createHashTag(Hashtag hashTag) {
        return hashTagDao.save(hashTag);
    }
    
    public Hashtag updateHashTag(Long id, Hashtag hashTag) {
        hashTag.setId(id);
        return hashTagDao.save(hashTag);
    }
    
    public void deleteHashTag(Long id) {
        hashTagDao.deleteById(id);
    }
    
}
