package com.example.ged.src.reference;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.reference.models.GetReferenceRes;
import com.example.ged.src.reference.models.GetReferencesRes;
import com.example.ged.src.referenceHeart.ReferenceHeartProvider;
import com.example.ged.src.referenceHeart.models.GetReferencesHeartRes;
import com.example.ged.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/references")
public class ReferenceController {
    private final JwtService jwtService;
    private final ReferenceProvider referenceProvider;

    /**
     * 레퍼런스 리스트 조회 API
     * [GET] /references?type=
     * @RequestParam type
     * @return BaseResponse<List<GetReferencesRes>>
     */
    @GetMapping("")
    public BaseResponse<List<GetReferencesRes>> getReferences(@RequestParam Integer type) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if (type== null || type <= 0) {
            return new BaseResponse<>(EMPTY_TYPE);
        }

        ArrayList<Integer> typeList = new ArrayList<Integer>();

        typeList.add(1);
        typeList.add(2);
        typeList.add(3);
        typeList.add(4);

        if (!typeList.contains(type)) {
            return new BaseResponse<>(INVALID_TYPE);
        }

        try {
            List<GetReferencesRes> getReferencesResList = referenceProvider.retrieveReferences(type);
            return new BaseResponse<>(SUCCESS,getReferencesResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 레퍼런스 상세 조회 API
     * [GET] /references/:referenceIdx
     * @PathVariable referenceIdx
     * @return BaseResponse<GetReferenceRes>
     */
    @GetMapping("/{referenceIdx}")
    public BaseResponse<GetReferenceRes> getReference(@PathVariable Integer referenceIdx) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if (referenceIdx == null || referenceIdx <= 0) {
            return new BaseResponse<>(EMPTY_REFERENCEIDX);
        }

        Boolean existReference = referenceProvider.existReference(referenceIdx);
        if (!existReference){
            return new BaseResponse<>(INVALID_REFERENCEIDX);
        }


        try {
            GetReferenceRes getReferenceRes = referenceProvider.retrieveReference(referenceIdx);
            return new BaseResponse<>(SUCCESS,getReferenceRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}