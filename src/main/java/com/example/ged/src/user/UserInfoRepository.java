package com.example.ged.src.user;

import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
//    UserInfo findBySocialIdAndStatus(String socialId,String status);
}
