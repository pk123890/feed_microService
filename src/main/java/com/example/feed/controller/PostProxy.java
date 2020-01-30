package com.example.feed.controller;

import com.example.feed.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "post", url = "http://10.177.68.182:8083")
public interface PostProxy {
    @GetMapping("post/getByUserId/{userId}")
    List<PostDTO> postByUserId(@PathVariable("userId") String userId);

    @PostMapping("post/getPostsByUserIds")
    List<PostDTO> getPostsByUserIds(@RequestBody List<String> userId);
}
