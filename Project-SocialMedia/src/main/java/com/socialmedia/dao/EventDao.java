
package com.socialmedia.dao;

import com.socialmedia.model.Event;
import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
    
    List<Event> findByUser(User user);
    
    List<Event> findByGuestsContains(User user);
}
