package com.example.feed.service;
import com.example.feed.collection.Feed;
import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.NewUserDataDto;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;

import java.util.List;

public interface FeedService {
    Feed getFeed(String userId);
//    List<PostDTO> getFriendsFeed(String userId);
    FeedDTO addPostInFeedAfterActivity(PostActivityDTO postActivityDTO);
    FeedDTO createFeed(String userId);
    void createNewFeed(NewUserDataDto newUserDataDto);

}
