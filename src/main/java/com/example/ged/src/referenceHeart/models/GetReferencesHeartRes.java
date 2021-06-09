package com.example.ged.src.referenceHeart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class GetReferencesHeartRes {
    private final Integer referenceIdx;
    private final String referenceName;
    private final String referenceThumbnail;
    private final String referenceAuthor;
    private final String referenceUrl;
}
