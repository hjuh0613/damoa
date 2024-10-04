package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no", nullable = false)
    private int categoryNo;

    @Column(name = "category_name", length = 45, nullable = false)
    private String categoryName;

    @Builder
    public Category(int categoryNo, String categoryName){
        this.categoryNo = categoryNo;
        this.categoryName = categoryName;
    }
}
