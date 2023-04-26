package com.socialmedia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.Group;

public interface GroupDao extends JpaRepository<Group, Integer> {
    
    Group findById(int id);
    
    List<Group> findAll();
    
    void save(Group group);
    
    void delete(Group group);
    
}

