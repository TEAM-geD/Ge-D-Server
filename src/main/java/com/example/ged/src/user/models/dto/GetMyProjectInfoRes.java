package com.example.ged.src.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetMyProjectInfoRes {
    private final Integer projectIdx;
    private final String projectThumbnailImageUrl;
    private final String projectName;
    private final Integer projectStatus;
    private final List<String> projectJobNameList;
    private final Integer userIdx;
    private final String userName;
    private final String userJob;
    private final List<GetProjectMemberProfile> projectTeamInfoList;
}
