package com.example.ged.src.project;

import com.example.ged.src.project.models.ProjectCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCategoryRepository extends CrudRepository <ProjectCategory,Integer>{
}
