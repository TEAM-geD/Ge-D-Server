package com.example.ged.src.category.models;

import com.example.ged.config.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="UserInfo")
public class UserJobCategory extends BaseEntity {
    @Id
    @Column(name="userJobCategoryIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userJobCategoryIdx;

    @Column(name="jobName",nullable = false)
    private String jobName;

    @Column(name="status")
    private String status;
}
