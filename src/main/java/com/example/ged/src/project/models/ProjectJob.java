package com.example.ged.src.project.models;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="ProjectJob")
public class ProjectJob {
    @Id
    @Column(name="projectJobIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectJobIdx;//인덱스

    @Column(name="projectJobName",nullable = false,length = 30)
    private String projectJobName;//프로젝트 직군 이름

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="projectIdx",nullable = false)
    private Project project;//프로젝트

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";
}
