package com.springboot.blog.service;

import com.springboot.blog.payloads.CommentDto;

public interface CommentService {

	
	//It(postId) means which post has this comment
	CommentDto createComment(CommentDto commentDto, Integer postId) ;
	
	void delete(Integer commentId);
}
