package com.example.ged.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserRes {
    private final Long userIdx;
    private final String jwt;
}
