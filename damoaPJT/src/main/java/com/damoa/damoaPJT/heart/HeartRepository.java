package com.damoa.damoaPJT.heart;

import com.damoa.damoaPJT.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Integer> {

    Optional<Heart> findByUserUserNoAndNoAndType(int userNo, int no, String type);
}
