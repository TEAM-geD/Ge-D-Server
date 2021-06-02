package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.user.models.PostSignUpReq;
import com.example.ged.src.user.models.PostUserRes;
import com.example.ged.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.example.ged.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;

    /**
     * 카카오 회원가입 API
     * [POST] /users/kakao-signup
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/users/kakao-signup")
    public BaseResponse<PostUserRes> postKakaoSignUp(@RequestBody PostSignUpReq parameters) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");


        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }
        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }

        if (parameters.getUserJob() == null || parameters.getUserJob().length() == 0) {
            return new BaseResponse<>(EMPTY_USER_JOB);
        }
        if (parameters.getUserJob() != "기획자"||parameters.getUserJob() != "개발자"||parameters.getUserJob() != "디자이너") {
            return new BaseResponse<>(INVALID_USER_JOB);
        }

        try {
            PostUserRes postUserRes = userInfoService.createKakaoSignUp(accessToken, deviceToken, parameters);
            return new BaseResponse<>(SUCCESS,postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 네이버 회원가입 API
     * [POST] /users/naver-signup
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/users/naver-signup")
    public BaseResponse<PostUserRes> postNaverSignUp(@RequestBody PostSignUpReq parameters) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("NAVER-ACCESS-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");


        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }
        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }

        if (parameters.getUserJob() == null || parameters.getUserJob().length() == 0) {
            return new BaseResponse<>(EMPTY_USER_JOB);
        }

        if (parameters.getUserJob() != "기획자"||parameters.getUserJob() != "개발자"||parameters.getUserJob() != "디자이너") {
            return new BaseResponse<>(INVALID_USER_JOB);
        }

        try {
            PostUserRes postUserRes = userInfoService.createNaverSignUp(accessToken, deviceToken, parameters);
            return new BaseResponse<>(SUCCESS,postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 카카오 로그인 API
     * [POST] /users/kakao-signin
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/users/kakao-signin")
    public BaseResponse<PostUserRes> postKakaoSignIn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");


        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }
        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }

        try {
            PostUserRes postUserRes = userInfoService.createKakaoSignIn(accessToken, deviceToken);
            return new BaseResponse<>(SUCCESS,postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 네이버 로그인 API
     * [POST] /users/naver-signin
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/users/naver-signin")
    public BaseResponse<PostUserRes> postNaverSignIn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");


        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }
        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }

        try {
            PostUserRes postUserRes = userInfoService.createNaverSignIn(accessToken, deviceToken);
            return new BaseResponse<>(SUCCESS,postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 자동 로그인 API
     * [POST] /users/auto-signin
     */
    @ResponseBody
    @PostMapping("/users/auto-signin")
    public BaseResponse<Void> postAutoSignIn() {

        try {
            Long userIdx = jwtService.getUserIdx();
            userInfoProvider.retrieveUserByUserIdx(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 탈퇴 API
     * [DELETE] /users/status
     */
    @ResponseBody
    @PatchMapping("/users/status")
    public BaseResponse<Void> patchUserStatus() {


        Long userIdx;
        try {
            userIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        try {
            userInfoService.updateUserStatus(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
