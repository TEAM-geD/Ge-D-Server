package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.src.user.models.PostSignUpReq;
import com.example.ged.src.user.models.PostUserRes;
import com.example.ged.src.user.models.UserInfo;
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
     * 카카오 회원가입
     * @param accessToken,deviceToken,parameters
     * @return PostUserRes
     * @throws BaseException
     */
    public PostUserRes createKakaoSignUp(String accessToken, String deviceToken, PostSignUpReq parameters) throws BaseException {
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

        UserInfo userInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);

        String userJob = parameters.getUserJob();

        // 이미 존재하는 회원이 없다면 유저 정보 저장
        if (userInfo == null) {
            userInfo = new UserInfo(userName, null, profilePhoto, deviceToken, userJob, null,null,socialId);

            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }
        else{
            throw new BaseException(EXIST_USER);
        }

        // JWT 생성
        try {
            Integer userIdx = userInfo.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        }catch(Exception e){
            throw new BaseException(FAILED_TO_KAKAO_SIGN_UP);
        }
    }

    /**
     * 네이버 회원가입
     * @param accessToken,deviceToken,parameters
     * @return PostUserRes
     * @throws BaseException
     */
    public PostUserRes createNaverSignUp(String accessToken, String deviceToken, PostSignUpReq parameters) throws BaseException {
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
        UserInfo userInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);

        String userJob = parameters.getUserJob();

        // 이미 존재하는 회원이 없다면 유저 정보 저장
        if (userInfo == null) {
            userInfo = new UserInfo(userName, null, profilePhoto, deviceToken, userJob, null,null,socialId);

            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }
        else{
            throw new BaseException(EXIST_USER);
        }

        // JWT 생성
        try {
            Integer userIdx = userInfo.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        }catch(Exception e){
            throw new BaseException(FAILED_TO_NAVER_SIGN_UP);
        }

    }


    /**
     * 카카오 로그인
     * @param accessToken,deviceToken
     * @return PostUserRes
     * @throws BaseException
     */
    public PostUserRes createKakaoSignIn(String accessToken, String deviceToken) throws BaseException {
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

        UserInfo userInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);


        // 이미 존재하는 회원이 없다면 카카오 로그인 실패
        if (userInfo == null) {
            throw new BaseException(FAILED_TO_KAKAO_SIGN_IN);

        }
        else{
            userInfo.setDeviceToken(deviceToken);
            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
            String jwt = jwtService.createJwt(userInfo.getUserIdx());
            Integer useridx = userInfo.getUserIdx();
            return new PostUserRes(useridx, jwt);
        }
    }


    /**
     * 네이버 로그인
     * @param accessToken,deviceToken
     * @return PostUserRes
     * @throws BaseException
     */
    public PostUserRes createNaverSignIn(String accessToken, String deviceToken) throws BaseException {
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

        UserInfo userInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);


        // 이미 존재하는 회원이 없다면 네이버 로그인 실패
        if (userInfo == null) {
            throw new BaseException(FAILED_TO_NAVER_SIGN_IN);

        }
        else{
            userInfo.setDeviceToken(deviceToken);
            try {
                userInfo = userInfoRepository.save(userInfo);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
            String jwt = jwtService.createJwt(userInfo.getUserIdx());
            Integer useridx = userInfo.getUserIdx();
            return new PostUserRes(useridx, jwt);
        }
    }


    /**
     * 회원 탈퇴 API
     * @throws BaseException
     */
    public void updateUserStatus(Integer userIdx) throws BaseException {

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);

        userInfo.setStatus("INACTIVE");
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception ignored) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
