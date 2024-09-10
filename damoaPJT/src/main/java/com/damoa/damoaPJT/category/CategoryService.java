package com.damoa.damoaPJT.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // 카테고리 번호로 카테고리 이름 조회
    public String getCategoryNameByCategoryNo(int categoryNo) {
        return categoryRepository.getReferenceById(categoryNo).getCategoryName();
    }
}
