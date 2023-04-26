
package com.socialmedia.controller;

import com.socialmedia.model.Event;
import com.socialmedia.model.User;
import com.socialmedia.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    
    private final EventService eventService;
    
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    
    @GetMapping("")
    public List<Event> getEvents(@AuthenticationPrincipal UserDetails userDetails) {
        User user = eventService.getUserFromUserDetails(userDetails);
        return eventService.getEventsForUser(user);
    }
    
    @PostMapping("")
    public Event createEvent(@RequestBody Event event, @AuthenticationPrincipal UserDetails userDetails) {
        User user = eventService.getUserFromUserDetails(userDetails);
        return eventService.createEvent(event, user);
    }
    
    @PostMapping("/{id}/guests")
    public Event addGuestToEvent(@PathVariable("id") Long eventId, @RequestParam Long guestId) {
        return eventService.addGuestToEvent(eventId, guestId);
    }
}
