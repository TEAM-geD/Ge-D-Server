package com.example.ged.src.referenceCategory;

import com.example.ged.config.BaseException;
import com.example.ged.src.reference.ReferenceRepository;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ged.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class ReferenceCategoryProvider {
    private final ReferenceCategoryRepository referenceCategoryRepository;

    /**
     * Idx로 레퍼런스 카테고리 조회
     *
     * @param referenceCategoryIdx
     * @return ReferenceCategory
     * @throws BaseException
     */
    public ReferenceCategory retrieveReferenceCategoryByReferenceCategoryIdx(Integer referenceCategoryIdx) throws BaseException {
        ReferenceCategory referenceCategory;

        try {
            referenceCategory = referenceCategoryRepository.findById(referenceCategoryIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_ID);
        }

        if (referenceCategory == null || !referenceCategory.getStatus().equals("ACTIVE")) {
            throw new BaseException(NOT_FOUND_REFERENCE_CATEGORY);
        }

        return referenceCategory;
    }
}
