package com.example.ged.src.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetMyInfoRes {
    private final String userName;
    private final String userJob;
    private final String userProfileImageUrl;
    private final String introduce;
    private final List<String> userSnsUrlList;
    //내가 만든 프로젝트 리스트 (projectStatus 랑 관련없음)
    private final List<GetMyProjectInfoRes> myProjectInfoList;
    //내가 참여한 프로젝트 리스트
    private final List<GetMyProjectInfoRes> myApplyProjectInfoList;


}
