package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
