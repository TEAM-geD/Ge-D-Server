package com.example.ged.src.user;

import com.example.ged.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
    UserInfo findBySocialIdAndStatus(String socialId, String active);
    UserInfo findByUserIdxAndStatus(Integer userIdx, String active);
    List<UserInfo> findByUserJobAndIsMembersAndStatus(String job, String isMembers, String active);

    UserInfo findBySocialId(String socialId);

    UserInfo findByUserIdx(Integer idx);
}
