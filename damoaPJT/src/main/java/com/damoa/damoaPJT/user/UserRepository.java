package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndUserPw(String userId, String userPw);
}
