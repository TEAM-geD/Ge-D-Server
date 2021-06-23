package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.config.BaseResponseStatus;
import com.example.ged.src.project.models.dto.GetProjectsRes;
import com.example.ged.src.project.models.dto.PostProjectReq;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        //프로젝트 카테고리가 AOS, IOS, WEB 이 아닌 경우
        if(!postProjectReq.getProjectCategoryNameList().isEmpty()){
            for(int i=0;i<postProjectReq.getProjectCategoryNameList().size();i++){
                if(!postProjectReq.getProjectCategoryNameList().get(i).equals("AOS") || !postProjectReq.getProjectCategoryNameList().get(i).equals("IOS") || !postProjectReq.getProjectCategoryNameList().get(i).equals("WEB")){
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
        //프로젝트 모집 직군이 "기획자", "개발자", "디자이너" 가 아닌 경우
        if(!postProjectReq.getProjectJobNameList().isEmpty()){
            for(int i=0;i<postProjectReq.getProjectJobNameList().size();i++){
                if(!postProjectReq.getProjectJobNameList().get(i).equals("기획자") || !postProjectReq.getProjectJobNameList().get(i).equals("개발자") || !postProjectReq.getProjectJobNameList().get(i).equals("디자이너")){
                    return new BaseResponse<>(INVALID_PROJECT_JOB);
                }
            }
        }
        //프로젝트 썸네일이 누락된 경우
        if(postProjectReq.getProjectThumbNailImgUrl() == null || postProjectReq.getProjectThumbNailImgUrl().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_THUMBNAIL_URL);
        }
        //프로젝트 이미지 1 을 입력하지 않은 경우
        if(postProjectReq.getProjectImgUrl1() == null || postProjectReq.getProjectImgUrl1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_1);
        }
        //프로젝트 설명 1 을 입력하지 않은 경우
        if(postProjectReq.getProjectDescription1() == null || postProjectReq.getProjectDescription1().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_1);
        }
        //프로젝트 이미지 2 을 입력하지 않은 경우
        if(postProjectReq.getProjectImgUrl2() == null || postProjectReq.getProjectImgUrl2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_IMAGE_2);
        }
        //프로젝트 설명 2 을 입력하지 않은 경우
        if(postProjectReq.getProjectDescription2() == null || postProjectReq.getProjectDescription2().length()==0){
            return new BaseResponse<>(EMPTY_PROJECT_DESCRIPTION_2);
        }
        //프로젝트 이미지 3 을 입력하지 않은 경우
        if(postProjectReq.getProjectImgUrl3() == null || postProjectReq.getProjectImgUrl3().length()==0){
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

    @ResponseBody
    @GetMapping("/projects")
    @Operation(summary = "프로젝트 리스트 조회 API")
    public BaseResponse<List<GetProjectsRes>> getProjects(@RequestParam(value="type",required = true)String type){
        if(!type.equals("ALL") || !type.equals("AOS") || !type.equals("IOS") || !type.equals("WEB")){
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

}
