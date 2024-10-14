package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository  extends JpaRepository<File, Integer> {
}
