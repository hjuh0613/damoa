package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository  extends JpaRepository<File, Integer> {

    // file_no로 조회하는 메서드
    Optional<File> findByFileNo(int fileNo);

    // board_no와 board_type으로 조회하는 메서드
    List<File> findByBoardNoAndBoardType(int boardNo, int boardType);

}
