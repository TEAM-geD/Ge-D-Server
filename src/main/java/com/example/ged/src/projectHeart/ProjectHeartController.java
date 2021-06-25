package com.example.ged.src.projectHeart;
import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.src.projectHeart.models.dto.PostProjectHeartReq;
import com.example.ged.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.ged.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProjectHeartController {

    private final ProjectHeartProvider projectHeartProvider;
    private final ProjectHeartService projectHeartService;
    private final JwtService jwtService;

    /**
     * 2021-06-25
     * 프로젝트 찜하기/취소 API
     * @param postProjectHeartReq
     * @return
     */
    @PostMapping("/projects/heart")
    @ResponseBody
    @Operation(summary = "프로젝트 찜하기/취소 API")
    public BaseResponse<String> postProjectHeart(@RequestBody PostProjectHeartReq postProjectHeartReq){

        try{
            Integer userIdx = jwtService.getUserIdx();
            Boolean existProjectHeart = projectHeartProvider.existProjectHeart(userIdx, postProjectHeartReq.getProjectIdx());

            //이미 찜한 내역이 존재할 경우
            if(existProjectHeart){
                projectHeartService.deleteProjectHeart(userIdx, postProjectHeartReq.getProjectIdx());
            }
            //아직 찜한 내역이 존재하지 않는 경우
            else{
                projectHeartService.createProjectHeart(userIdx, postProjectHeartReq.getProjectIdx());
            }
            return new BaseResponse<>(SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
}
