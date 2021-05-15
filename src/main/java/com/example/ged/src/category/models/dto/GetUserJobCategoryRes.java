package com.example.ged.src.category.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserJobCategoryRes {
    private final Integer userJobCategoryIdx;
    private final String jobName;
}
