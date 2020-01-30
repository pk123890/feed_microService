package com.example.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostActivityDTO {
    private PostDTO postDTO;
    private long userFriendId;
}
