package com.example.ged.src.userSns;

import com.example.ged.src.user.models.UserInfo;
import com.example.ged.src.userSns.models.UserSns;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserSnsRepository extends CrudRepository<UserSns,Integer> {

    List<UserSns> findByUserInfoAndStatus(UserInfo user, String active);
}