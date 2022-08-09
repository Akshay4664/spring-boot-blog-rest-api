package com.springboot.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "postid", postId));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = commentRepo.save(comment);
		
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void delete(Integer commentId) {
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id",commentId));
		
		commentRepo.delete(comment);
	}
}
