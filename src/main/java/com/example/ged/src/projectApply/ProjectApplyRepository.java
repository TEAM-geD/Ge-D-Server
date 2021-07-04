package com.example.ged.src.projectApply;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectApplyRepository extends CrudRepository<ProjectApply,Integer> {
    ProjectApply findAllByProjectAndUserInfoAndStatus(Project project, UserInfo userInfo,String status);


    List<ProjectApply> findAllByProjectAndApplyStatusAndStatus(Project project, String applyStatus, String status);
}
