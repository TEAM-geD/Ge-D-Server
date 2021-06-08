package com.example.ged.src.referenceLike.models;

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
@Table(name="ReferenceLike")
public class ReferenceLike extends BaseEntity {
    @Id
    @Column(name="idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name="userIdx",nullable = false)
    private Integer userIdx;

    @Column(name="referenceIdx",nullable = false)
    private Integer referenceIdx;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public ReferenceLike(Integer userIdx,Integer referenceIdx){
        this.userIdx = userIdx;
        this.referenceIdx = referenceIdx;
    }

}