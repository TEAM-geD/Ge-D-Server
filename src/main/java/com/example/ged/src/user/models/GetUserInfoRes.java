package com.example.ged.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class GetUserInfoRes {
    private final Integer userIdx;
    private final String userName;
    private final String introduce;
    private final String profileImageUrl;
    private final String backgroundImageUrl;
    private final String userJob;
    private final String isMembers;
    private final List snsUrlList;
}
