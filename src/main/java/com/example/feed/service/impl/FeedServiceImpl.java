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
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;

@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    FeedRepository feedRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    PostProxy postProxy;

    @Override
    public List<UserFeedDto> createFeed(String userId) {

        FeedDTO feedDTO = new FeedDTO();
        BaseResponse<UserDTO> userProxyFriends = userProxy.getFriendsList(userId);
        HashSet<String> friendIds = userProxyFriends.getData().getFriendIds();
        Feed feed = feedRepository.findByUserId(userId);

        List<PostUserDTO> postUserDTOList = new ArrayList<>();
        if (!feed.getPostDetails().contains(null)) {
            postUserDTOList.addAll(feed.getPostDetails());
        }

        for (String id : friendIds) {
            if (id == "") {
                continue;
            }
            Feed friendFeed = feedRepository.findByUserId(id);
            if (!friendFeed.getPostDetails().contains(null)) {
                postUserDTOList.addAll(friendFeed.getPostDetails());
            }
        }

        System.out.println(postUserDTOList);

        List<PostUserDTO> postTimelineDtosdesc = postUserDTOList.stream()
                .sorted(Comparator.comparing(PostUserDTO::getTimeStamp).reversed())
                .collect(Collectors.toList());
        List<PostUserDTO> postWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(postTimelineDtosdesc));


        List<UserFeedDto> userFeedDtos = new ArrayList<>();

        for (PostUserDTO postUserDTO : postWithoutDuplicates) {
            userFeedDtos.add(mapPostToUserFeed(postUserDTO));
        }

        BeanUtils.copyProperties(feed, feedDTO);


        return userFeedDtos;


    }

    @Override
    public void createNewFeed(NewUserDataDto newUserDataDto) {
        Feed feed = new Feed();
        feed.setUserId(newUserDataDto.getUserId());
        feed.setImageUrl(newUserDataDto.getImageUrl());
        feed.setUserName(newUserDataDto.getUserName());
        feedRepository.insert(feed);
    }

    @Override
    public void addPostInFeedAfterActivity(PostActivityDTO postActivityDTO) {
        PostUserDTO postUserDTO = new PostUserDTO();
        postUserDTO.setMessage(postActivityDTO.getMessage());
        //postActivityDTO.getPostDTO().setTimestamp(getTimeStamp());
        postUserDTO.setTimeStamp(getTimeStamp());
        postUserDTO.setPostDTO(postActivityDTO.getPostDTO());
        postUserDTO.setFriendId(postActivityDTO.getUserFriendId());
        postUserDTO.setPostId(postActivityDTO.getPostDTO().getPostId());

        if (!postActivityDTO.getUserFriendId().equals("empty")) {

            BaseResponse<UserDTO> userProxyFriends = userProxy.getFriendsList(postActivityDTO.getUserFriendId());
            UserDTO userDTO = userProxyFriends.getData();

            HashSet<String> friendList = userDTO.getFriendIds();

            for (String id : friendList) {
                if (id == "") {
                    continue;
                }
                Feed friendFeed = feedRepository.findByUserId(id);
                List<PostUserDTO> postUserDTOS = new ArrayList<>();
                if (friendFeed.getPostDetails()==null) {
                    postUserDTOS.add(postUserDTO);
                    friendFeed.setPostDetails(postUserDTOS);
                    feedRepository.save(friendFeed);
                } else {
                    postUserDTOS = friendFeed.getPostDetails();
                    postUserDTOS.add(postUserDTO);
                    friendFeed.setPostDetails(postUserDTOS);
                    feedRepository.save(friendFeed);
                }
            }

        } else {
            Feed userFeed = feedRepository.findByUserId(postActivityDTO.getPostDTO().getUserId());
            List<PostUserDTO> postUserDTOS = new ArrayList<>();
            if (userFeed.getPostDetails() == null) {
                postUserDTOS.add(postUserDTO);
                userFeed.setPostDetails(postUserDTOS);
                feedRepository.save(userFeed);
            } else {

                postUserDTOS = userFeed.getPostDetails();
                postUserDTOS.add(postUserDTO);
                userFeed.setPostDetails(postUserDTOS);
                feedRepository.save(userFeed);
            }
        }

    }

    @Override
    public Feed getFeed(String userId) {
//        FeedDTO feedDTO = new FeedDTO();
        Feed feed = feedRepository.findByUserId(userId);

        return feed;
    }

    public Date getTimeStamp() {
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        return timeStamp;
    }

    public UserFeedDto mapPostToUserFeed(PostUserDTO postUserDTO) {
        UserFeedDto userFeedDto = new UserFeedDto();
        if (postUserDTO.getMessage().equals("Posted")) {
            Feed feed = feedRepository.findByUserId(postUserDTO.getPostDTO().getUserId());
            userFeedDto.setUserId(feed.getUserId());
            userFeedDto.setUserName(feed.getUserName());
            userFeedDto.setImageUrl(feed.getImageUrl());
            userFeedDto.setActivity("Posted");
            userFeedDto.setPostDTO(postUserDTO.getPostDTO());
            userFeedDto.setFriendId(null);
            userFeedDto.setFriendName(null);
        } else {
            Feed feed = feedRepository.findByUserId(postUserDTO.getFriendId());
            userFeedDto.setUserId(feed.getUserId());
            userFeedDto.setUserName(feed.getUserName());
            userFeedDto.setImageUrl(feed.getImageUrl());
            userFeedDto.setActivity(postUserDTO.getMessage());
            userFeedDto.setPostDTO(postUserDTO.getPostDTO());

            Feed feed1 = feedRepository.findByUserId(postUserDTO.getPostDTO().getUserId());
            userFeedDto.setFriendId(feed1.getUserId());
            userFeedDto.setFriendName(feed1.getUserName());
        }

        return userFeedDto;
    }


}
