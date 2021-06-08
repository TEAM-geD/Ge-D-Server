package com.example.ged.src.reference.models;

import com.example.ged.config.BaseEntity;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import com.example.ged.src.referenceLike.models.ReferenceLike;
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
@Table(name="Reference")
public class Reference extends BaseEntity {
    @Id
    @Column(name="referenceIdx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer referenceIdx;

    @Column(name="referenceName",nullable = false, length = 50)
    private String referenceName;

    @Column(name="referenceThumbnail",nullable = false,columnDefinition = "TEXT")
    private String referenceThumbnail;

    @Column(name="referenceAuthor",nullable = false, length = 20)
    private String referenceAuthor;

    @Column(name="referenceAuthorJob",nullable = false, length = 20)
    private String referenceAuthorJob;

    @Column(name="referenceUrl",nullable = false,columnDefinition = "TEXT")
    private String referenceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenceCategoryIdx", nullable = false)
    private ReferenceCategory referenceCategory;

    @Column(name="status",nullable = false)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "reference", cascade = CascadeType.ALL)
    private List<ReferenceLike> referenceLikes = new ArrayList<>();

    public Reference(String referenceName,String referenceThumbnail,String referenceAuthor,String referenceAuthorJob,String referenceUrl, ReferenceCategory referenceCategory){
        this.referenceName = referenceName;
        this.referenceThumbnail = referenceThumbnail;
        this.referenceAuthor = referenceAuthor;
        this.referenceAuthorJob = referenceAuthorJob;
        this.referenceUrl = referenceUrl;
        this.referenceCategory = referenceCategory;
    }

}
