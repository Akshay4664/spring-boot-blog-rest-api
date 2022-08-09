package com.springboot.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.springboot.blog.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private int id;
    
    @NotEmpty
    @Size(min=4 , message = "Username must be min of 4 characters 1!")
    private String name;
    
    @Email(message = "Email address is not valid !!")
    private String email;
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    private String about;
    
    private Set<RoleDto> roles = new HashSet<>();
}
