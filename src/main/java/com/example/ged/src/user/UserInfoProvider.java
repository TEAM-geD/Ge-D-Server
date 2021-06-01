package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserInfoProvider {
    private final UserInfoRepository userInfoRepository;


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
