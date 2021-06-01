package com.example.ged.src.user;

import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
    List<UserInfo> findBySocialIdAndStatus(String socialId, String active);
//    UserInfo findBySocialIdAndStatus(String socialId,String status);
}
