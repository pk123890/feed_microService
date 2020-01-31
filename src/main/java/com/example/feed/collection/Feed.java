package com.example.feed.collection;

import com.example.feed.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "userfeed")
public class Feed {
    @Id
    private String userId;
    private List<String> user2Ids;
    private List<PostDTO> postDTOList;
    private List<String> messages;
}
