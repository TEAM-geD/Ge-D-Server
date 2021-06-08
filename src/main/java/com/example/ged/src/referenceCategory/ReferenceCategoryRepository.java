package com.example.ged.src.referenceCategory;

import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceCategoryRepository extends CrudRepository<ReferenceCategory,Integer> {

}