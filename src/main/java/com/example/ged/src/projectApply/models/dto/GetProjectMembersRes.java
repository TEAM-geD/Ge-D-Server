package com.example.ged.src.projectApply.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetProjectMembersRes {
    private final Integer projectMakerIdx;
    private final String projectMakerName;
    private final String projectMakerJobName;
    private final List<GetProjectMemberInfoRes> projectMemberInfoList;
}
