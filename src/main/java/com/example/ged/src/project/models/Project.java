package com.example.ged.src.project.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="Project")
public class Project extends BaseEntity {

    @Id
    @Column(name="projectIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectIdx;//인덱스

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private UserInfo userInfo;//프로젝트를 만든 사람

    @Column(name="projectName",nullable = false,length = 50)
    private String projectName;//프로젝트명

    @Column(name="projectThumbnailImageUrl",nullable = false,columnDefinition = "TEXT")
    private String projectThumbnailImageUrl;//프로젝트 대표 이미지 URL

    @Column(name="projectImageUrl1",columnDefinition = "TEXT")
    private String projectImageUrl1;//프로젝트 이미지 1 URL

    @Column(name="projectDescription1",length = 255)
    private String projectDescription1;//프로젝트 설명 1

    @Column(name="projectImageUrl2",columnDefinition = "TEXT")
    private String projectImageUrl2;//프로젝트 이미지 2 URL

    @Column(name="projectDescription2",length = 255)
    private String projectDescription2;//프로젝트 설명 2

    @Column(name="projectImageUrl3",columnDefinition = "TEXT")
    private String projectImageUrl3;//프로젝트 이미지 3 URL

    @Column(name="projectDescription3",length = 255)
    private String projectDescription3;//프로젝트 설명 3

    @Column(name="applyKakaoLinkUrl",columnDefinition = "TEXT")
    private String applyKakaoLinkUrl;//카카오 신청링크 URL

    @Column(name="applyGoogleFoamUrl",columnDefinition = "TEXT")
    private String applyGoogleFoamUrl;//구글 폼 URL

    @Column(name="projectStartDate",length = 50)
    private String projectStartDate;//프로젝트 시작일자

    @Column(name="projectEndDate",length = 50)
    private String projectEndDate;//프로젝트 마감일자

    @Column(name="projectStatus",length = 10)
    private Integer projectStatus;//프로젝트 상태

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public Project(UserInfo userInfo, String projectName, String projectThumbNailImgUrl, String projectImgUrl1, String projectImgUrl2, String projectImgUrl3, String projectDescription1, String projectDescription2, String projectDescription3, String applyKakaoLinkUrl, String applyGoogleFoamUrl, int projectStatus) {
        this.userInfo = userInfo;
        this.projectName = projectName;
        this.projectThumbnailImageUrl = projectThumbNailImgUrl;
        this.projectImageUrl1 = projectImgUrl1;
        this.projectImageUrl2 = projectImgUrl2;
        this.projectImageUrl3 = projectImgUrl3;
        this.projectDescription1 = projectDescription1;
        this.projectDescription2 = projectDescription2;
        this.projectDescription3 = projectDescription3;
        this.applyKakaoLinkUrl = applyKakaoLinkUrl;
        this.applyGoogleFoamUrl = applyGoogleFoamUrl;
        this.projectStatus = projectStatus;

    }
}
