package com.example.ged.src.user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class PatchUserInfoReq {
    private String userName;
    private String introduce;
    private String profileImageUrl;
    private String backgroundImageUrl;
    private String userJob;
    private String isMembers;
    private List<String> snsUrlList;

}
