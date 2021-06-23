package com.example.ged.src.project;

import com.example.ged.src.project.models.Project;
import com.example.ged.src.project.models.ProjectJob;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectJobRepository extends CrudRepository<ProjectJob,Integer> {
    List<ProjectJob> findAllByProject(Project project);
}
