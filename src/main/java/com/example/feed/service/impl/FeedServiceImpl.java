package com.example.feed.service.impl;

import com.example.feed.collection.Feed;
import com.example.feed.controller.PostProxy;
import com.example.feed.controller.UserProxy;
import com.example.feed.dto.*;
import com.example.feed.repository.FeedRepository;
import com.example.feed.response.BaseResponse;
import com.example.feed.service.FeedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public FeedDTO createFeed(String userId) {

        BaseResponse<UserDTO> userProxyFriends = userProxy.getFriendsList(userId);
        List<PostUserDTO> postUserDTOList = new ArrayList<>();
        if(userProxyFriends.getData().getFriendIds()!=null) {
            HashSet<String> friendIds = userProxyFriends.getData().getFriendIds();
            List<String> friendList = new ArrayList<>(friendIds);
            List<BaseResponse<UserDTO>> friendsdetails = new ArrayList<>();
            List<PostUserDTO> friendsPostUserDTOList = new ArrayList<>();

            for (String friendId : friendList) {
                friendsdetails.add(userProxy.getFriendsList(friendId));
            }
            List<PostDTO> friendsPostDTOList = postProxy.getPostsByUserIds(friendList);
            for (PostDTO postDTO : friendsPostDTOList) {
                PostUserDTO postUserDTO=new PostUserDTO();
                postUserDTO.setPostDTO(postDTO);
                postUserDTO.setUserName(userProxyFriends.getData().getUserName());
                postUserDTO.setImageUrl(userProxyFriends.getData().getImageUrl());
                friendsPostUserDTOList.add(postUserDTO);
            }

            postUserDTOList.addAll(friendsPostUserDTOList);
        }

        List<PostDTO> postDTOList = postProxy.postByUserId(userId);





        for (PostDTO postDTO : postDTOList) {
            PostUserDTO postUserDTO=new PostUserDTO();
            postUserDTO.setPostDTO(postDTO);
            postUserDTO.setUserName(userProxyFriends.getData().getUserName());
            postUserDTO.setImageUrl(userProxyFriends.getData().getImageUrl());
            postUserDTOList.add(postUserDTO);
        }




        Feed feed = feedRepository.findByUserId(userId);
        FeedDTO feedDTO = new FeedDTO();
        List<String> user2IdsList=new ArrayList<>();


        List<String> messagesList = new ArrayList<>();


        feed.setPostUserDTOList(postUserDTOList);
        user2IdsList.add(userId);
        messagesList.add("Posted");
        feed.setUser2Ids(user2IdsList);
        feed.setMessages(messagesList);
        feedRepository.save(feed);
        BeanUtils.copyProperties(feed, feedDTO);


        return feedDTO;


    }

    @Override
    public FeedDTO createNewFeed(String userId) {
        Feed feed =new Feed();
        feed.setUserId(userId);
        feed.setPostUserDTOList(null);
        feed.setMessages(null);
        feed.setUser2Ids(null);
        feedRepository.save(feed);
        FeedDTO feedDTO=new FeedDTO();
        BeanUtils.copyProperties(feed,feedDTO);
        return feedDTO;
    }

    @Override
    public FeedDTO addPostInFeedAfterActivity(PostActivityDTO postActivityDTO) {
        Feed feed = feedRepository.findByUserId(postActivityDTO.getUserFriendId());
        Feed feed1 = feedRepository.findByUserId(postActivityDTO.getPostDTO().getUserId());

        FeedDTO feedDTO = new FeedDTO();
        PostUserDTO postUserDTO = new PostUserDTO();

        List<String> messages=feed1.getMessages();
        List<PostUserDTO> postUserDTOList = feed.getPostUserDTOList();
        List<String> user2IdsList = feed1.getUser2Ids();

        String user2Id = postActivityDTO.getUserFriendId();
        String message=postActivityDTO.getMessage();

        messages.add(message);
        user2IdsList.add(user2Id);

        BaseResponse<UserDTO> userProxyFriends = userProxy.getFriendsList(postActivityDTO.getPostDTO().getUserId());

        postUserDTO.setPostDTO(postActivityDTO.getPostDTO());
        postUserDTO.setUserName(userProxyFriends.getData().getUserName());
        postUserDTO.setImageUrl(userProxyFriends.getData().getImageUrl());
        postUserDTOList.add(postUserDTO);
        feed.setPostUserDTOList(postUserDTOList);

        BeanUtils.copyProperties(feed, feedDTO);
        return feedDTO;
    }

    @Override
    public FeedDTO getFeed(String userId) {
        FeedDTO feedDTO = new FeedDTO();
        Feed feed = feedRepository.findByUserId(userId);
        BeanUtils.copyProperties(feed,feedDTO);
        return feedDTO;
    }


}
