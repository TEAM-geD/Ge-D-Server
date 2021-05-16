package com.example.ged.src.oauth.models;

import com.example.ged.src.user.models.LoginMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostLoginReq {
    private String token;
    private LoginMethod loginMethod;

    public boolean isKakaoType(){
        return loginMethod.equals(LoginMethod.KAKAO);
    }
    public boolean isNaverType(){
        return loginMethod.equals(LoginMethod.NAVER);
    }
    public boolean isAppleType(){
        return loginMethod.equals(LoginMethod.APPLE);
    }
}
