package com.example.ged.src.project.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetProjectsRes {
    private Integer projectIdx;
    private String projectThumbnailImageUrl;
    private String projectName;
    private List<String> projectJobNameList;
    private Integer userIdx;
    private String userName;
    private String userJob;
}
