package com.springboot.blog.repository;

import com.springboot.blog.model.Category;
import com.springboot.blog.model.Post;
import com.springboot.blog.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	//@Query("select * from post where title like '%keyword%'")
	List<Post> findByTitleContaining(String keyword);
}
