package com.example.ged.src.user.models;

import com.example.ged.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="UserInfo")
public class UserInfo extends BaseEntity {
    @Id
    @Column(name="userIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIdx;

    @Column(name="userName",nullable = false, length = 10)
    private String userName;

    @Column(name="introduce", length = 40)
    private String introduce;

    @Column(name="profileImageUrl",columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(name="deviceToken",columnDefinition = "TEXT")
    private String deviceToken;

    @Column(name="userJob",nullable = false, length = 4)
    private String userJob;

    @Column(name="isMembers",nullable = false, length = 1)
    private String isMembers;

    @Column(name="backgroundImageUrl",columnDefinition = "TEXT")
    private String backgroundImageUrl;

    @Column(name="socialId",nullable = false, length = 100)
    private String socialId;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public UserInfo(String userName, String introduce, String profileImageUrl, String deviceToken, String userJob, String isMembers, String backgroundImageUrl, String socialId){
        this.userName = userName;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.deviceToken = deviceToken;
        this.userJob = userJob;
        this.isMembers = isMembers;
        this.backgroundImageUrl = backgroundImageUrl;
        this.socialId = socialId;
    }

}
