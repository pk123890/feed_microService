package com.example.feed.service;
import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;

import java.util.List;

public interface FeedService {
    FeedDTO getFeed(Long userId);
    List<PostDTO> getFriendsFeed(Long userId);
    Long addPostInFeedAfterActivity(PostActivityDTO postActivityDTO);

}
