
package com.socialmedia.service;

import com.socialmedia.dao.GroupDao;
import com.socialmedia.model.Group;
import com.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    
    private final GroupDao groupDao;
    
    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    
    public Group getGroupById(Long id) {
        return groupDao.findById(id).orElse(null);
    }
    
    public Group createGroup(Group group) {
        return groupDao.save(group);
    }
    
    public Group updateGroup(Long id, Group group) {
        group.setId(id);
        return groupDao.save(group);
    }
    
    public void deleteGroup(Long id) {
        groupDao.deleteById(id);
    }
    
    public List<User> getGroupMembers(Long id) {
        Group group = groupDao.findById(id).orElse(null);
        return group != null ? group.getMembers() : null;
    }
    
}
