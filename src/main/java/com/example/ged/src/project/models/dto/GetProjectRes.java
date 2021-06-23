package com.example.ged.src.project.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetProjectRes {
    private final Integer projectIdx;
    private final String projectName;
    private final String projectThumbNailImageUrl;
    private final String projectImageUrl1;
    private final String projectDescription1;
    private final String projectImageUrl2;
    private final String projectDescription2;
    private final String projectImageUrl3;
    private final String projectDescription3;
    private final String applyKakaoLinkUrl;
    private final String applyGoogleFoamUrl;
    private final Integer projectLikeStatus;//찜하기 눌렀으면 1, 안눌렀으면 0
    private final Integer projectStatus; //프로젝트 신청 가능 상태 (0 : 모집중, 1 : 진행중, 2 : 마감)
}
