package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
}
