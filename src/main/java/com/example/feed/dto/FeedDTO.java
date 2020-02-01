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
    private List<String> user2Ids;
    private List<PostUserDTO> postUserDTOList;
    private List<String> messages;

}
