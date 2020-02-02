package com.example.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedDTO {

    private String userId;
    private String userName;
    private String imageUrl;
    private List<PostUserDTO> postDetails;

}
