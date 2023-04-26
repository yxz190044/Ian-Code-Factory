
package com.socialmedia.controller;

import com.socialmedia.dao.UserDao;
import com.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserDao userDao;
    
    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userDao.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @GetMapping("/search")
    public List<User> searchUsersByUsername(@RequestParam("keyword") String keyword) {
        return userDao.searchByUsername(keyword);
    }
    
    @GetMapping("/{id}/following")
    public List<User> getFollowedUsers(@PathVariable("id") Long id) {
        return userDao.findFollowedUsers(id);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User savedUser = userDao.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = userDao.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        User savedUser = userDao.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, @AuthenticationPrincipal User currentUser) {
        User user = userDao.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!currentUser.equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userDao.delete(user);
        return ResponseEntity.noContent().build();
    }
}
