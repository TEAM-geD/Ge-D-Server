package com.example.ged.src.category;

import com.example.ged.src.category.models.UserJobCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJobCategoryRepository extends CrudRepository<UserJobCategory,Integer> {
    List<UserJobCategory> findAllByStatus(String status);
}
