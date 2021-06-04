package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.src.user.models.*;
import com.example.ged.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoProvider userInfoProvider;
    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;

    /**
     * 카카오 로그인 API
     * @param accessToken,deviceToken
     * @return PostUserSignInRes
     * @throws BaseException
     */
    public PostUserSignInRes createKakaoSignIn(String accessToken, String deviceToken) throws BaseException {
        JSONObject jsonObject;

        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        String apiURL = "https://kapi.kakao.com/v2/user/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        HttpURLConnection con;
        try {
            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new BaseException(WRONG_URL);
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_CONNECT);
        }

        String body;
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> rqheader : requestHeaders.entrySet()) {
                con.setRequestProperty(rqheader.getKey(), rqheader.getValue());
            }

            int responseCode = con.getResponseCode();
            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                streamReader = new InputStreamReader(con.getInputStream());
            } else { // 에러 발생
                streamReader = new InputStreamReader(con.getErrorStream());
            }

            BufferedReader lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            body = responseBody.toString();
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        } finally {
            con.disconnect();
        }

        if (body.length() == 0) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        }
        System.out.println(body);

        String socialId;
        String response;
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(body);
            socialId = "kakao_"+jsonObject.get("id").toString();
            response = jsonObject.get("kakao_account").toString();
        }
        catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }

        String profilePhoto=null;
        String userName=null;
        String email=null;
        String phoneNumber=null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject responObj = (JSONObject) jsonParser.parse(response);
            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
            }

            String profile = responObj.get("profile").toString();
            JSONObject profileObj = (JSONObject) jsonParser.parse(profile);
            userName = profileObj.get("nickname").toString();

            /*
            if(profileObj.get("profile_image")!=null) {
                profilePhoto = profileObj.get("profile_image").toString();
            }*/

        }
        catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }


        UserInfo existUserInfo = null;
        existUserInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);

        // 1-1. 존재하는 회원이 없다면 회원가입
        if (existUserInfo == null) {
            UserInfo userInfo = new UserInfo(userName, null, null, deviceToken, null, null,null,socialId,email);

            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_SAVE_USERINFO);
            }

            String jwt = jwtService.createJwt(userInfo.getUserIdx());

            Integer useridx = userInfo.getUserIdx();
            return new PostUserSignInRes(useridx, jwt);

        }
        // 1-2. 존재하는 회원이 있다면 로그인
        else {
            String jwt = jwtService.createJwt(existUserInfo.getUserIdx());

            Integer useridx = existUserInfo.getUserIdx();
            return new PostUserSignInRes(useridx, jwt);
        }
    }

    /**
     * 네이버 로그인 API
     * @param accessToken,deviceToken
     * @return PostUserSignInRes
     * @throws BaseException
     */
    public PostUserSignInRes createNaverSignIn(String accessToken, String deviceToken) throws BaseException {
        JSONObject jsonObject;
        String resultcode;

        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        HttpURLConnection con;
        try {
            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new BaseException(WRONG_URL);
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_CONNECT);
        }

        String body;
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> rqheader : requestHeaders.entrySet()) {
                con.setRequestProperty(rqheader.getKey(), rqheader.getValue());
            }

            int responseCode = con.getResponseCode();
            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                streamReader = new InputStreamReader(con.getInputStream());
            } else { // 에러 발생
                streamReader = new InputStreamReader(con.getErrorStream());
            }

            BufferedReader lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            body = responseBody.toString();
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        } finally {
            con.disconnect();
        }

        if (body.length() == 0) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        }

        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(body);
            resultcode = jsonObject.get("resultcode").toString();
            System.out.println(resultcode);
        }
        catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }

        String response;
        if(resultcode.equals("00")){
            response = jsonObject.get("response").toString();
            System.out.println(response);
        }
        else{
            throw new BaseException(FORBIDDEN_ACCESS);
        }

        String socialId;
        String profilePhoto=null;
        String userName;
        String email=null;
        String phoneNumber=null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject responObj = (JSONObject) jsonParser.parse(response);
            socialId = "naver_"+responObj.get("id").toString();
            /*
            if(responObj.get("profile_image")!=null) {
                profilePhoto = responObj.get("profile_image").toString();
            }*/
            userName = responObj.get("name").toString();
            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
            }
            if(responObj.get("mobile")!=null) {
                phoneNumber = responObj.get("mobile").toString();
            }
        }
        catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }
        UserInfo existUserInfo = null;
        existUserInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);

        // 1-1. 존재하는 회원이 없다면 회원가입
        if (existUserInfo == null) {
            UserInfo userInfo = new UserInfo(userName, null, null, deviceToken, null, null,null,socialId,email);

            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_SAVE_USERINFO);
            }

            String jwt = jwtService.createJwt(userInfo.getUserIdx());

            Integer useridx = userInfo.getUserIdx();
            return new PostUserSignInRes(useridx, jwt);

        }
        // 1-2. 존재하는 회원이 있다면 로그인
        else{
            String jwt = jwtService.createJwt(existUserInfo.getUserIdx());

            Integer useridx = existUserInfo.getUserIdx();
            return new PostUserSignInRes(useridx, jwt);
        }


    }


    /**
     * 유저 탈퇴 API
     * @throws BaseException
     */
    public void updateUserStatus(Integer jwtUserIdx) throws BaseException {

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);

        userInfo.setStatus("INACTIVE");
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_SAVE_USERINFO);
        }
    }

    /**
     * 유저 정보 수정 API
     * @param userIdx,parameters
     * @return PatchUserInfoRes
     * @throws BaseException
     */
    public PatchUserInfoRes updateUserInfo(Integer jwtUserIdx, Integer userIdx, PatchUserInfoReq patchUserInfoReq) throws BaseException {
        //jwt 확인
        if(userIdx != jwtUserIdx){
            throw new BaseException(FORBIDDEN_USER);
        }
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);

        String userName = patchUserInfoReq.getUserName();
        String introduce = patchUserInfoReq.getIntroduce();
        String profileImageUrl = patchUserInfoReq.getProfileImageUrl();
        String backgroundImageUrl = patchUserInfoReq.getBackgroundImageUrl();
        String userJob = patchUserInfoReq.getUserJob();
        String isMembers = patchUserInfoReq.getIsMembers();

        userInfo.setUserName(userName);
        userInfo.setIntroduce(introduce);
        userInfo.setProfileImageUrl(profileImageUrl);
        userInfo.setBackgroundImageUrl(backgroundImageUrl);
        userInfo.setUserJob(userJob);
        userInfo.setIsMembers(isMembers);
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_SAVE_USERINFO);
        }


        return new PatchUserInfoRes(userIdx,userName,introduce,profileImageUrl,backgroundImageUrl,userJob,isMembers);
    }


}
