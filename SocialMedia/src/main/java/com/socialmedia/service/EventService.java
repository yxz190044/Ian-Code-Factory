
package com.socialmedia.service;

import com.socialmedia.dao.EventDao;
import com.socialmedia.dao.UserDao;
import com.socialmedia.model.Event;
import com.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EventService {
    
    private final EventDao eventDao;
    private final UserDao userDao;
    
    @Autowired
    public EventService(EventDao eventDao, UserDao userDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
    }
    
    public User getUserFromUserDetails(UserDetails userDetails) {
        return userDao.findByEmail(userDetails.getUsername());
    }
    
    public List<Event> getEventsForUser(User user) {
        return eventDao.findByUser(user);
    }
    
    public Event createEvent(Event event, User user) {
        event.setCreatedAt(new Date());
        event.setUser(user);
        return eventDao.save(event);
    }
    
    public Event addGuestToEvent(Long eventId, Long guestId) {
        Event event = eventDao.findById(eventId).orElse(null);
        User guest = userDao.findById(guestId).orElse(null);
        
        if (event == null || guest == null) {
            return null;
        }
        
        Set<User> guests = event.getGuests();
        if (guests == null) {
            guests = new HashSet<>();
        }
        guests.add(guest);
        event.setGuests(guests);
        return eventDao.save(event);
    }
}
