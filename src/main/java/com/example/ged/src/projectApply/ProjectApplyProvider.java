package com.example.ged.src.projectApply;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.projectApply.models.dto.GetProjectMemberInfoRes;
import com.example.ged.src.projectApply.models.dto.GetProjectMembersRes;
import com.example.ged.src.user.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectApplyProvider {

    private final ProjectProvider projectProvider;
    private final ProjectApplyRepository projectApplyRepository;

    /**
     * 프로젝트 참가한 사람들 조회
     * @param projectIdx
     * @return
     * @throws BaseException
     */
    public GetProjectMembersRes getProjectMembers(Integer projectIdx) throws BaseException{
        Project project = projectProvider.retrieveProjectByProjectIdx(projectIdx);

        List<ProjectApply> projectApplyList = projectApplyRepository.findAllByProjectAndApplyStatusAndStatus(project,"CONFIRMED","ACTIVE");

        List<GetProjectMemberInfoRes> getProjectMemberInfoResList = new ArrayList<>();
        for(ProjectApply projectApply : projectApplyList){
            Integer userIdx = projectApply.getUserInfo().getUserIdx();
            String userName = projectApply.getUserInfo().getUserName();
            String userJobName = projectApply.getUserInfo().getUserJob();
            GetProjectMemberInfoRes getProjectMemberInfoRes = new GetProjectMemberInfoRes(userIdx,userName,userJobName);
            getProjectMemberInfoResList.add(getProjectMemberInfoRes);
        }
        GetProjectMembersRes getProjectMembersRes = new GetProjectMembersRes(project.getUserInfo().getUserIdx(),
                project.getUserInfo().getUserName(),
                project.getUserInfo().getUserJob(),
                getProjectMemberInfoResList);

        return getProjectMembersRes;

    }
}
