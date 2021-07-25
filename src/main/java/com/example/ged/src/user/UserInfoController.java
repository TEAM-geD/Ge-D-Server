package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.user.models.*;
import com.example.ged.src.user.models.dto.GetMyInfoRes;
import com.example.ged.src.user.models.dto.GetMyProjectsRes;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;

import static com.example.ged.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;

    /**
     * 카카오 로그인 API
     * [POST] /users/kakao-signin
     * @return BaseResponse<PostUserSignInRes>
     */
    @ResponseBody
    @PostMapping("/users/kakao-signin")
    public BaseResponse<PostUserSignInRes> postKakaoSignIn() {
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
            PostUserSignInRes postUserSignInRes = userInfoService.createKakaoSignIn(accessToken, deviceToken);
            return new BaseResponse<>(SUCCESS, postUserSignInRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 네이버 로그인 API
     * [POST] /users/naver-signin
     * @return BaseResponse<PostUserSignInRes>
     */
    @ResponseBody
    @PostMapping("/users/naver-signin")
    public BaseResponse<PostUserSignInRes> postNaverSignIn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("NAVER-ACCESS-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");


        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }
        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }


        try {
            PostUserSignInRes postUserSignInRes = userInfoService.createNaverSignIn(accessToken, deviceToken);
            return new BaseResponse<>(SUCCESS, postUserSignInRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 애플 로그인 API
     * [POST] /users/apple-signin
     * @RequestBody postAppleSignInReq
     * @return BaseResponse<PostUserSignInRes>
     */
    @ResponseBody
    @PostMapping("/users/apple-signin")
    public BaseResponse<PostUserSignInRes> postAppleSignIn(@RequestBody PostAppleSignInReq postAppleSignInReq) throws BaseException, ParseException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String idToken = request.getHeader("APPLE-ID-TOKEN");
        String deviceToken = request.getHeader("DEVICE-TOKEN");
        String userName = postAppleSignInReq.getUserName();
        String userEmail = postAppleSignInReq.getUserEmail();

        if (deviceToken == null || deviceToken.length() == 0) {
            return new BaseResponse<>(EMPTY_DEVICE_TOKEN);
        }

        if (idToken == null || idToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ID_TOKEN);
        }

        // idToken 디코딩하여 sub 뽑기
        SignedJWT signedJWT = SignedJWT.parse(idToken);
        ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();
        String socialId = "apple_"+payload.getSubject();

        // socialId db에 있는지 확인
        UserInfo existsUserInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);

        try {
            PostUserSignInRes postUserSignInRes;
            if (existsUserInfo == null){
                if (userName == null || userName.length() == 0) {
                    return new BaseResponse<>(EMPTY_USER_NAME);
                }
                if (userEmail == null || userEmail.length() == 0) {
                    return new BaseResponse<>(EMPTY_USER_EMAIL);
                }
                // 첫번째 로그인

                postUserSignInRes = userInfoService.createAppleSignUp(socialId, userName, userEmail, deviceToken);

            }
            else{
                // 로그인
                postUserSignInRes = userInfoService.createAppleSignIn(existsUserInfo);
            }

            return new BaseResponse<>(SUCCESS, postUserSignInRes);
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
            Integer userIdx = jwtService.getUserIdx();
            userInfoProvider.retrieveUserByUserIdx(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 탈퇴 API
     * [PATCH] /users/:userIdx/status
     */
    @ResponseBody
    @PatchMapping("/users/{userIdx}/status")
    public BaseResponse<Void> patchUserStatus(@PathVariable Integer userIdx) throws BaseException {


        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if(userIdx != jwtUserIdx){
            return new BaseResponse<>(FORBIDDEN_USER);
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }


        try {
            userInfoService.updateUserStatus(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 정보 조회 API
     * [GET] /users/:userIdx/info
     * @PathVariable userIdx
     * @return BaseResponse<GetUserInfoRes>
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}/info")
    public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable Integer userIdx) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


        if(userIdx != jwtUserIdx){
            return new BaseResponse<>(FORBIDDEN_USER);
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        try {
            GetUserInfoRes getUserInfoRes = userInfoProvider.retrieveUserInfo(userIdx);
            return new BaseResponse<>(SUCCESS,getUserInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 유저 정보 수정 API
     * [PATCH] /users/:userIdx/info
     * @RequestBody patchUserInfoReq
     * @PathVariable userIdx
     * @return BaseResponse<PatchUserInfoRes>
     */
    @ResponseBody
    @PatchMapping("/users/{userIdx}/info")
    public BaseResponse<PatchUserInfoRes> patchUserInfo(@PathVariable Integer userIdx, @RequestBody PatchUserInfoReq patchUserInfoReq) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if(userIdx != jwtUserIdx){
            return new BaseResponse<>(FORBIDDEN_USER);
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        if (patchUserInfoReq.getUserName() == null || patchUserInfoReq.getUserName().length() == 0 ) {
            return new BaseResponse<>(EMPTY_USER_NAME);
        }

        if (patchUserInfoReq.getIntroduce() == null || patchUserInfoReq.getIntroduce().length() == 0 ) {
            return new BaseResponse<>(EMPTY_INTRODUCE);
        }

        if (patchUserInfoReq.getIntroduce() != null && patchUserInfoReq.getIntroduce().length() > 40 ) {
            return new BaseResponse<>(INVALID_INTRODUCE_LENGTH);
        }

        if (patchUserInfoReq.getProfileImageUrl() == null || patchUserInfoReq.getProfileImageUrl().length() == 0 ) {
            return new BaseResponse<>(EMPTY_PROFILE_IMAGE_URL);
        }

        if (patchUserInfoReq.getBackgroundImageUrl() == null || patchUserInfoReq.getBackgroundImageUrl().length() == 0 ) {
            return new BaseResponse<>(EMPTY_BACKGROUND_IMAGE_URL);
        }

        if (patchUserInfoReq.getUserJob() == null || patchUserInfoReq.getUserJob().length() == 0) {
            return new BaseResponse<>(EMPTY_USER_JOB);
        }

        ArrayList<String> userJobList = new ArrayList<String>();

        userJobList.add("기획자");
        userJobList.add("개발자");
        userJobList.add("디자이너");

        if (!userJobList.contains(patchUserInfoReq.getUserJob())) {
            return new BaseResponse<>(INVALID_USER_JOB);
        }
        if (patchUserInfoReq.getIsMembers() == null || patchUserInfoReq.getIsMembers().length() == 0) {
            return new BaseResponse<>(EMPTY_IS_MEMBERS);
        }
        ArrayList<String> isMembersList = new ArrayList<String>();

        isMembersList.add("Y");
        isMembersList.add("N");

        if (!isMembersList.contains(patchUserInfoReq.getIsMembers())) {
            return new BaseResponse<>(INVALID_IS_MEMBERS);
        }

        if (patchUserInfoReq.getSnsUrlList() == null || patchUserInfoReq.getSnsUrlList().isEmpty()) {
            return new BaseResponse<>(EMPTY_SNS_URL_LIST);
        }

        for(int i=0;i<patchUserInfoReq.getSnsUrlList().size();i++){
            String url = patchUserInfoReq.getSnsUrlList().get(i);
            if(!url.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
                return new BaseResponse<>(INVALID_URL);
            }
        }

        try {
            PatchUserInfoRes patchUserInfoRes = userInfoService.updateUserInfo(jwtUserIdx, userIdx, patchUserInfoReq);
            return new BaseResponse<>(SUCCESS,patchUserInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 멤버스 리스트 조회 API
     * [GET] /members?job=developer
     * @PathVariable userIdx
     * @return BaseResponse<GetUserInfoRes>
     */
    @ResponseBody
    @GetMapping("/members")
    public BaseResponse<List<GetMembersRes>> getMembersList(@RequestParam String job) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if (job== null || job.length() == 0) {
            return new BaseResponse<>(EMPTY_USER_JOB);
        }

        ArrayList<String> userJobList = new ArrayList<String>();

        userJobList.add("기획자");
        userJobList.add("개발자");
        userJobList.add("디자이너");

        if (!userJobList.contains(job)) {
            return new BaseResponse<>(INVALID_USER_JOB);
        }

        try {
            List<GetMembersRes> getMembersResList = userInfoProvider.retrieveMembers(job);
            return new BaseResponse<>(SUCCESS,getMembersResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 멤버스 상세 조회 API
     * @param projectStatus
     * @return
     */
    @GetMapping("/members/{userIdx}")
    @ResponseBody
    @Operation
    public BaseResponse<GetMyInfoRes> getMembers(@RequestParam(value = "projectStatus",required = true) Integer projectStatus,
                                                @PathVariable(required = true,value = "userIdx")Integer userIdx){
        ArrayList<Integer> projectStatusList = new ArrayList<>();
        projectStatusList.add(0);
        projectStatusList.add(1);
        projectStatusList.add(2);
        projectStatusList.add(3);

        if(!projectStatusList.contains(projectStatus)){
            return new BaseResponse<>(INVALID_PROJECT_STATUS);
        }

        try{
            Integer userJwtIdx = jwtService.getUserIdx();
            GetMyInfoRes getMyInfoRes = userInfoProvider.getMyInfoRes(userIdx,projectStatus);
            return new BaseResponse<>(SUCCESS,getMyInfoRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 마이페이지 조회 API
     * @param projectStatus
     * @return
     */
    @GetMapping("/users/{userIdx}")
    @ResponseBody
    @Operation
    public BaseResponse<GetMyInfoRes> getMyInfo(@RequestParam(value = "projectStatus",required = true) Integer projectStatus,
                                                @PathVariable(required = true,value = "userIdx")Integer userIdx){
        /**
         * projectStatus == 0 : 모집중
         * projectStatus == 1 : 진행중
         * projectStatus == 2 :  마감
         * projectStatus == 3 : 전체
         *
         */
        ArrayList<Integer> projectStatusList = new ArrayList<>();
        projectStatusList.add(0);
        projectStatusList.add(1);
        projectStatusList.add(2);
        projectStatusList.add(3);

        if(!projectStatusList.contains(projectStatus)){
            return new BaseResponse<>(INVALID_PROJECT_STATUS);
        }

        try{
            Integer userJwtIdx = jwtService.getUserIdx();
            if(userIdx != userJwtIdx){
                return new BaseResponse<>(DIFFERENT_USER_INDEX_AND_JWT);
            }
            GetMyInfoRes getMyInfoRes = userInfoProvider.getMyInfoRes(userIdx,projectStatus);
            return new BaseResponse<>(SUCCESS,getMyInfoRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 마이페이지 자세히보기 API
     * @param userIdx
     * @param type
     * @return
     */
    @GetMapping("/users/{userIdx}/detail")
    @ResponseBody
    @Operation
    public BaseResponse<List<GetMyProjectsRes>> getMyProjectsDetail(@PathVariable(required = true, value = "userIdx")Integer userIdx,
                                                                    @RequestParam(required = true, value="type")Integer type){

        /**
         * type == 1 : 내가 만든 프로젝트
         * type == 2 : 내가 신청한 프로젝트
         */
        if(type != 1 && type !=2){
            return new BaseResponse<>(INVALID_SELECT_TYPE);
        }
        try{
            Integer userJwtIdx = jwtService.getUserIdx();
            if(userIdx != userJwtIdx){
                return new BaseResponse<>(DIFFERENT_USER_INDEX_AND_JWT);
            }
            List<GetMyProjectsRes> getMyProjectsResList = userInfoProvider.getMyProjectsRes(userIdx,type);
            return new BaseResponse<>(SUCCESS,getMyProjectsResList);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
