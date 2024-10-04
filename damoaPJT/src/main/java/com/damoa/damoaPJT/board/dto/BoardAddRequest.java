package com.damoa.damoaPJT.board.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.Category;
import com.damoa.damoaPJT.entity.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// @ModelAttribute를 사용하기 위해서는 @Setter가 필요
// @RequestBody를 사용할 경우 @Setter가 필요없지만, form 형식의 데이터는 받을 수 없음
//      추가적으로 파일같은 multipart/form-data 형식 또한 받을 수 없음
// 참고
// https://middleearth.tistory.com/35    @ModelAttribute, @RequestBody 등 정리
// https://minchul-son.tistory.com/546   @ModelAttribute 동작 방식 설명

// 위와 관련하여 수정하면 매개변수의 이름을 인식하지 못하는 에러 발생
// 스프링 부트 3.2부터 발생하는 에러임
// 관련 설명 및 조치
// https://velog.io/@ghwns9991/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-3.2-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98-%EC%9D%B4%EB%A6%84-%EC%9D%B8%EC%8B%9D-%EB%AC%B8%EC%A0%9C

@Getter
@AllArgsConstructor
public class BoardAddRequest {

    private String title;
    private String content;
    private int price;

    private String location;
    private String category;

    public Board toEntity(){
        return Board.builder()
                .boardTitle(title)
                .boardContent(content)
                .boardPrice(price)
                .boardLocation(location)
                .category(Category.builder()
                        .categoryName(category)
                        .build())
                .build();
    }

}
