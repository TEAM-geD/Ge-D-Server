package com.example.ged.src.project;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Integer> {

    List<Project> findProjectByStatusOrderByProjectIdxDesc(String status);

    @Query("select P from Project P inner join ProjectCategory PC on P.projectIdx = PC.project.projectIdx where PC.projectCategoryName =:projectName and P.status=:status order by P.projectIdx desc")
    List<Project> findProjectByProjectJobName(String projectName,String status);

    Project findProjectByProjectIdxAndStatus(Integer projectIdx, String status);

    List<Project> findTop2ByUserInfoAndStatusOrderByProjectIdxDesc(UserInfo userInfo, String status);

    List<Project> findAllByUserInfoAndStatusOrderByProjectIdxDesc(UserInfo userInfo, String status);
}
