package com.example.ged.src.projectApply;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.project.ProjectProvider;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.projectApply.models.dto.GetProjectMembersRes;
import com.example.ged.src.projectApply.models.dto.PatchProjectMemberReq;
import com.example.ged.src.projectApply.models.dto.PostProjectApplyReq;
import com.example.ged.src.user.UserInfoProvider;
import com.example.ged.src.user.models.UserInfo;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static com.example.ged.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProjectApplyController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final JwtService jwtService;
    private final ProjectApplyService projectApplyService;
    private final ProjectApplyProvider projectApplyProvider;
    private final ProjectProvider projectProvider;
    private final UserInfoProvider userInfoProvider;

    /**
     * 푸시알림 API - test
     * [POST] /fcm-test
     * @return BaseResponse<Void>
     */
    @PostMapping("/fcm-test")
    public  BaseResponse<Void> posFcmTest() throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String targetToken = request.getHeader("FCM-TOKEN");
        String title = "title-test";
        String body = "body-test";
        System.out.println("targetToken: "+targetToken+", title: "+title+", body: "+body);

        firebaseCloudMessageService.sendMessageTo(targetToken,title,body);

        return new BaseResponse<>(SUCCESS);

    }

    /**
     * 2021-07-04
     * 프로젝트 참여 신청하기 API
     * @param postProjectApplyReq
     * @return
     */
    @PostMapping("/projects/join")
    @ResponseBody
    @Operation(summary = "프로젝트 참여 신청하기 API")
    @Transactional
    public BaseResponse<String> postProjectApply(@RequestBody PostProjectApplyReq postProjectApplyReq)  throws IOException {
        try{
            Integer userIdx = jwtService.getUserIdx();
            UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
            Project project = projectProvider.retrieveProjectByProjectIdx(postProjectApplyReq.getProjectIdx());

            String targetToken = project.getUserInfo().getDeviceToken();
            String title = "프로젝트 참여 신청";
            String body = userInfo.getUserName()+"님이 프로젝트 참여 신청하였습니다.";

            firebaseCloudMessageService.sendMessageTo(targetToken,title,body);
            projectApplyService.postProjectApply(userInfo,project);
            return new BaseResponse<>(SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-07-04
     * 프로젝트 참여 신청 취소하기 API
     * @param postProjectApplyReq
     * @return
     */
    @PostMapping("/projects/dis-join")
    @ResponseBody
    @Operation(summary = "프로젝트 참여 신청 취소하기 API")
    public BaseResponse<String> deleteProjectApply(@RequestBody PostProjectApplyReq postProjectApplyReq){
        try{
            Integer userIdx = jwtService.getUserIdx();
            projectApplyService.deleteProjectApply(userIdx,postProjectApplyReq);
            return new BaseResponse<>(SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2021-07-04
     * 프로젝트 참여 멤버 리스트 조회 API
     * @param projectIdx
     * @return
     */
    @GetMapping("/projects/{projectIdx}/members")
    @ResponseBody
    @Operation(summary = "프로젝트 참여 멤버 리스트 조회 API")
    public BaseResponse<GetProjectMembersRes> getProjectMember(@PathVariable Integer projectIdx){
        try{
            Integer userIdx = jwtService.getUserIdx();
            GetProjectMembersRes getProjectMembersRes = projectApplyProvider.getProjectMembers(projectIdx);
            return new BaseResponse<>(SUCCESS ,  getProjectMembersRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 2021-07-11
     * 프로젝트 참여 멤버 삭제하기 API
     * @param projectIdx
     * @param patchProjectMemberReq
     * @return
     */
    @PatchMapping("/projects/{projectIdx}/members")
    @ResponseBody
    @Operation(summary = "프로젝트 참여 멤버 제거하기 API")
    public BaseResponse<String> deleteProjectMember(@PathVariable Integer projectIdx,
                                                    @RequestBody PatchProjectMemberReq patchProjectMemberReq){
        try{
            Integer userIdx = jwtService.getUserIdx();
            projectApplyService.deleteProjectMember(projectIdx,userIdx,patchProjectMemberReq);
            return new BaseResponse<>(SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
