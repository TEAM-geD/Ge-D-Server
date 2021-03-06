package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.config.BaseResponseStatus;
import com.example.ged.src.project.models.dto.*;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {
    private final JwtService jwtService;
    private final ProjectService projectService;
    private final ProjectProvider projectProvider;

    /**
     * 2021-06-23
     * 프로젝트 추가 API
     * @param postProjectReq
     * @return
     */
    @ResponseBody
    @PostMapping("/projects")
    @Operation(summary = "프로젝트 추가 API")
    public BaseResponse<String> postProject(@RequestBody PostProjectReq postProjectReq){

        //프로젝트 카테고리를 아무것도 선택하지 않은 경우
        if(postProjectReq.getProjectCategoryNameList().size()==0 || postProjectReq.getProjectCategoryNameList().isEmpty()){
            return new BaseResponse<>(EMPTY_PROJECT_CATEGORY);
        }
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("AOS");
        categoryList.add("IOS");
        categoryList.add("WEB");

        //프로젝트 카테고리가 AOS, IOS, WEB 이 아닌 경우
        if(!postProjectReq.getProjectCategoryNameList().isEmpty()){
            for(int i=0;i<postProjectReq.getProjectCategoryNameList().size();i++){
                if(!categoryList.contains(postProjectReq.getProjectCategoryNameList().get(i))){
                    return new BaseResponse<>(INVALID_PROJECT_CATEGORY);
                }
            }
        }
        //프로젝트명을 입력하지 않은 경우
        if(postProjectReq.getProjectName()==null || postProjectReq.getProjectName().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_NAME);
        }
        //프로젝트 모집 직군을 아무것도 선택하지 않은 경우
        if(postProjectReq.getProjectJobNameList().size()==0 || postProjectReq.getProjectJobNameList().isEmpty()){
            return new BaseResponse<>(EMPTY_PROJECT_JOB);
        }

        ArrayList<String> jobList = new ArrayList<>();
        jobList.add("기획자");
        jobList.add("개발자");
        jobList.add("디자이너");
        //프로젝트 모집 직군이 "기획자", "개발자", "디자이너" 가 아닌 경우
        if(!postProjectReq.getProjectJobNameList().isEmpty()){
            for(int i=0;i<postProjectReq.getProjectJobNameList().size();i++){
                if(!jobList.contains(postProjectReq.getProjectJobNameList().get(i))){
                    return new BaseResponse<>(INVALID_PROJECT_JOB);
                }
            }
        }
        //프로젝트 썸네일이 누락된 경우
        if(postProjectReq.getProjectThumbnailImageUrl() == null || postProjectReq.getProjectThumbnailImageUrl().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_THUMBNAIL_URL);
        }
        //프로젝트 이미지 1 을 입력하지 않은 경우
        if(postProjectReq.getProjectImageUrl1() == null || postProjectReq.getProjectImageUrl1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_1);
        }
        //프로젝트 설명 1 을 입력하지 않은 경우
        if(postProjectReq.getProjectDescription1() == null || postProjectReq.getProjectDescription1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_1);
        }
        //프로젝트 이미지 2 을 입력하지 않은 경우
        if(postProjectReq.getProjectImageUrl2() == null || postProjectReq.getProjectImageUrl2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_2);
        }
        //프로젝트 설명 2 을 입력하지 않은 경우
        if(postProjectReq.getProjectDescription2() == null || postProjectReq.getProjectDescription2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_2);
        }
        //프로젝트 이미지 3 을 입력하지 않은 경우
        if(postProjectReq.getProjectImageUrl3() == null || postProjectReq.getProjectImageUrl3().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_3);
        }
        //프로젝트 설명 3 을 입력하지 않은 경우
        if(postProjectReq.getProjectDescription3() == null || postProjectReq.getProjectDescription3().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_3);
        }
        //카카오톡 신청 URL 둘 중 하나도 입력하지 않은 경우
        if(postProjectReq.getApplyKakaoLinkUrl() == null && postProjectReq.getApplyKakaoLinkUrl().length() == 0){
            return new BaseResponse<>(EMPTY_APPLY_KAKAO_LINK_URL);
        }
        //구글 신청 폼을 입력하지 않은 경우
        if(postProjectReq.getApplyGoogleFoamUrl() == null || postProjectReq.getApplyGoogleFoamUrl().length() == 0){
            return new BaseResponse<>(EMPTY_APPLY_GOOGLE_FOAM_URL);
        }
        try{
            Integer userIdx = jwtService.getUserIdx();
            projectService.postProject(postProjectReq,userIdx);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-06-24
     * 프로젝트 리스트 조회 API
     * @param type
     * @return
     */
    @ResponseBody
    @GetMapping("/projects")
    @Operation(summary = "프로젝트 리스트 조회 API")
    public BaseResponse<List<GetProjectsRes>> getProjects(@RequestParam(value="type",required = true)String type){
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("ALL");
        categoryList.add("AOS");
        categoryList.add("IOS");
        categoryList.add("WEB");

        if(!categoryList.contains(type)){
            return new BaseResponse<>(INVALID_PROJECT_CATEGORY);
        }
        try{
            Integer userIdx = jwtService.getUserIdx();
            List<GetProjectsRes> getProjectsResList = projectProvider.getProjects(type);
            return new BaseResponse<>(SUCCESS,getProjectsResList);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-06-24
     * 프로젝트 상세 조회 API
     * @param projectIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/projects/{projectIdx}")
    @Operation(summary = "프로젝트 상세 조회 API")
    public BaseResponse<GetProjectRes> getProject(@PathVariable(required = true,value = "projectIdx") Integer projectIdx){
        try{
            Integer userIdx = jwtService.getUserIdx();
            GetProjectRes getProjectRes = projectProvider.getProject(userIdx,projectIdx);
            return new BaseResponse<>(SUCCESS,getProjectRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-06-24
     * 프로젝트 삭제 API
     * @param projectIdx
     * @return
     */
    @ResponseBody
    @PatchMapping("/projects/{projectIdx}/status")
    @Operation(summary = "프로젝트 삭제 API")
    public BaseResponse<String> deleteProject(@PathVariable(required = true,value = "projectIdx") Integer projectIdx){
        try{
            Integer userIdx=  jwtService.getUserIdx();
            projectService.deleteProject(userIdx,projectIdx);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-06-24
     * 프로젝트 수정 API
     * @param projectIdx
     * @return
     */
    @ResponseBody
    @PatchMapping("/projects/{projectIdx}")
    @Operation(summary = "프로젝트 수정 API")
    public BaseResponse<String> updateProject(@PathVariable(required = true,value = "projectIdx")Integer projectIdx,
                                              @RequestBody PatchProjectReq patchProjectReq){

        //프로젝트 카테고리를 아무것도 선택하지 않은 경우
        if(patchProjectReq.getProjectCategoryNameList().size()==0 || patchProjectReq.getProjectCategoryNameList().isEmpty()){
            return new BaseResponse<>(EMPTY_PROJECT_CATEGORY);
        }
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("AOS");
        categoryList.add("IOS");
        categoryList.add("WEB");

        //프로젝트 카테고리가 AOS, IOS, WEB 이 아닌 경우
        if(!patchProjectReq.getProjectCategoryNameList().isEmpty()){
            for(int i=0;i<patchProjectReq.getProjectCategoryNameList().size();i++){
                if(!categoryList.contains(patchProjectReq.getProjectCategoryNameList().get(i))){
                    return new BaseResponse<>(INVALID_PROJECT_CATEGORY);
                }
            }
        }
        //프로젝트명을 입력하지 않은 경우
        if(patchProjectReq.getProjectName()==null || patchProjectReq.getProjectName().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_NAME);
        }
        //프로젝트 모집 직군을 아무것도 선택하지 않은 경우
        if(patchProjectReq.getProjectJobNameList().size()==0 || patchProjectReq.getProjectJobNameList().isEmpty()){
            return new BaseResponse<>(EMPTY_PROJECT_JOB);
        }

        ArrayList<String> jobList = new ArrayList<>();
        jobList.add("기획자");
        jobList.add("개발자");
        jobList.add("디자이너");
        //프로젝트 모집 직군이 "기획자", "개발자", "디자이너" 가 아닌 경우
        if(!patchProjectReq.getProjectJobNameList().isEmpty()){
            for(int i=0;i<patchProjectReq.getProjectJobNameList().size();i++){
                if(!jobList.contains(patchProjectReq.getProjectJobNameList().get(i))){
                    return new BaseResponse<>(INVALID_PROJECT_JOB);
                }
            }
        }
        //프로젝트 썸네일이 누락된 경우
        if(patchProjectReq.getProjectThumbnailImageUrl() == null || patchProjectReq.getProjectThumbnailImageUrl().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_THUMBNAIL_URL);
        }
        //프로젝트 이미지 1 을 입력하지 않은 경우
        if(patchProjectReq.getProjectImageUrl1() == null || patchProjectReq.getProjectImageUrl1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_1);
        }
        //프로젝트 설명 1 을 입력하지 않은 경우
        if(patchProjectReq.getProjectDescription1() == null || patchProjectReq.getProjectDescription1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_1);
        }
        //프로젝트 이미지 2 을 입력하지 않은 경우
        if(patchProjectReq.getProjectImageUrl2() == null || patchProjectReq.getProjectImageUrl2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_2);
        }
        //프로젝트 설명 2 을 입력하지 않은 경우
        if(patchProjectReq.getProjectDescription2() == null || patchProjectReq.getProjectDescription2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_2);
        }
        //프로젝트 이미지 3 을 입력하지 않은 경우
        if(patchProjectReq.getProjectImageUrl3() == null || patchProjectReq.getProjectImageUrl3().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_3);
        }
        //프로젝트 설명 3 을 입력하지 않은 경우
        if(patchProjectReq.getProjectDescription3() == null || patchProjectReq.getProjectDescription3().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_3);
        }
        //카카오톡 신청 URL 둘 중 하나도 입력하지 않은 경우
        if(patchProjectReq.getApplyKakaoLinkUrl() == null && patchProjectReq.getApplyKakaoLinkUrl().length() == 0){
            return new BaseResponse<>(EMPTY_APPLY_KAKAO_LINK_URL);
        }
        //구글 신청 폼을 입력하지 않은 경우
        if(patchProjectReq.getApplyGoogleFoamUrl() == null || patchProjectReq.getApplyGoogleFoamUrl().length() == 0){
            return new BaseResponse<>(EMPTY_APPLY_GOOGLE_FOAM_URL);
        }

        try{
            Integer userIdx = jwtService.getUserIdx();
            projectService.updateProject(userIdx,projectIdx,patchProjectReq);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-07-11
     * 프로젝트 모집 마감하기 API"
     * @param projectIdx
     * @return
     */
    @ResponseBody
    @PatchMapping("/projects/{projectIdx}/projectStatus")
    @Operation(summary = "프로젝트 모집 마감하기 API")
    public BaseResponse<String> finishProjectApply(@PathVariable(required = true,value = "projectIdx") Integer projectIdx){
        try{
            Integer userIdx=  jwtService.getUserIdx();
            projectService.finishProjectApply(userIdx,projectIdx);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-07-11
     * 프로젝트 결과물 등록하기 API
     * @param projectIdx
     * @param postProjectResultReq
     * @return
     */
    @ResponseBody
    @PostMapping("/projects/{projectIdx}/result")
    @Operation(summary = "프로젝트 결과물 등록하기 API")
    public BaseResponse<String> postProjectResult(@PathVariable(required = true,value = "projectIdx")Integer projectIdx,
                                                  @RequestBody PostProjectResultReq postProjectResultReq){
        if(postProjectResultReq.getProjectResultUrl() == null || postProjectResultReq.getProjectResultUrl().length() == 0){
            return new BaseResponse<>(EMPTY_PROJECT_RESULT_URL);
        }
        try{
            Integer userIdx=  jwtService.getUserIdx();
            projectService.postProjectResult(userIdx,projectIdx,postProjectResultReq);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
