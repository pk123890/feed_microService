package com.example.feed.repository;

import com.example.feed.collection.Feed;
import com.example.feed.dto.FeedDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedRepository extends MongoRepository<Feed,Long> {
    FeedDTO findByUserId(Long userId);
}
