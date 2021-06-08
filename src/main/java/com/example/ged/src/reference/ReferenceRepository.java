package com.example.ged.src.reference;

import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceCategory.models.ReferenceCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReferenceRepository extends CrudRepository<Reference,Integer> {

    List<Reference> findByReferenceCategoryAndStatus(ReferenceCategory referenceCategory, String active);

    Reference findByReferenceIdxAndStatus(Integer referenceIdx, String active);

    Boolean existsByReferenceIdxAndStatus(Integer referenceIdx, String active);

}