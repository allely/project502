package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository repository;

    public void save(RequestCategory form) {
        // 게터, 세터로 카테고리클래스의 내용을 form( RequestCategory 안에 넣어 사용할 수 있게 함
        Category category = new ModelMapper().map(form, Category.class);

    }
}
