package com.example.ged.src.referenceHeart;

import com.example.ged.config.BaseException;
import com.example.ged.src.reference.ReferenceProvider;
import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceHeart.models.ReferenceHeart;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ged.config.BaseResponseStatus.FAILED_TO_SAVE_REFERENCE_HEART;


@Service
@RequiredArgsConstructor
public class ReferenceHeartService {
    private final ReferenceHeartProvider referenceHeartProvider;
    private final ReferenceHeartRepository referenceHeartRepository;
    private final UserInfoProvider userInfoProvider;
    private final ReferenceProvider referenceProvider;

    /**
     * 레퍼런스 찜하기 생성
     * @param userIdx,referenceIdx
     * @return void
     * @throws BaseException
     */
    public void createReferenceHeart(Integer userIdx, Integer referenceIdx) throws BaseException {
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Reference reference = referenceProvider.retrieveReferenceByReferenceIdx(referenceIdx);


        ReferenceHeart referenceHeart = new ReferenceHeart(userInfo, reference);
        try {
            referenceHeartRepository.save(referenceHeart);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_SAVE_REFERENCE_HEART);
        }

    }

    /**
     * 레퍼런스 찜하기 취소
     * @param userIdx,referenceIdx
     * @return void
     * @throws BaseException
     */
    public void deleteReferenceHeart(Integer userIdx, Integer referenceIdx) throws BaseException {
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Reference reference = referenceProvider.retrieveReferenceByReferenceIdx(referenceIdx);

        ReferenceHeart referenceHeart = referenceHeartProvider.retrieveReferenceHeartByUserIdxAndReferenceIdx(userIdx,referenceIdx);
        referenceHeart.setStatus("INACTIVE");

        try {
            referenceHeartRepository.save(referenceHeart);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_SAVE_REFERENCE_HEART);
        }
    }

}