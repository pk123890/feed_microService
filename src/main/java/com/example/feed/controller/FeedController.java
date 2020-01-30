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

    @GetMapping("/getfeed/{userId}")
    public FeedDTO getFeed(@PathVariable("userId") Long userId) {
        FeedDTO feedDTO=feedService.getFeed(userId);
        return feedDTO;
    }

    @PostMapping("/addPostAfterActivity")
    public Long addPostAfterActivity(@RequestBody PostActivityDTO postActivityDTO){
        return feedService.addPostInFeedAfterActivity(postActivityDTO);
    }

}
