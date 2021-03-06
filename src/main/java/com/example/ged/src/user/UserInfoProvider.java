package com.example.ged.src.user;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.ProjectJobRepository;
import com.example.ged.src.project.ProjectRepository;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectJob;
import com.example.ged.src.project.models.dto.GetProjectsRes;
import com.example.ged.src.projectApply.ProjectApplyRepository;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.user.models.GetMembersRes;
import com.example.ged.src.user.models.GetUserInfoRes;
import com.example.ged.src.user.models.UserInfo;
import com.example.ged.src.user.models.dto.GetMyInfoRes;
import com.example.ged.src.user.models.dto.GetMyProjectInfoRes;
import com.example.ged.src.user.models.dto.GetMyProjectsRes;
import com.example.ged.src.user.models.dto.GetProjectMemberProfile;
import com.example.ged.src.userSns.UserSnsRepository;
import com.example.ged.src.userSns.models.UserSns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.ged.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserInfoProvider {
    private final UserInfoRepository userInfoRepository;
    private final UserSnsRepository userSnsRepository;
    private final ProjectRepository projectRepository;
    private final ProjectJobRepository projectJobRepository;
    private final ProjectApplyRepository projectApplyRepository;
    /**
     * ์ ์ ์กฐํ
     * @return User
     * @throws BaseException
     */
    public UserInfo retrieveUserInfoBySocialId(String socialId) throws BaseException {

        UserInfo existsUserInfo =null;
        try {
            existsUserInfo = userInfoRepository.findBySocialIdAndStatus(socialId, "ACTIVE");

        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_SOCIALID_AND_STATUS);
        }

        return existsUserInfo;
    }

    /**
     * Idx๋ก ํ์ ์กฐํ
     * @param userIdx
     * @return User
     * @throws BaseException
     */
    public UserInfo retrieveUserByUserIdx(Integer userIdx) throws BaseException {
        // 1. DB์์ User ์กฐํ
        UserInfo userInfo;
        try {
            userInfo = userInfoRepository.findById(userIdx).orElse(null); // ์์ ?
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERIDX);
        }

        // 2. ์กด์ฌํ๋ ํ์์ธ์ง ํ์ธ
        if (userInfo == null || !userInfo.getStatus().equals("ACTIVE")) {
            throw new BaseException(INACTIVE_USER);
        }

        // 3. User๋ฅผ return
        return userInfo;
    }

    /**
     * ์ ์  ์ ๋ณด ์กฐํ API
     * @param userIdx
     * @return GetUserInfoRes
     * @throws BaseException
     */
    public GetUserInfoRes retrieveUserInfo(Integer userIdx) throws BaseException {
        UserInfo userInfo;
        try {
            userInfo = userInfoRepository.findByUserIdxAndStatus(userIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERIDX_AND_STATUS);
        }
        String userName = userInfo.getUserName();
        String introduce = userInfo.getIntroduce();
        String profileImageUrl = userInfo.getProfileImageUrl();
        String backgroundImageUrl = userInfo.getBackgroundImageUrl();
        String userJob = userInfo.getUserJob();
        String isMembers = userInfo.getIsMembers();

        UserInfo user = retrieveUserByUserIdx(userIdx);

        List<UserSns> userSnsList ;
        try{
            userSnsList = userSnsRepository.findByUserInfoAndStatus(user,"ACTIVE");
        }catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_STATUS);
        }

        List<String> userSnsUrlList = new ArrayList<>();
        for(int i=0;i<userSnsList.size();i++){
            userSnsUrlList.add(userSnsList.get(i).getSnsUrl());
        }


        return new GetUserInfoRes(userIdx,userName,introduce,profileImageUrl,backgroundImageUrl,userJob,isMembers,userSnsUrlList);
    }

    /**
     * ๋ฉค๋ฒ์ค ๋ฆฌ์คํธ ์กฐํ API
     * @param job
     * @return List<GetMembersRes>
     * @throws BaseException
     */
    public List<GetMembersRes> retrieveMembers(String job) throws BaseException {
        List<UserInfo> userInfoList ;

        try {
            userInfoList = userInfoRepository.findByUserJobAndIsMembersAndStatus(job,"Y","ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERJOB_AND_ISMEMBERS_AND_STATUS);
        }
        List<GetMembersRes> getMembersResList = new ArrayList<>();
        for(int i = 0 ; i < userInfoList.size() ; i++){
            Integer userIdx = userInfoList.get(i).getUserIdx();
            String userName = userInfoList.get(i).getUserName();
            String introduce = userInfoList.get(i).getIntroduce();
            String profileImageUrl = userInfoList.get(i).getProfileImageUrl();
            String backgroundImageUrl = userInfoList.get(i).getBackgroundImageUrl();
            String userJob = userInfoList.get(i).getUserJob();
            GetMembersRes getMembersRes = new GetMembersRes(userIdx, userName, introduce, profileImageUrl, backgroundImageUrl, userJob);
            getMembersResList.add(getMembersRes);

        }
        return  getMembersResList;
    }



    public GetMyInfoRes getMyInfoRes(Integer userIdx, Integer projectStatus) throws BaseException{
        UserInfo userInfo = retrieveUserByUserIdx(userIdx);

        List<UserSns> userSnsList = userSnsRepository.findByUserInfoAndStatus(userInfo,"ACTIVE"); ;

        List<String> userSnsUrlList = new ArrayList<>();
        for(int i=0;i<userSnsList.size();i++){
            userSnsUrlList.add(userSnsList.get(i).getSnsUrl());
        }

        /**
         * ๋ด๊ฐ ๋ง๋  ํ๋ก์ ํธ ๊ด๋ จ
         */
        List<GetMyProjectInfoRes> myProjectInfoList = new ArrayList<>();
        List<Project> myProjectList = projectRepository.findTop2ByUserInfoAndStatusOrderByProjectIdxDesc(userInfo,"ACTIVE");
        for(Project project : myProjectList){
            /**
             * ํ๋ก์ ํธ ์ง๊ตฐ ๊ฐ์ ธ์ค๊ธฐ
             */
            List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
            List<String> projectJobNameList = new ArrayList<>();

            for(ProjectJob projectJob : projectJobList){
                projectJobNameList.add(projectJob.getProjectJobName());
            }

            /**
             * ํ๋ก์ ํธ ์ฐธ์ฌํ ์ฌ๋๋ค ์ ๋ณด ๊ฐ์ ธ์ค๊ธฐ
             */
            List<GetProjectMemberProfile> projectTeamInfoList = new ArrayList<>();
            List<ProjectApply> projectApplyList = projectApplyRepository.findAllByProjectAndApplyStatusAndStatus(project,"CONFIRMED","ACTIVE");
            for(ProjectApply projectApply : projectApplyList){
                GetProjectMemberProfile getProjectMemberProfile = new GetProjectMemberProfile(projectApply.getUserInfo().getUserIdx(),
                        projectApply.getUserInfo().getProfileImageUrl());
                projectTeamInfoList.add(getProjectMemberProfile);
            }


            GetMyProjectInfoRes getProjectsRes  = new GetMyProjectInfoRes(project.getProjectIdx(),
                    project.getProjectThumbnailImageUrl(),
                    project.getProjectName(),
                    project.getProjectStatus(),
                    projectJobNameList,
                    project.getUserInfo().getUserIdx(),
                    project.getUserInfo().getUserName(),
                    project.getUserInfo().getUserJob(),
                    projectTeamInfoList);
            myProjectInfoList.add(getProjectsRes);
        }
        /**
         *  ๋ด๊ฐ ์ฐธ์ฌํ ํ๋ก์ ํธ ๊ด๋ จ
         */
        List<GetMyProjectInfoRes> myApplyProjectInfoList = new ArrayList<>();
        List<ProjectApply> myApplyProjectList = projectApplyRepository.findAllByUserInfoAndApplyStatusAndStatusOrderByProjectApplyIdx(userInfo,"CONFIRMED","ACTIVE");

        int cnt = 0;

        for(ProjectApply projectApply : myApplyProjectList){

            Project project = projectApply.getProject();
            if(cnt == 4){
                break;
            }

            if(projectStatus == 0){
                if(project.getProjectStatus()!=0){
                    continue;
                }
            }else if(projectStatus==1){
                if(project.getProjectStatus()!=1){
                    continue;
                }
            }else if(projectStatus == 2){
                if(project.getProjectStatus()!=2){
                    continue;
                }
            }

            /**
             * ํ๋ก์ ํธ ์ง๊ตฐ ๊ฐ์ ธ์ค๊ธฐ
             */
            List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
            List<String> projectJobNameList = new ArrayList<>();

            for(ProjectJob projectJob : projectJobList){
                projectJobNameList.add(projectJob.getProjectJobName());
            }

            /**
             * ํ๋ก์ ํธ ์ฐธ์ฌํ ์ฌ๋๋ค ์ ๋ณด ๊ฐ์ ธ์ค๊ธฐ
             */
            List<GetProjectMemberProfile> projectTeamInfoList = new ArrayList<>();
            List<ProjectApply> projectApplyList = projectApplyRepository.findAllByProjectAndApplyStatusAndStatus(project,"CONFIRMED","ACTIVE");
            for(ProjectApply teamApply : projectApplyList){
                GetProjectMemberProfile getProjectMemberProfile = new GetProjectMemberProfile(teamApply.getUserInfo().getUserIdx(),
                        teamApply.getUserInfo().getProfileImageUrl());
                projectTeamInfoList.add(getProjectMemberProfile);
            }

            GetMyProjectInfoRes getProjectsRes  = new GetMyProjectInfoRes(project.getProjectIdx(),
                    project.getProjectThumbnailImageUrl(),
                    project.getProjectName(),
                    project.getProjectStatus(),
                    projectJobNameList,
                    project.getUserInfo().getUserIdx(),
                    project.getUserInfo().getUserName(),
                    project.getUserInfo().getUserJob(),
                    projectTeamInfoList);
            myApplyProjectInfoList.add(getProjectsRes);
            cnt++;
        }
        GetMyInfoRes getMyInfoRes = new GetMyInfoRes(userInfo.getUserName(),
                userInfo.getUserJob(),
                userInfo.getProfileImageUrl(),
                userInfo.getIntroduce(),
                userSnsUrlList,
                myProjectInfoList,
                myApplyProjectInfoList);
        return getMyInfoRes;
    }

    /**
     * ๋ง์ดํ์ด์ง ์์ธํ๋ณด๊ธฐ
     * @param userIdx
     * @param type
     * @return
     * @throws BaseException
     */
    public List<GetMyProjectsRes> getMyProjectsRes(Integer userIdx, Integer type) throws BaseException{
        UserInfo userInfo = retrieveUserByUserIdx(userIdx);

        List<GetMyProjectsRes> getMyProjectsResList = new ArrayList<>();
        /**
         * ๋ด๊ฐ ์ฌ๋ฆฐ ํ๋ก์ ํธ
         */
        if(type == 1){
            List<Project> myProjectList = projectRepository.findAllByUserInfoAndStatusOrderByProjectIdxDesc(userInfo,"ACTIVE");

            for(Project project : myProjectList){
                List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
                List<String> projectJobNameList = new ArrayList<>();

                for(ProjectJob projectJob : projectJobList){
                    projectJobNameList.add(projectJob.getProjectJobName());
                }

                GetMyProjectsRes getMyProjectsRes = new GetMyProjectsRes(project.getProjectIdx(),project.getProjectThumbnailImageUrl(),
                        project.getProjectName(),projectJobNameList,project.getProjectStatus(),project.getUserInfo().getUserIdx(),project.getUserInfo().getUserName(),
                        project.getUserInfo().getUserJob(),project.getUserInfo().getProfileImageUrl());

                getMyProjectsResList.add(getMyProjectsRes);
            }
        }
        /**
         * ๋ด๊ฐ ์ ์ฒญํ ํ๋ก์ ํธ
         */
        else if(type == 2){
            List<ProjectApply> myApplyProjectList = projectApplyRepository.findAllByUserInfoAndApplyStatusAndStatusOrderByProjectApplyIdx(userInfo,"CONFIRMED","ACTIVE");

            for(ProjectApply projectApply : myApplyProjectList){
                Project project = projectApply.getProject();

                List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
                List<String> projectJobNameList = new ArrayList<>();

                for(ProjectJob projectJob : projectJobList){
                    projectJobNameList.add(projectJob.getProjectJobName());
                }

                GetMyProjectsRes getMyProjectsRes = new GetMyProjectsRes(project.getProjectIdx(),project.getProjectThumbnailImageUrl(),
                        project.getProjectName(),projectJobNameList,project.getProjectStatus(),project.getUserInfo().getUserIdx(),project.getUserInfo().getUserName(),
                        project.getUserInfo().getUserJob(),project.getUserInfo().getProfileImageUrl());

                getMyProjectsResList.add(getMyProjectsRes);
            }
        }
        return getMyProjectsResList;
    }


}
