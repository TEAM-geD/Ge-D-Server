package com.example.ged.src.referenceCategory.models;

import com.example.ged.config.BaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="ReferenceCategory")
public class ReferenceCategory extends BaseEntity {
    @Id
    @Column(name="referenceCategoryIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer referenceCategoryIdx;

    @Column(name="referenceCategoryName",nullable = false,length = 20)
    private String referenceCategoryName;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public ReferenceCategory(String referenceCategoryName){
        this.referenceCategoryName = referenceCategoryName;
    }

}