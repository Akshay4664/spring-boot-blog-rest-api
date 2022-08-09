package com.springboot.blog.service;

import com.springboot.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);

    public UserDto updateUser(UserDto user , Integer userId);

    public UserDto getUserById(Integer userId);

    public List<UserDto> getAllUsers();

    public void deleteUser(Integer userId);
}
