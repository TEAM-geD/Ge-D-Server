package com.example.ged.src.reference.models;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetReferencesRes {
    private final Integer referenceCategoryIdx;
    private final Integer referenceIdx;
    private final String referenceThumbnail;
    private final String referenceAuthor;
    private final String referenceAuthorJob;
    private final String referenceUrl;
}
