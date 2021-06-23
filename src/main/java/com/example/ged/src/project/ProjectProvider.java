package com.example.ged.src.project;

import com.example.ged.config.BaseException;
import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectJob;
import com.example.ged.src.project.models.dto.GetProjectsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectProvider {

    private final ProjectRepository projectRepository;
    private final ProjectJobRepository projectJobRepository;

    /**
     * 프로젝트 리스트 조회 API
     * @param type
     * @return
     * @throws BaseException
     */
    public List<GetProjectsRes> getProjects(String type) throws BaseException {

        List<GetProjectsRes> getProjectsResList = null;
        List<Project> projectList = null;
        if(type.equals("ALL")){
            projectList = projectRepository.findProjectByStatus("ACTIVE");
        }else{
            projectList = projectRepository.findProjectByProjectJobName(type,"ACTIVE");
        }
        for(Project project : projectList){
            List<ProjectJob> projectJobList = projectJobRepository.findAllByProject(project);
            List<String> projectJobNameList = null;

            for(ProjectJob projectJob : projectJobList){
                projectJobNameList.add(projectJob.getProjectJobName());
            }

            GetProjectsRes getProjectsRes = new GetProjectsRes(project.getProjectIdx(),project.getProjectThumbnailImageUrl(),project.getProjectName(),projectJobNameList,project.getUserInfo().getUserIdx(),project.getUserInfo().getUserName(),project.getUserInfo().getUserName());
            getProjectsResList.add(getProjectsRes);
        }
        return getProjectsResList;
    }
}
