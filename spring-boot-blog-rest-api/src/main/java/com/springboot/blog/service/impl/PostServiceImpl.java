package com.springboot.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.model.Post;
import com.springboot.blog.model.User;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.PostService;
//import com.springboot.blog.service.UserService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CategoryRepository categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","id" , userId));
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","id" , categoryId));
		
		Post post = modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
//		post.setAddedDate(new Date());
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost = postRepo.save(post);
		
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		//post.setAddDate(postDto.getAddedDate());
		
		Post updatedPost = postRepo.save(post);

		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		//List<Post> posts = postRepo.findAll();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		Page<Post> pagePost = postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				
				 .collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalElements(pagePost.getNumberOfElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		
		return postResponse;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post posts = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postid", postId));
		return modelMapper.map(posts, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id", categoryId));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		return posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}
