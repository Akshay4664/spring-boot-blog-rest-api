package com.springboot.blog.service.impl;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;
    
//    @Override
//    public User createUser(User user) {
//        //User user  = this.dtoToUser(userDto);
//        User savedUser = this.userRepository.save(user);
//        //return this.userToDto(savedUser);
//        return savedUser;
//    }

    @Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}
    
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        //we are fetching user against this id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepository.save(user);
        UserDto userDto1 = userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
    }




    //convert Entity to DTO and DTO to entity
    public User dtoToUser(UserDto userDto){
    	User user = this.modelMapper.map(userDto, User.class);
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user){
    	UserDto userDto = this.modelMapper.map(user, UserDto.class);
    	
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
	
		//encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//by default we assign normal users to roles
		Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser =  userRepository.save(user);
		
		return modelMapper.map(newUser, UserDto.class);
	}
}
