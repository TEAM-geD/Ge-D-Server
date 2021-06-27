package com.example.ged.src.projectHeart.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetProjectsHeartRes {
    private final Integer projectIdx;
    private final String projectThumbnailImageUrl;
    private final String projectName;
    private final List<String> projectJobNameList;
    private final Integer userIdx;
    private final String userName;
    private final String userJob;
    private final String userProfileImageUrl;
}
