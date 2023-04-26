
package com.socialmedia.controller;

import com.socialmedia.model.Like;
import com.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    
    private LikeService likeService;
    
    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id) {
        Like like = likeService.getLikeById(id);
        return like != null ? ResponseEntity.ok(like) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody Like like) {
        Like createdLike = likeService.createLike(like);
        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Like> updateLike(@PathVariable Long id, @RequestBody Like like) {
        Like updatedLike = likeService.updateLike(id, like);
        return updatedLike != null ? ResponseEntity.ok(updatedLike) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }
}
