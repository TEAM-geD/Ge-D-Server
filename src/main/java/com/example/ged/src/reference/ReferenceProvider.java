package com.example.ged.src.reference;

import com.example.ged.config.BaseException;
import com.example.ged.src.reference.models.GetReferenceRes;
import com.example.ged.src.reference.models.GetReferencesRes;
import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceCategory.ReferenceCategoryProvider;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import com.example.ged.src.referenceHeart.ReferenceHeartRepository;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReferenceProvider {
    private final ReferenceRepository referenceRepository;
    private final ReferenceCategoryProvider referenceCategoryProvider;
    private final UserInfoProvider userInfoProvider;
    private final ReferenceHeartRepository referenceHeartRepository;

    /**
     * 레퍼런스 리스트 조회 API
     * @param type
     * @return List<GetReferencesRes>
     * @throws BaseException
     */
    public List<GetReferencesRes> retrieveReferences(Integer type) throws BaseException {
        List<Reference> referenceList;
        ReferenceCategory referenceCategory = referenceCategoryProvider.retrieveReferenceCategoryByReferenceCategoryIdx(type);
        try {
            referenceList = referenceRepository.findByReferenceCategoryAndStatus(referenceCategory, "ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_REFERENCE_CATEGORY_AND_STATUS);
        }

        List<GetReferencesRes> getReferencesResList = new ArrayList<>();
        for (int i = 0; i < referenceList.size(); i++) {
            Integer referenceCategoryIdx = referenceList.get(i).getReferenceCategory().getReferenceCategoryIdx();
            Integer referenceIdx = referenceList.get(i).getReferenceIdx();
            String referenceThumbnail = referenceList.get(i).getReferenceThumbnail();
            String referenceAuthor = referenceList.get(i).getReferenceAuthor();
            String referenceAuthorJob = referenceList.get(i).getReferenceAuthorJob();
            String referenceAuthorProfileUrl = referenceList.get(i).getReferenceAuthorProfileUrl();
            String referenceUrl = referenceList.get(i).getReferenceUrl();

            GetReferencesRes getReferencesRes = new GetReferencesRes(referenceCategoryIdx, referenceIdx, referenceThumbnail, referenceAuthor, referenceAuthorJob,referenceAuthorProfileUrl, referenceUrl);
            getReferencesResList.add(getReferencesRes);

        }
        return getReferencesResList;
    }


    /**
     * 레퍼런스 상세 조회 API
     * @param referenceIdx,userIdx
     * @return GetReferenceRes
     * @throws BaseException
     */
    public GetReferenceRes retrieveReference(Integer referenceIdx,Integer userIdx) throws BaseException {

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Reference reference = retrieveReferenceByReferenceIdx(referenceIdx);

        Boolean existReferenceHeart = referenceHeartRepository.existsByUserInfoAndReferenceAndStatus(userInfo,reference,"ACTIVE");
        String isHeart;
        if(existReferenceHeart){
            isHeart="Y";
        }
        else{
            isHeart="N";
        }

        Reference ref;

        try {
            ref = referenceRepository.findByReferenceIdxAndStatus(referenceIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_REFERENCEIDX_AND_STATUS);
        }

        String referenceUrl = ref.getReferenceUrl();

        return new GetReferenceRes(referenceIdx,referenceUrl,isHeart);
    }



    /**
     * 레퍼런스 인덱스 존재여부
     * @param referenceIdx
     * @return Boolean
     * @throws BaseException
     */
    public Boolean existReference(Integer referenceIdx) throws BaseException {
        Boolean existReference;
        try {
            existReference = referenceRepository.existsByReferenceIdxAndStatus(referenceIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_REFERENCEIDX_AND_STATUS);
        }


        return existReference;
    }

    /**
     * Idx로 레퍼런스 조회
     *
     * @param referenceIdx
     * @return Reference
     * @throws BaseException
     */
    public Reference retrieveReferenceByReferenceIdx(Integer referenceIdx) throws BaseException {
        Reference reference;

        try {
            reference = referenceRepository.findById(referenceIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_ID);
        }

        if (reference == null || !reference.getStatus().equals("ACTIVE")) {
            throw new BaseException(NOT_FOUND_REFERENCE);
        }

        return reference;
    }




}