package com.example.feed.service;
import com.example.feed.collection.Feed;
import com.example.feed.dto.*;

import java.util.List;

public interface FeedService {
    Feed getFeed(String userId);
//    List<PostDTO> getFriendsFeed(String userId);
    void addPostInFeedAfterActivity(PostActivityDTO postActivityDTO);
    List<UserFeedDto> createFeed(String userId);
    void createNewFeed(NewUserDataDto newUserDataDto);

}
