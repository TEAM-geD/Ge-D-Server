package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectCategory;
import com.example.ged.src.project.models.ProjectJob;
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

}
