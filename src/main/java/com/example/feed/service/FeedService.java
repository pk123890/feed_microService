package com.example.feed.service;
import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;

import java.util.List;

public interface FeedService {
    FeedDTO getFeed(String userId);
//    List<PostDTO> getFriendsFeed(String userId);
    FeedDTO addPostInFeedAfterActivity(PostActivityDTO postActivityDTO);
    FeedDTO createFeed(String userId);
    FeedDTO createNewFeed(String userId);

}
