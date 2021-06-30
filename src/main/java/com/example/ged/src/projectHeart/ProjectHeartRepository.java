package com.example.ged.src.projectHeart;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.projectHeart.models.ProjectHeart;
import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectHeartRepository extends CrudRepository<ProjectHeart,Integer> {
    Boolean existsByUserInfoAndProjectAndStatus(UserInfo userInfo, Project project, String status);
    ProjectHeart findAllByUserInfoAndProjectAndStatus(UserInfo userInfo, Project project, String status);
    List<ProjectHeart> findAllByUserInfoAndStatusOrderByProjectHeartIdxDesc(UserInfo userInfo, String status);
}
