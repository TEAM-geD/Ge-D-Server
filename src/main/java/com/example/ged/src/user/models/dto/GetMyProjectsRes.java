package com.example.ged.src.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetMyProjectsRes {
    private final Integer projectIdx;
    private final String projectThumbnailImageUrl;
    private final String projectName;
    private final List<String> projectJobNameList;
    private final Integer projectStatus;
    private final Integer userIdx;
    private final String userName;
    private final String userJob;
    private final String userProfileImageUrl;
}
