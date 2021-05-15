package com.example.ged.src.category;

import com.example.ged.config.BaseException;
import com.example.ged.config.BaseResponse;
import com.example.ged.config.BaseResponseStatus;
import com.example.ged.src.category.models.dto.GetUserJobCategoryRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.ged.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserJobCategoryController {
    private final UserJobCategoryProvider userJobCategoryProvider;
    /**
     * 2021-05-15
     * 직군 카테고리 전체 조회 API
     * [GET] /job-categories
     * @return
     */
    @ResponseBody
    @GetMapping("/job-categories")
    @Operation(summary = "직군 카테고리 전체 조회 API")
    public BaseResponse<List<GetUserJobCategoryRes>> getUserJobCategory(){
        try{
            List<GetUserJobCategoryRes> getUserJobCategoryResList = userJobCategoryProvider.getUserJobCategoryResList();
            return new BaseResponse<>(SUCCESS,getUserJobCategoryResList);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
