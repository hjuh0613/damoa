package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.file.dto.FileAddRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${global.fileStorePath}")
    private String fileStorePath;

    public List<FileAddRequest> storeFile(List<MultipartFile> multipartFiles, int boardNo, int board_type) throws Exception {

        // 오늘 날짜의 폴더명 획득
        Date date = new Date();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMdd");
        String dateFolder = simpleFormatter.format(date).toString();

        // 파일 경로 생성
        String storePathString = fileStorePath;
        storePathString = storePathString.concat(File.separator).concat(dateFolder);

        File saveFolder = new File(storePathString);

        // 파일경로가 존재하지 않거나 파일인 경우
        if( !saveFolder.exists() || saveFolder.isFile() ) {
            // 폴더 생성
            if( saveFolder.mkdirs() ){
                System.out.println("[file.mkdir] saveFolder : Create Success");
            } else {
                System.out.println("[file.mkdir] saveFolder : Create Fail");
            }
        }

        String filePath = "";

        // DB에 저장하기 위한 return 생성
        List<FileAddRequest> boardFileAddRequestList = new ArrayList<>();

        for(int i=0; i<multipartFiles.size(); i++){

            MultipartFile file = multipartFiles.get(i);

            String originFileName = file.getOriginalFilename();

            // 만약 파일명이 없다면 저장 없이 지나감
            if("".equals(originFileName)){
                continue;
            }

            // 새로 저장할 파일 이름
            // UUID는 파일의 고유 번호
            String getUUId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            String newName = getUUId;

            // 저장할 파일의 크기
            long size = file.getSize();

            if(!"".equals(originFileName)){

                // 파일 저장
                filePath = storePathString.concat(File.separator).concat(newName);
                file.transferTo(new File(filePath));
            }

            // db에 저장하기 위한 파일 정보 생성
            FileAddRequest boardFileAddRequest = new FileAddRequest(originFileName, filePath, boardNo, board_type, size);

            boardFileAddRequestList.add(boardFileAddRequest);

        }

        return boardFileAddRequestList;
    }

}