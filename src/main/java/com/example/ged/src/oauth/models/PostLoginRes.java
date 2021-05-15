package com.example.ged.src.oauth.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostLoginRes {
    private Integer userIdx;
    private String jwt;

}
