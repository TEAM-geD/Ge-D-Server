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
@Table(name="ProjectCategory")
public class ProjectCategory extends BaseEntity {
    @Id
    @Column(name="projectCategoryIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectCategoryIdx;//인덱스

    @Column(name="projectCategoryName",nullable = false,length = 30)
    private String projectCategoryName;//프로젝트 카테고리 이름

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="projectIdx",nullable = false)
    private Project project;//프로젝트

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";
}
