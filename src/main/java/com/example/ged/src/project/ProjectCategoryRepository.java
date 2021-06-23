package com.example.ged.src.project;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectCategoryRepository extends CrudRepository <ProjectCategory,Integer>{
    List<ProjectCategory> findAllByProject(Project project);
}
