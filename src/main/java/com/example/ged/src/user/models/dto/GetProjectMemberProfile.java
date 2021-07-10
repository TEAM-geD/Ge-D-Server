package com.example.ged.src.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetProjectMemberProfile {
    private final Integer userIdx;
    private final String userProfileImageUrl;
}
