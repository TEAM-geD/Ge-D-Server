package com.example.ged.src.category;

import com.example.ged.config.BaseException;
import com.example.ged.src.category.models.UserJobCategory;
import com.example.ged.src.category.models.dto.GetUserJobCategoryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ged.config.BaseResponseStatus.FAILED_TO_GET_USER_JOB_CATEGORIES;

@Service
@RequiredArgsConstructor
public class UserJobCategoryProvider {
    private final UserJobCategoryRepository userJobCategoryRepository;
    /**
     * 유저 직군 전체 조회
     * @return
     * @throws BaseException
     */
    public List<GetUserJobCategoryRes> getUserJobCategoryResList() throws BaseException{
        List<UserJobCategory> userJobCategories;
        try{
            userJobCategories = userJobCategoryRepository.findAllByStatus("ACTIVE");
        }catch(Exception ignored){
            throw new BaseException(FAILED_TO_GET_USER_JOB_CATEGORIES);
        }
        return userJobCategories.stream().map(userJobCategory -> {

            Integer userJobCategoryIdx = userJobCategory.getUserJobCategoryIdx();
            String jobName = userJobCategory.getJobName();
            return new GetUserJobCategoryRes(userJobCategoryIdx,jobName);

        }).collect(Collectors.toList());
    }


}
