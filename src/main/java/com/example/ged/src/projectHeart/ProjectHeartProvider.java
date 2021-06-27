package com.example.ged.src.projectHeart;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectJobRepository;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectJob;
import com.example.ged.src.projectHeart.models.ProjectHeart;
import com.example.ged.src.projectHeart.models.dto.GetProjectsHeartRes;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectHeartProvider {

    private final UserInfoProvider userInfoProvider;
    private final ProjectProvider projectProvider;
    private final ProjectHeartRepository projectHeartRepository;
    private final ProjectJobRepository projectJobRepository;

    /**
     * 프로젝트 찜 존재여부
     * @param userIdx
     * @param projectIdx
     * @return
     * @throws BaseException
     */
    public Boolean existProjectHeart(Integer userIdx, Integer projectIdx) throws BaseException{
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Project project = projectProvider.retrieveProjectByProjectIdx(projectIdx);

        Boolean existProjectHeart = projectHeartRepository.existsByUserInfoAndProjectAndStatus(userInfo,project,"ACTIVE");
        return existProjectHeart;
    }

    /**
     * 프로젝트 찜한 내역 리스트 조회
     * @param userIdx
     * @return
     * @throws BaseException
     */
    public List<GetProjectsHeartRes> getProjectsHeartList(Integer userIdx) throws BaseException{
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        List<ProjectHeart> projectHeartList = projectHeartRepository.findAllByUserInfoAndStatus(userInfo,"ACTIVE");

        List<GetProjectsHeartRes> getProjectsHeartResList = new ArrayList<>();

        for(ProjectHeart projectHeart : projectHeartList){
            Integer projectIdx = projectHeart.getProject().getProjectIdx();
            String projectThumbnailImageUrl = projectHeart.getProject().getProjectThumbnailImageUrl();
            String projectName = projectHeart.getProject().getProjectName();

            List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(projectHeart.getProject());

            List<String> projectJobNameList = new ArrayList<>();

            for(ProjectJob projectJob : projectJobList){
                projectJobNameList.add(projectJob.getProjectJobName());
            }

            Integer projectUserIdx = projectHeart.getProject().getUserInfo().getUserIdx();
            String userName = projectHeart.getProject().getUserInfo().getUserName();
            String userJob = projectHeart.getProject().getUserInfo().getUserJob();
            String userProfileImageUrl = projectHeart.getProject().getUserInfo().getProfileImageUrl();

            GetProjectsHeartRes getProjectsHeartRes = new GetProjectsHeartRes(projectIdx,projectThumbnailImageUrl,projectName,projectJobNameList,projectUserIdx,userName,userJob,userProfileImageUrl);
            getProjectsHeartResList.add(getProjectsHeartRes);
        }
        return getProjectsHeartResList;
    }


}
