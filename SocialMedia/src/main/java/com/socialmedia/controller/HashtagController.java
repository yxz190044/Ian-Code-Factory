
package com.socialmedia.controller;

import com.socialmedia.model.Hashtag;
import com.socialmedia.service.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hashtags")
public class HashTagController {
    
    private HashTagService hashTagService;
    
    @Autowired
    public HashTagController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Hashtag> getHashTagById(@PathVariable Long id) {
        Hashtag hashtag = hashTagService.getHashTagById(id);
        return new ResponseEntity<>(hashtag, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<Hashtag> createHashTag(@RequestBody Hashtag hashTag) {
        Hashtag createdHashTag = hashTagService.createHashTag(hashTag);
        return new ResponseEntity<>(createdHashTag, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Hashtag> updateHashTag(@PathVariable Long id, @RequestBody Hashtag hashTag) {
        Hashtag updatedHashTag = hashTagService.updateHashTag(id, hashTag);
        return new ResponseEntity<>(updatedHashTag, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHashTag(@PathVariable Long id) {
        hashTagService.deleteHashTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
