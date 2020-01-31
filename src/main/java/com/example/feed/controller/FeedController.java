package com.example.feed.controller;


import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;
import com.example.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feed")
public class FeedController {
    @Autowired
    FeedService feedService;

    @GetMapping("/getFeed/{userId}")
    public FeedDTO getFeed(@PathVariable("userId") String userId) {
        FeedDTO feedDTO=feedService.getFeed(userId);
        return feedDTO;
    }

    @PostMapping("/addPostAfterActivity")
    public String addPostAfterActivity(@RequestBody PostActivityDTO postActivityDTO){
        return feedService.addPostInFeedAfterActivity(postActivityDTO);
    }

    @GetMapping("/createFeed/{userId}")
    public FeedDTO createFeed(@PathVariable("userId")String userId){
        FeedDTO feedDTO=feedService.createFeed(userId);
        return feedDTO;
    }



}
