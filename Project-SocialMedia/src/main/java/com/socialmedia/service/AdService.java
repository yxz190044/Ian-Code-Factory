
package com.socialmedia.controller;

import com.socialmedia.model.Ad;
import com.socialmedia.service.AdService;
import com.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {
    
    @Autowired
    private AdService adService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public Ad getById(@PathVariable("id") Long id) {
        return adService.getById(id);
    }
    
    @GetMapping("/user")
    public List<Ad> getByUser(Principal principal) {
        String username = principal.getName();
        return adService.getByUser(userService.getByUsername(username));
    }
    
    @GetMapping("/search")
    public List<Ad> search(@RequestParam("q") String keyword) {
        return adService.search(keyword);
    }
    
    @PostMapping
    public Ad create(@RequestBody Ad ad, Principal principal) {
        String username = principal.getName();
        ad.setUser(userService.getByUsername(username));
        ad.setCreatedAt(new Date());
        adService.create(ad);
        return ad;
    }
    
    @PutMapping("/{id}")
    public Ad update(@PathVariable("id") Long id, @RequestBody Ad ad) {
        Ad existingAd = adService.getById(id);
        existingAd.setTitle(ad.getTitle());
        existingAd.setDescription(ad.getDescription());
        adService.update(existingAd);
        return existingAd;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Ad existingAd = adService.getById(id);
        adService.delete(existingAd);
    }
}
