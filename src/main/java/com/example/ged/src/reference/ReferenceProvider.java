package com.example.ged.src.reference;

import com.example.ged.config.BaseException;
import com.example.ged.src.reference.models.GetReferencesRes;
import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceCategory.ReferenceCategoryProvider;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.ged.config.BaseResponseStatus.FAILED_TO_FIND_BY_REFERENCE_CATEGORY_AND_STATUS;

@Service
@RequiredArgsConstructor
public class ReferenceProvider {
    private final ReferenceRepository referenceRepository;
    private final ReferenceCategoryProvider referenceCategoryProvider;

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
            String referenceUrl = referenceList.get(i).getReferenceUrl();

            GetReferencesRes getReferencesRes = new GetReferencesRes(referenceCategoryIdx, referenceIdx, referenceThumbnail, referenceAuthor, referenceAuthorJob, referenceUrl);
            getReferencesResList.add(getReferencesRes);

        }
        return getReferencesResList;
    }




}