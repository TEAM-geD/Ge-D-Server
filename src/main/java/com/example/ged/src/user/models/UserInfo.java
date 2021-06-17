package com.example.ged.src.user.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.referenceHeart.models.ReferenceHeart;
import com.example.ged.src.userSns.models.UserSns;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="User")
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

    @Column(name="deviceToken",nullable = false,columnDefinition = "TEXT")
    private String deviceToken;

    @Column(name="userJob", length = 4)
    private String userJob;

    @Column(name="isMembers", length = 1)
    private String isMembers;

    @Column(name="backgroundImageUrl",columnDefinition = "TEXT")
    private String backgroundImageUrl;

    @Column(name="socialId",nullable = false, length = 100)
    private String socialId;

    @Column(name="email",nullable = false, length = 100)
    private String email;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<ReferenceHeart> referenceHearts = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<UserSns> userSnss = new ArrayList<>();


    public UserInfo(String userName, String introduce, String profileImageUrl, String deviceToken, String userJob, String isMembers, String backgroundImageUrl, String socialId, String email){
        this.userName = userName;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.deviceToken = deviceToken;
        this.userJob = userJob;
        this.isMembers = isMembers;
        this.backgroundImageUrl = backgroundImageUrl;
        this.socialId = socialId;
        this.email = email;
    }

}
