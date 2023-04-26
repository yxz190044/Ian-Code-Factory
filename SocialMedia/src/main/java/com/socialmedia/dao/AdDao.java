
package com.socialmedia.dao;

import com.socialmedia.model.Ad;
import com.socialmedia.model.User;

import java.util.List;

public interface AdDao {
    
    Ad findById(Long id);
    
    List<Ad> findByUser(User user);
    
    List<Ad> findByTitleContainingIgnoreCase(String keyword);
    
    List<Ad> findByDescriptionContainingIgnoreCase(String keyword);
    
    void save(Ad ad);
    
    void delete(Ad ad);
}
