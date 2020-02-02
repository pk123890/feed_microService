package com.example.feed.controller;

import com.example.feed.dto.UserDTO;
import com.example.feed.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "user", url = "http://172.16.20.180:8082")
public interface UserProxy {
    @GetMapping("user/getUserDetails/{userId}")
    BaseResponse<UserDTO> getFriendsList(@PathVariable(value = "userId") String userId);
    @GetMapping("user/getUserDetails/{userId}")
    BaseResponse<UserDTO> getUserDetailsById(@PathVariable(value = "userId") String userId);
}
