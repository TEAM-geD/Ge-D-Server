package com.example.ged.src.userSns.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="UserSns")
public class UserSns extends BaseEntity {
    @Id
    @Column(name="userSnsIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSnsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @Column(name="snsUrl",columnDefinition = "TEXT")
    private String snsUrl;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public UserSns(UserInfo userInfo, String snsUrl){
        this.userInfo = userInfo;
        this.snsUrl = snsUrl;
    }

}
