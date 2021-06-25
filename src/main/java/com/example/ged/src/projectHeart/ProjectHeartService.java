package com.example.ged.src.projectHeart;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectHeart.models.ProjectHeart;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectHeartService {

    private final UserInfoProvider userInfoProvider;
    private final ProjectProvider projectProvider;
    private final ProjectHeartRepository projectHeartRepository;

    /**
     * 프로젝트 찜하기 생성
     * @param userIdx
     * @param projectIdx
     * @throws BaseException
     */
    public void createProjectHeart(Integer userIdx,Integer projectIdx) throws BaseException{

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Project project = projectProvider.retrieveProjectByProjectIdx(projectIdx);

        ProjectHeart projectHeart = new ProjectHeart(userInfo,project);
        try{
            projectHeartRepository.save(projectHeart);
        }catch(Exception exception){
            throw new BaseException(FAILED_TO_SAVE_PROJECT_HEART);
        }
    }

    /**
     * 프로젝트 찜하기 취소
     * @param userIdx
     * @param projectIdx
     * @throws BaseException
     */
    public void deleteProjectHeart(Integer userIdx,Integer projectIdx) throws BaseException{

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Project project = projectProvider.retrieveProjectByProjectIdx(projectIdx);

        ProjectHeart projectHeart = projectHeartRepository.findAllByUserInfoAndProjectAndStatus(userInfo,project,"ACTIVE");
        projectHeart.setStatus("INACTIVE");

        try{
            projectHeartRepository.save(projectHeart);
        }catch(Exception exception){
            throw new BaseException(FAILED_TO_SAVE_PROJECT_HEART);
        }

    }
}
