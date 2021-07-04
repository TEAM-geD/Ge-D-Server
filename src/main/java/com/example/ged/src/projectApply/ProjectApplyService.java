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

        if(project.getProjectStatus()!=0){
            throw new BaseException(EXPIRED_PROJECT_APPLY);
        }

        if(projectApplyRepository.findAllByProjectAndUserInfoAndStatus(project,userInfo,"ACTIVE") != null){
            throw new BaseException(ALREADY_POST_PROJECT_APPLY);
        }

        ProjectApply projectApply = new ProjectApply(userInfo,project,"WAITING");

        //todo : FCM 알림 넣기
        try{
            projectApplyRepository.save(projectApply);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_PROJECT_APPLY);
        }
    }

    /**
     * 프로젝트 참여 신청 취소하기
     * @param userIdx
     * @param postProjectApplyReq
     * @throws BaseException
     */
    @Transactional
    public void deleteProjectApply(Integer userIdx, PostProjectApplyReq postProjectApplyReq) throws BaseException{
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        Project project = projectProvider.retrieveProjectByProjectIdx(postProjectApplyReq.getProjectIdx());

        ProjectApply projectApply = projectApplyRepository.findAllByProjectAndUserInfoAndStatus(project,userInfo,"ACTIVE");
        if(projectApply == null){
            throw new BaseException(DID_NOT_APPLY_PROJECT_YET);
        }
        try{
            projectApplyRepository.delete(projectApply);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_DELETE_PROJECT_APPLY);
        }
    }
}
