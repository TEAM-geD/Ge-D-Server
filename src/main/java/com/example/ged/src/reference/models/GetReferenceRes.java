package com.example.ged.src.reference.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetReferenceRes {
    private final Integer referenceIdx;
    private final String referenceUrl;
}