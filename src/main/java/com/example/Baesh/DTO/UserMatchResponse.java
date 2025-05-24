package com.example.Baesh.DTO;

import com.example.Baesh.Entity.UserE;
import lombok.Data;

import java.util.List;

@Data
public class UserMatchResponse {
    private String userId;
    private String name;
    private String intro;
    private List<String> matchedTags;
    private int matchCount;

    public UserMatchResponse(UserE user,List<String> matchedTags){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.matchedTags = matchedTags;
        this.matchCount = matchedTags.size();
    }
}
