package com.example.ged.src.referenceHeart;

import com.example.ged.src.reference.models.Reference;
import com.example.ged.src.referenceHeart.models.ReferenceHeart;
import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReferenceHeartRepository extends CrudRepository<ReferenceHeart,Integer> {

    Boolean existsByUserInfoAndReferenceAndStatus(UserInfo userInfo, Reference reference, String active);

    ReferenceHeart findByUserInfoAndReferenceAndStatus(UserInfo userInfo, Reference reference, String active);

    List<ReferenceHeart> findByUserInfoAndStatus(UserInfo userInfo, String active, Sort createdAt);
}
