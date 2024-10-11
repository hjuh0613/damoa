package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "board_no", nullable = false)
    private int boardNo;

    @Column(name = "board_title", length = 150, nullable = false)
    private String boardTitle;

    @Column(name = "board_content", length = 1500, nullable = false)
    private String boardContent;

    @CreatedDate
    @Column(name = "board_date")
    private LocalDateTime boardDate;

    @Column(name = "board_location", length = 500, nullable = false)
    private String boardLocation;

    @Column(name = "board_price", nullable = false)
    private int boardPrice;

    // @OneToOne 카테고리 1개당 게시글 1개 작성 가능
    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Board(int boardNo,
                 String boardTitle,
                 String boardContent,
                 LocalDateTime boardDate,
                 String boardLocation,
                 int boardPrice,
                 Category category,
                 User user){
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardDate = boardDate;
        this.boardLocation = boardLocation;
        this.boardPrice = boardPrice;
        this.category = category;
        this.user = user;
    }

    public void update(String boardTitle,
                       String boardContent,
                       String boardLocation,
                       int boardPrice,
                       int categoryNo){
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardLocation = boardLocation;
        this.boardPrice = boardPrice;
        this.category = Category.builder()
                .categoryNo(categoryNo)
                .build();
    }

}
