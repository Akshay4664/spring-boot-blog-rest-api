package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.model.Comment;
import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {
	
	
	@Autowired
	CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
													@PathVariable Integer postId){
		
		CommentDto comment = commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/post/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		
		commentService.delete(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted successfully !!", true)
												, HttpStatus.OK);
	}
}
