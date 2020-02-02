package com.example.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO
{

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostUserDTO that = (PostUserDTO) o;
        return Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(postId);
    }

    private PostDTO postDTO;
    private String postId;
    private String friendId;
    private String message;
    private Date timeStamp;
}
