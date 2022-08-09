package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
