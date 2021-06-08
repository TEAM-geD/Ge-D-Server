package com.example.ged.src.referenceHeart;

public class ReferenceHeartController {

    //    /**
//     * 레퍼런스 좋아요(취소) API
//     * [POST] /references/heart
//     * @return BaseResponse<void>
//     * @RequestBody postReferencesHeartReq
//     */
//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<Void> postScrapRecipe(@RequestBody PostReferencesHeartReq postReferencesHeartReq) throws BaseException {
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//        Refere
//
//
//        try {
////            Integer userIdx = jwtService.getUserIdx();
//
//            // 이미 발급 받은건지
//            ScrapPublic scrapPublic = null;
//            scrapPublic = scrapPublicProvider.retrieveScrapRecipe(parameters.getRecipeId(), userIdx);
//
//
//            PostScrapPublicRes postScrapPublicRes;
//            if (scrapPublic != null) {
//                postScrapPublicRes =  scrapPublicService.deleteScrapRecipe(parameters.getRecipeId(),userIdx);
//            }
//            else{
//                postScrapPublicRes = scrapPublicService.createScrapRecipe(parameters.getRecipeId(),userIdx);
//            }
//            return new BaseResponse<>(postScrapPublicRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//
//    }
}
