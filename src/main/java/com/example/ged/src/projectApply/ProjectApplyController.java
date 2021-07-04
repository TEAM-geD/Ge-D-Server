package com.example.ged.src.projectApply;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.projectApply.models.dto.GetProjectMembersRes;
import com.example.ged.src.projectApply.models.dto.PostProjectApplyReq;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.ged.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProjectApplyController {

    private final JwtService jwtService;
    private final ProjectApplyService projectApplyService;
    private final ProjectApplyProvider projectApplyProvider;

    /**
     * 2021-07-04
     * 프로젝트 참여 신청하기 API
     * @param postProjectApplyReq
     * @return
     */
    @PostMapping("/projects/join")
    @ResponseBody
    @Operation(summary = "프로젝트 참여 신청하기 API")
    public BaseResponse<String> postProjectApply(@RequestBody PostProjectApplyReq postProjectApplyReq){
        try{
            Integer userIdx = jwtService.getUserIdx();
            projectApplyService.postProjectApply(userIdx,postProjectApplyReq);
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

}
