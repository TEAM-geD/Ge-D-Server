package com.example.ged.src.oauth;

import com.example.ged.config.BaseException;
import com.example.ged.src.oauth.models.PostLoginRes;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import com.example.ged.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

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
public class KakaoLoginService {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;

//    /**
//     * 카카오 로그인
//     * @param accessToken
//     * @return
//     * @throws BaseException
//     */
//    public PostLoginRes kakaoLogin(String accessToken) throws BaseException{
//        JSONObject jsonObject;
//
//        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
//        String apiURL = "https://kapi.kakao.com/v2/user/me";
//
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("Authorization", header);
//
//        HttpURLConnection con;
//        try {
//            URL url = new URL(apiURL);
//            con = (HttpURLConnection) url.openConnection();
//        } catch (MalformedURLException e) {
//            throw new BaseException(WRONG_URL);
//        } catch (IOException e) {
//            throw new BaseException(FAILED_TO_CONNECT);
//        }
//
//        String body;
//        try {
//            con.setRequestMethod("GET");
//            for (Map.Entry<String, String> requestHeader : requestHeaders.entrySet()) {
//                con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
//            }
//
//            int responseCode = con.getResponseCode();
//            InputStreamReader streamReader;
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
//                streamReader = new InputStreamReader(con.getInputStream());
//            } else { // 에러 발생
//                streamReader = new InputStreamReader(con.getErrorStream());
//            }
//
//            BufferedReader lineReader = new BufferedReader(streamReader);
//            StringBuilder responseBody = new StringBuilder();
//
//            String line;
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            body = responseBody.toString();
//        } catch (IOException e) {
//            throw new BaseException(FAILED_TO_READ_RESPONSE);
//        } finally {
//            con.disconnect();
//        }
//
//        if (body.length() == 0) {
//            throw new BaseException(FAILED_TO_READ_RESPONSE);
//        }
//
//        String socialId;
//        String response;
//        try{
//            JSONParser jsonParser = new JSONParser();
//            jsonObject = (JSONObject) jsonParser.parse(body);
//            socialId = "kakao_"+jsonObject.get("id").toString();
//            response = jsonObject.get("kakao_account").toString();
//        }
//        catch (Exception e){
//            throw new BaseException(FAILED_TO_PARSE);
//        }
//
//        String profilePhoto=null;
//        String userName=null;
//        String email=null;
//        String phoneNumber=null;
//        try {
//            JSONParser jsonParser = new JSONParser();
//            JSONObject responseObj = (JSONObject) jsonParser.parse(response);
//            if(responseObj.get("email")!=null) {
//                email = responseObj.get("email").toString();
//            }
//
//            String profile = responseObj.get("profile").toString();
//            JSONObject profileObj = (JSONObject) jsonParser.parse(profile);
//            userName = profileObj.get("nickname").toString();
//
//            /*
//            if(profileObj.get("profile_image")!=null) {
//                profilePhoto = profileObj.get("profile_image").toString();
//            }*/
//        }
//        catch (Exception e){
//            throw new BaseException(FAILED_TO_PARSE);
//        }
//        UserInfo userInfo = userInfoProvider.retrieveUserInfoBySocialId(socialId);
//        String jwt = jwtService.createJwt(userInfo.getUserIdx());
//        return new PostLoginRes(userInfo.getUserIdx(), jwt);
//    }
}
