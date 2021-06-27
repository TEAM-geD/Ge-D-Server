package com.example.ged.src.projectHeart.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.project.models.Project;
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
@Table(name="ProjectHeart")
public class ProjectHeart extends BaseEntity {

    @Id
    @Column(name="idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectHeartIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectIdx", nullable = false)
    private Project project;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public ProjectHeart(UserInfo userInfo,Project project){
        this.userInfo = userInfo;
        this.project = project;
    }
}
