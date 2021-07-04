package com.example.ged.src.projectApply;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectApply.models.ProjectApply;
import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface ProjectApplyRepository extends CrudRepository<ProjectApply,Integer> {
    ProjectApply findAllByProjectAndUserInfoAndStatus(Project project, UserInfo userInfo,String status);
}
