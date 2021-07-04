package com.example.ged.src.projectApply;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.projectApply.models.dto.PostProjectApplyReq;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectApplyService {
    private final UserInfoProvider userInfoProvider;
    private final ProjectProvider projectProvider;
    private final ProjectApplyRepository projectApplyRepository;

    /**
     * 프로젝트 참여 신청하기
     * @param userIdx
     * @param postProjectApplyReq
     * @throws BaseException
     */
    @Transactional
    public void postProjectApply(Integer userIdx, PostProjectApplyReq postProjectApplyReq) throws BaseException{
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Project project = projectProvider.retrieveProjectByProjectIdx(postProjectApplyReq.getProjectIdx());


        if(projectApplyRepository.findAllByProjectAndUserInfoAndStatus(project,userInfo,"ACTIVE") != null){
            throw new BaseException(ALREADY_POST_PROJECT_APPLY);
        }

        ProjectApply projectApply = new ProjectApply(userInfo,project,"APPLY");

        //todo : FCM 알림 넣기
        try{
            projectApplyRepository.save(projectApply);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_PROJECT_APPLY);
        }

    }
}
