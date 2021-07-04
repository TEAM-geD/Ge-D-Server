package com.example.ged.src.projectApply.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetProjectMemberInfoRes {
    private final Integer userIdx;
    private final String userName;
    private final String userJobName;
}
