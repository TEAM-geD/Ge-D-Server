package com.example.ged.src.projectHeart;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectHeart.models.ProjectHeart;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectHeartProvider {

    private final UserInfoProvider userInfoProvider;
    private final ProjectProvider projectProvider;
    private final ProjectHeartRepository projectHeartRepository;

    /**
     * 프로젝트 찜 존배여부
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
}
