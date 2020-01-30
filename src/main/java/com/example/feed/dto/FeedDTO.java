package com.example.feed.dto;

import javafx.geometry.Pos;
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
    private List<PostDTO> postDTOList;
    private List<String> messages;
}
