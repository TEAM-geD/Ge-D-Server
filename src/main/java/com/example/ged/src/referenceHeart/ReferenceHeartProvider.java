package com.example.ged.src.referenceHeart;

import com.example.ged.config.BaseException;
import com.example.ged.src.reference.ReferenceProvider;
import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceHeart.models.ReferenceHeart;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ged.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class ReferenceHeartProvider {
    private final ReferenceHeartRepository referenceHeartRepository;
    private final UserInfoProvider userInfoProvider;
    private final ReferenceProvider referenceProvider;


    /**
     * 레퍼런스 좋아요 존재여부
     * @param userIdx, referenceIdx
     * @return Boolean
     * @throws BaseException
     */
    public Boolean existReferenceHeart(Integer userIdx, Integer referenceIdx) throws BaseException {
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Reference reference = referenceProvider.retrieveReferenceByReferenceIdx(referenceIdx);

        Boolean existReferenceHeart;
        try {
            existReferenceHeart = referenceHeartRepository.existsByUserInfoAndReferenceAndStatus(userInfo,reference,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERINFO_AND_REFERENCE_AND_STATUS);
        }


        return existReferenceHeart;
    }

    /**
     * userIdx,referenceIdx로 ReferenceHeart 조회
     * @param userIdx, referenceIdx
     * @return ReferenceHeart
     * @throws BaseException
     */
    public ReferenceHeart retrieveReferenceHeartByUserIdxAndReferenceIdx(Integer userIdx, Integer referenceIdx) throws BaseException {
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Reference reference = referenceProvider.retrieveReferenceByReferenceIdx(referenceIdx);

        ReferenceHeart referenceHeart;
        try {
            referenceHeart = referenceHeartRepository.findByUserInfoAndReferenceAndStatus(userInfo,reference,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_REFERENCE_AND_STATUS);
        }


        return referenceHeart;
    }





}