package com.example.ged.src.referenceHeart;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.reference.ReferenceProvider;
import com.example.ged.src.referenceHeart.models.GetReferencesHeartRes;
import com.example.ged.src.referenceHeart.models.PostReferencesHeartReq;
import com.example.ged.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReferenceHeartController {
    private final JwtService jwtService;
    private final ReferenceProvider referenceProvider;
    private final ReferenceHeartProvider referenceHeartProvider;
    private final ReferenceHeartService referenceHeartService;

//    /**
//     * 레퍼런스 찜하기(취소) API
//     * [POST] /references/heart
//     * @return BaseResponse<Void>
//     * @RequestBody postReferencesHeartReq
//     */
//    @PostMapping("/heart")
//    public BaseResponse<Void> postReferencesHeart(@RequestBody PostReferencesHeartReq postReferencesHeartReq) throws BaseException {
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//        Integer referenceIdx = postReferencesHeartReq.getReferenceIdx();
//
//
//        if (referenceIdx == null || referenceIdx <= 0) {
//            return new BaseResponse<>(EMPTY_REFERENCEIDX);
//        }
//
//        Boolean existReference = referenceProvider.existReference(referenceIdx);
//        if (!existReference){
//            return new BaseResponse<>(INVALID_REFERENCEIDX);
//        }
//
//        Boolean existReferenceHeart = referenceHeartProvider.existReferenceHeart(jwtUserIdx,referenceIdx);
//
//        try {
//            if (existReferenceHeart){
//                referenceHeartService.deleteReferenceHeart(jwtUserIdx,referenceIdx);
//            }
//            else{
//                referenceHeartService.createReferenceHeart(jwtUserIdx,referenceIdx);
//            }
//            return new BaseResponse<>(SUCCESS);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//    /**
//     * 레퍼런스 찜한 내역 조회 API
//     * [GET] /references/heart
//     */
//    @GetMapping("/heart")
//    public BaseResponse<Void> getTest()  {
//        System.out.println("test");
//        return new BaseResponse<>(SUCCESS);
//
//    }
//    /**
//     * 레퍼런스 찜한 내역 조회 API
//     * [GET] /references/heart
//     * @return BaseResponse<List<GetReferencesHeartRes>>
//     */
//    @ResponseBody
//    @GetMapping("/final")
//    public BaseResponse<List<GetReferencesHeartRes>> getReferencesHeart() throws BaseException {
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//        try {
//            List<GetReferencesHeartRes> getReferencesHeartResList = referenceHeartProvider.retrieveReferenceHeart(jwtUserIdx);
//
//            return new BaseResponse<>(SUCCESS,getReferencesHeartResList);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

}
