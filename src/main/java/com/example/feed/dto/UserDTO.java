package com.example.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long userId;
    private String userName;
    private String imageUrl;
    private String gender;
    private String email;
    private Date DOB;
    private Long mobileNumber;
    private List<String> interests;
    private String profileType;
    private String displayType;
    private HashSet<Long> friendIds;
    private HashSet<Long> pendingFriendIds;
}
