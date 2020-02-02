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
import java.util.*;

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
        if (!userProxyFriends.getData().getFriendIds().contains("")) {
            HashSet<String> friendIds = userProxyFriends.getData().getFriendIds();
            List<String> friendList = new ArrayList<>(friendIds);
            List<BaseResponse<UserDTO>> friendsdetails = new ArrayList<>();
            List<PostUserDTO> friendsPostUserDTOList = new ArrayList<>();

            for (String friendId : friendList) {
                friendsdetails.add(userProxy.getFriendsList(friendId));
            }
            List<PostDTO> friendsPostDTOList = postProxy.getPostsByUserIds(friendList);

            for (PostDTO postDTO : friendsPostDTOList) {
                PostUserDTO postUserDTO = new PostUserDTO();
                postUserDTO.setPostDTO(postDTO);
                postUserDTO.setUserName(friendsdetails.get(0).getData().getUserName());
                postUserDTO.setImageUrl(friendsdetails.get(0).getData().getImageUrl());
                friendsPostUserDTOList.add(postUserDTO);

            }

            postUserDTOList.addAll(friendsPostUserDTOList);
        }

        List<PostDTO> postDTOList = postProxy.postByUserId(userId);
        for (PostDTO postDTO : postDTOList) {
            PostUserDTO postUserDTO = new PostUserDTO();
            postUserDTO.setPostDTO(postDTO);
            postUserDTO.setUserName(userProxyFriends.getData().getUserName());
            postUserDTO.setImageUrl(userProxyFriends.getData().getImageUrl());
            postUserDTOList.add(postUserDTO);
        }


        Feed feed = feedRepository.findByUserId(userId);
        FeedDTO feedDTO = new FeedDTO();
        List<String> user2IdsList = new ArrayList<>();


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
    public void createNewFeed(NewUserDataDto newUserDataDto)
    {
        Feed feed = new Feed();
        feed.setUserId(newUserDataDto.getUserId());
        feed.setImageUrl(newUserDataDto.getImageUrl());
        feed.setUserName(newUserDataDto.getUserName());
        feedRepository.insert(feed);
    }

    @Override
    public void addPostInFeedAfterActivity(PostActivityDTO postActivityDTO)
    {
        if(!postActivityDTO.getUserFriendId().equals("empty"))
        {
            Feed feed = feedRepository.findByUserId(postActivityDTO.getUserFriendId());

            PostUserDTO postUserDTO = new PostUserDTO();
            postUserDTO.setMessage(postActivityDTO.getMessage());
            postUserDTO.setPostDTO(postActivityDTO.getPostDTO());
            postUserDTO.setFriendId(postActivityDTO.getUserFriendId());



           /* List<String> messages = feed1.getMessages();
            List<PostUserDTO> postUserDTOList = feed.getPostUserDTOList();
            List<String> user2IdsList = feed1.getUser2Ids();

            String user2Id = postActivityDTO.getUserFriendId();
            String message = postActivityDTO.getMessage();

            messages.add(message);
            user2IdsList.add(user2Id);*/



            BaseResponse<UserDTO> userProxyFriends = userProxy.getFriendsList(postActivityDTO.getUserFriendId());
            UserDTO userDTO = userProxyFriends.getData();

            HashSet<String> friendList = userDTO.getFriendIds();

            for(String id : friendList)
            {
                FeedDTO feedDTO = new FeedDTO();
                Feed friendFeed = feedRepository.findByUserId(id);
                if(friendFeed.getPostDetails()==null)
                {
                    List<PostUserDTO> postUserDTOS = new ArrayList<>();
                    postUserDTOS.add(postUserDTO);
                    friendFeed.setPostDetails(postUserDTOS);
                    feedRepository.save(friendFeed);
                }
                else {
                    List<PostUserDTO> postUserDTOS = friendFeed.getPostDetails();
                    postUserDTOS.add(postUserDTO);
                    friendFeed.setPostDetails(postUserDTOS);
                    feedRepository.save(friendFeed);
                }
            }


            postUserDTO.setPostDTO(postActivityDTO.getPostDTO());
            postUserDTO.setUserName(userProxyFriends.getData().getUserName());
            postUserDTO.setImageUrl(userProxyFriends.getData().getImageUrl());
            postUserDTOList.add(postUserDTO);
            feed.setPostUserDTOList(postUserDTOList);

            BeanUtils.copyProperties(feed, feedDTO);
        }
        else
        {

        }

    }

    @Override
    public Feed getFeed(String userId) {
//        FeedDTO feedDTO = new FeedDTO();
        Feed feed = feedRepository.findByUserId(userId);

        return feed;
    }


}
