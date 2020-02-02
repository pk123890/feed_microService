package com.example.feed.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedDto {

    String userId;
    String userName;
    String imageUrl;
    String friendId;
    String activity;
    PostDTO postDTO;
    String friendName;

}
