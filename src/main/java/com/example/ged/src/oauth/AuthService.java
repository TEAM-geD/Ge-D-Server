package com.example.ged.src.oauth;

import com.example.ged.config.BaseException;
import com.example.ged.src.oauth.models.PostLoginReq;
import com.example.ged.src.oauth.models.PostLoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

//    private final KakaoLoginService kakaoLoginService;
//    /**
//     * 로그인 서비스 함수
//     * @param postLoginReq
//     * @return
//     * @throws BaseException
//     */
//    public PostLoginRes loginService(PostLoginReq postLoginReq) throws BaseException {
//        PostLoginRes postLoginRes = null;
//        if(postLoginReq.isKakaoType()){
//            postLoginRes =  kakaoLoginService.kakaoLogin(postLoginReq.getToken());
//        }
//        if(postLoginReq.isNaverType()){
//
//        }
//        if(postLoginReq.isAppleType()){
//
//        }
//        return postLoginRes;
//    }
}
