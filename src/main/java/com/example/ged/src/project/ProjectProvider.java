package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectJob;
import com.example.ged.src.project.models.dto.GetProjectRes;
import com.example.ged.src.project.models.dto.GetProjectsRes;
import com.example.ged.src.projectHeart.ProjectHeartProvider;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectProvider {

    private final ProjectRepository projectRepository;
    private final ProjectJobRepository projectJobRepository;
    private final UserInfoProvider userInfoProvider;
    private final ProjectHeartProvider projectHeartProvider;

    /**
     * 프로젝트 리스트 조회
     * @param type
     * @return
     * @throws BaseException
     */
    public List<GetProjectsRes> getProjects(String type) throws BaseException {

        List<GetProjectsRes> getProjectsResList = null;
        List<Project> projectList = null;
        if(type.equals("ALL")){
            projectList = projectRepository.findProjectByStatus("ACTIVE");
        }else{
            projectList = projectRepository.findProjectByProjectJobName(type,"ACTIVE");
        }
        for(Project project : projectList){
            List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
            List<String> projectJobNameList = null;

            for(ProjectJob projectJob : projectJobList){
                projectJobNameList.add(projectJob.getProjectJobName());
            }

            GetProjectsRes getProjectsRes = new GetProjectsRes(project.getProjectIdx(),project.getProjectThumbnailImageUrl(),project.getProjectName(),projectJobNameList,project.getUserInfo().getUserIdx(),project.getUserInfo().getUserName(),project.getUserInfo().getUserName());
            getProjectsResList.add(getProjectsRes);
        }
        return getProjectsResList;
    }

    /**
     * 프로젝트 상세조회
     * @param userIdx
     * @param projectIdx
     * @return
     * @throws BaseException
     */
    public GetProjectRes getProject(Integer userIdx,Integer projectIdx) throws BaseException{
        Project project = projectRepository.findProjectByProjectIdxAndStatus(projectIdx,"ACTIVE");
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);

        if(project == null){
            throw new BaseException(FAILED_TO_GET_PROJECT);
        }

        //찜하지 않은 경우 : 0, 찜한 경우 : 1
        Integer projectLikeStatus = 0;

        //이미 찜한 경우
        if(projectHeartProvider.existProjectHeart(userIdx,projectIdx)){
            projectLikeStatus = 1;
        }
        //찜하지 않은 경우
        else{
            projectLikeStatus = 0;
        }

        GetProjectRes getProjectRes = new GetProjectRes(project.getProjectIdx(),
                project.getProjectName(),
                project.getProjectThumbnailImageUrl(),
                project.getProjectImageUrl1(),
                project.getProjectDescription1(),
                project.getProjectImageUrl2(),
                project.getProjectDescription2(),
                project.getProjectImageUrl3(),
                project.getProjectDescription3(),
                project.getApplyKakaoLinkUrl(),
                project.getApplyGoogleFoamUrl(),
                projectLikeStatus,
                project.getProjectStatus());
        return getProjectRes;
    }

    public Project retrieveProjectByProjectIdx(Integer projectIdx) throws BaseException{
        Project project = projectRepository.findProjectByProjectIdxAndStatus(projectIdx,"ACTIVE");
        if(project == null){
            throw new BaseException(FAILED_TO_GET_PROJECT);
        }
        return project;
    }
}
