package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectCategory;
import com.example.ged.src.project.models.ProjectJob;
import com.example.ged.src.project.models.dto.PatchProjectReq;
import com.example.ged.src.project.models.dto.PostProjectReq;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserInfoProvider userInfoProvider;
    private final ProjectJobRepository projectJobRepository;
    private final ProjectCategoryRepository projectCategoryRepository;

    /**
     * 프로젝트 등록 API
     * @param postProjectReq
     * @param userIdx
     * @throws BaseException
     */
    @Transactional
    public void postProject(PostProjectReq postProjectReq,Integer userIdx) throws BaseException{
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);

        String projectName = postProjectReq.getProjectName();
        String projectThumbNailImgUrl = postProjectReq.getProjectThumbnailImageUrl();
        String projectImgUrl1 = postProjectReq.getProjectImageUrl1();
        String projectImgUrl2 = postProjectReq.getProjectImageUrl2();
        String projectImgUrl3 = postProjectReq.getProjectImageUrl3();
        String projectDescription1 = postProjectReq.getProjectDescription1();
        String projectDescription2 = postProjectReq.getProjectDescription2();
        String projectDescription3 = postProjectReq.getProjectDescription3();
        String applyKakaoLinkUrl = postProjectReq.getApplyKakaoLinkUrl();
        String applyGoogleFoamUrl = postProjectReq.getApplyGoogleFoamUrl();

        //프로젝트 저장
        Project project = new Project(userInfo,projectName,projectThumbNailImgUrl,projectImgUrl1,projectImgUrl2,projectImgUrl3,projectDescription1,projectDescription2,projectDescription3,applyKakaoLinkUrl,applyGoogleFoamUrl,0);

        try{
            projectRepository.save(project);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_PROJECT);
        }

        //프로젝트 카테고리 저장
        List<String> projectCategoryNameList = postProjectReq.getProjectCategoryNameList();
        for(String projectCategoryName : projectCategoryNameList){
            ProjectCategory projectCategory = new ProjectCategory(project,projectCategoryName);
            projectCategoryRepository.save(projectCategory);
        }

        //프로젝트 직군 저장
        List<String> projectJobNameList = postProjectReq.getProjectJobNameList();
        for(String projectJobName : projectJobNameList){
            ProjectJob projectJob = new ProjectJob(project,projectJobName);
            projectJobRepository.save(projectJob);
        }

    }

    /**
     * 프로젝트 삭제
     * @param userIdx
     * @param projectIdx
     * @throws BaseException
     */
    @Transactional
    public void deleteProject(Integer userIdx, Integer projectIdx) throws BaseException{
        Project project = projectRepository.findProjectByProjectIdxAndStatus(projectIdx,"ACTIVE");
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(project == null){
            throw new BaseException(FAILED_TO_GET_PROJECT);
        }
        if(userInfo!=project.getUserInfo()){
            throw new BaseException(NOT_YOUR_PROJECT);
        }
        project.setStatus("INACTIVE");
        try{
            projectRepository.save(project);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_PROJECT);//todo failed to delete project 로 수정
        }
    }

    /**
     * 프로젝트 수정
     * @param userIdx
     * @param projectIdx
     * @param patchProjectReq
     * @throws BaseException
     */
    @Transactional
    public void updateProject(Integer userIdx, Integer projectIdx, PatchProjectReq patchProjectReq) throws BaseException{
        Project project = projectRepository.findProjectByProjectIdxAndStatus(projectIdx,"ACTIVE");
        if(project == null){
            throw new BaseException(FAILED_TO_GET_PROJECT);
        }
        if(userIdx!=project.getUserInfo().getUserIdx()){
            throw new BaseException(NOT_YOUR_PROJECT);
        }

        /**
         * 기존에 선택했던 프로젝트 카테고리 제거
         */
        List<ProjectCategory> projectCategoryList = projectCategoryRepository.findAllByProject(project);
        for(ProjectCategory projectCategory : projectCategoryList){
            projectCategoryRepository.delete(projectCategory);
        }

        /**
         * 기존에 선택했던 프로젝트 모집 직군 제거
         */
        List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
        for(ProjectJob projectJob : projectJobList){
            projectJobRepository.delete(projectJob);
        }

        /**
         * 새롭게 입력받은 프로젝트 카테고리 저장
         */
        for(String categoryName : patchProjectReq.getProjectCategoryNameList()){
            ProjectCategory projectCategory = new ProjectCategory(project, categoryName);
            projectCategoryRepository.save(projectCategory);
        }

        /**
         * 새롭게 입력받은 프로젝트 모집 직군 저장
         */
        for(String jobName : patchProjectReq.getProjectJobNameList()){
            ProjectJob projectJob = new ProjectJob(project, jobName);
            projectJobRepository.save(projectJob);
        }

        project.setProjectName(patchProjectReq.getProjectName());
        project.setProjectThumbnailImageUrl(patchProjectReq.getProjectThumbnailImageUrl());
        project.setProjectImageUrl1(patchProjectReq.getProjectImageUrl1());
        project.setProjectDescription1(project.getProjectDescription1());
        project.setProjectImageUrl2(patchProjectReq.getProjectImageUrl2());
        project.setProjectDescription2(patchProjectReq.getProjectDescription2());
        project.setProjectImageUrl3(patchProjectReq.getProjectImageUrl3());
        project.setProjectDescription3(patchProjectReq.getProjectDescription3());
        project.setApplyKakaoLinkUrl(patchProjectReq.getApplyKakaoLinkUrl());
        project.setApplyGoogleFoamUrl(patchProjectReq.getApplyGoogleFoamUrl());

        try{
            projectRepository.save(project);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_PROJECT);
        }

    }

    /**
     * 프로젝트 모집 마감하기
     * @param userIdx
     * @param projectIdx
     * @throws BaseException
     */
    @Transactional
    public void finishProjectApply(Integer userIdx, Integer projectIdx) throws BaseException{
        Project project = projectRepository.findProjectByProjectIdxAndStatus(projectIdx,"ACTIVE");
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(project == null){
            throw new BaseException(FAILED_TO_GET_PROJECT);
        }
        if(userInfo!=project.getUserInfo()){
            throw new BaseException(NOT_YOUR_PROJECT);
        }
        project.setProjectStatus(1);
        try{
            projectRepository.save(project);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_PROJECT); //todo failed to change projectStatus 로 수정
        }
    }




}
