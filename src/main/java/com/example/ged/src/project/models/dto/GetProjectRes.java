package com.example.ged.src.project.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetProjectRes {
    private Integer projectIdx;
    private String projectName;
    private String projectThumbNailImageUrl;
    private String projectImageUrl1;
    private String projectDescription1;
    private String projectImageUrl2;
    private String projectDescription2;
    private String projectImageUrl3;
    private String projectDescription3;
    private String applyKakaoLinkUrl;
    private String applyGoogleFoamUrl;
    private Integer projectLikeStatus;//찜하기 눌렀으면 1, 안눌렀으면 0
}
