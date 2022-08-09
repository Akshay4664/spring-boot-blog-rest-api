package com.springboot.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.springboot.blog.model.Category;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments = new HashSet<>();
	
}
