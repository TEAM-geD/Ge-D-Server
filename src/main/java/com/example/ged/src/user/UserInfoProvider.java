package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserInfoProvider {
    private final UserInfoRepository userInfoRepository;

    /**
     * 유저조회
     * @return User
     * @throws BaseException
     */
    public UserInfo retrieveUserInfoBySocialId(String socialId) throws BaseException {
        // 1. socialId 이용해서 UserInfo DB 접근
        List<UserInfo> existsUserInfoList;
        try {
            existsUserInfoList = userInfoRepository.findBySocialIdAndStatus(socialId, "ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(DATABASE_ERROR);
        }

        // 2. 존재하는 UserInfo가 있는지 확인
        UserInfo userInfo;
        if (existsUserInfoList != null && existsUserInfoList.size() > 0) {
            userInfo = existsUserInfoList.get(0);
        } else {
            userInfo = null;
        }

        // 3. UserInfo를 return
        return userInfo;
    }

//    /**
//     * socialId 로 UserInfo 찾기
//     * @param socialId
//     * @return
//     * @throws BaseException
//     */
//    public UserInfo retrieveUserInfoBySocialId(String socialId) throws BaseException{
//        UserInfo userInfo = userInfoRepository.findBySocialIdAndStatus(socialId,"ACTIVE");
//        if(userInfo == null){
//            throw new BaseException(NOT_FOUND_USER);
//        }
//        return userInfo;
//    }
}
