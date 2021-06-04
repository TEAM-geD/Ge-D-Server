package com.example.ged.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserSignInRes {
    private final Integer userIdx;
    private final String jwt;
}
