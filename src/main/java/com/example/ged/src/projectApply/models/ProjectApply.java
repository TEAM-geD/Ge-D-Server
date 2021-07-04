package com.example.ged.src.projectApply.models;

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
@Table(name="ProjectApply")
public class ProjectApply extends BaseEntity {

    @Id
    @Column(name="projectApplyIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectApplyIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectIdx", nullable = false)
    private Project project;

    @Column(name="applyStatus",nullable = false)
    private String applyStatus;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";
}
