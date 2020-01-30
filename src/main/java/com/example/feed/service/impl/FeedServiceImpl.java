package com.example.feed.service.impl;

import com.example.feed.collection.Feed;
import com.example.feed.controller.PostProxy;
import com.example.feed.controller.UserProxy;
import com.example.feed.dto.FeedDTO;
import com.example.feed.dto.PostActivityDTO;
import com.example.feed.dto.PostDTO;
import com.example.feed.dto.UserDTO;
import com.example.feed.repository.FeedRepository;
import com.example.feed.response.BaseResponse;
import com.example.feed.service.FeedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    FeedRepository feedRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    PostProxy postProxy;

    @Override
    public FeedDTO getFeed(String userId) {
        Feed feed = new Feed();
        FeedDTO feedDTO = new FeedDTO();
        List<PostDTO> postDTOList = postProxy.postByUserId(userId);
        List<String> messages = new ArrayList<>();
        BaseResponse<UserDTO> userProxyFriendsList = userProxy.getFriendsList(userId);
        HashSet<String> friendIds = userProxyFriendsList.getData().getFriendIds();
        List<String> friendList = new ArrayList<>(friendIds);
        List<PostDTO> postsDTOList = postProxy.getPostsByUserIds(friendList);
        messages.add("Posted");
        feed.setUserId(userId);
        feed.setUser2Ids(null);
        postDTOList.addAll(postsDTOList);
        feed.setPostDTOList(postDTOList);
        feed.setMessages(messages);
        feedRepository.save(feed);
        BeanUtils.copyProperties(feed, feedDTO);
        return feedDTO;

    }

    @Override
    public List<PostDTO> getFriendsFeed(String userId) {
        BaseResponse<UserDTO> userProxyFriendsList = userProxy.getFriendsList(userId);
        HashSet<String> friendIds = userProxyFriendsList.getData().getFriendIds();
        List<String> friendList = new ArrayList<>(friendIds);
        List<PostDTO> postDTOList = postProxy.getPostsByUserIds(friendList);
        return postDTOList;
    }

    @Override
    public String addPostInFeedAfterActivity(PostActivityDTO postActivityDTO) {
        Feed feed = new Feed();
        FeedDTO feedDTO = feedRepository.findByUserId(postActivityDTO.getUserFriendId());
        List<PostDTO> postProxyList = new ArrayList<>();
        postProxyList.add(postActivityDTO.getPostDTO());
        feedDTO.setPostDTOList(postProxyList);
        BeanUtils.copyProperties(feedDTO, feed);
        feedRepository.save(feed);
        return feed.getUserId();
    }
}
