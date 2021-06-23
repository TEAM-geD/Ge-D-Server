package com.example.ged.src.project.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostProjectReq {
    private List<String> projectCategoryNameList;
    private String projectName;
    private List<String> projectJobNameList;
    private String projectThumbnailImageUrl;
    private String projectImageUrl1;
    private String projectDescription1;
    private String projectImageUrl2;
    private String projectDescription2;
    private String projectImageUrl3;
    private String projectDescription3;
    private String applyKakaoLinkUrl;
    private String applyGoogleFoamUrl;
}
