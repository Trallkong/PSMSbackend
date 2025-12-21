package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Community;
import com.trallkong.psmsbackend.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping
    public List<Community> getAllCommunities() {
        return communityService.getAllCommunities();
    }

    @GetMapping("/{id}")
    public Community getCommunityById(@PathVariable Integer id) {
        return communityService.getCommunityById(id);
    }

    @PostMapping
    public Community addCommunity(@RequestBody Community community) {
        return communityService.addCommunity(community);
    }

    @DeleteMapping("/{id}")
    public void deleteCommunityById(@PathVariable Integer id) {
        communityService.deleteCommunityById(id);
    }

    @PutMapping("/{id}")
    public Community updateCommunity(@PathVariable Integer id, @RequestBody Community community) {
        return communityService.updateCommunity(id, community);
    }
}
