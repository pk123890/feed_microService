package com.example.feed.controller;


import com.example.feed.collection.Feed;
import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.NewUserDataDto;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;
import com.example.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("feed")
public class FeedController {
    @Autowired
    FeedService feedService;

    @GetMapping("/getFeed/{userId}")
    public Feed getFeed(@PathVariable("userId") String userId) {
        Feed feed=feedService.getFeed(userId);
        return feed;
    }

    @PostMapping("/addPostAfterActivity")
    public void addPostAfterActivity(@RequestBody PostActivityDTO postActivityDTO){
        feedService.addPostInFeedAfterActivity(postActivityDTO);
    }

    @GetMapping("/createFeed/{userId}")
    public void createFeed(@PathVariable("userId")String userId){
        feedService.createFeed(userId);
    }

    @GetMapping("createNewFeed")
    public String createNewFeed(@RequestBody NewUserDataDto newUserDataDto){
        feedService.createNewFeed(newUserDataDto);
        return "New User Feed Created";
    }


}
