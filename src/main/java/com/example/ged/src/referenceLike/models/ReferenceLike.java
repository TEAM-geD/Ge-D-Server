package com.example.ged.src.referenceLike.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.reference.models.Reference;
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
@Table(name="ReferenceLike")
public class ReferenceLike extends BaseEntity {
    @Id
    @Column(name="idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenceIdx", nullable = false)
    private Reference reference;


    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    public ReferenceLike(UserInfo userInfo,Reference reference){
        this.userInfo = userInfo;
        this.reference = reference;
    }

}