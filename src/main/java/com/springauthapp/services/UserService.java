package com.springauthapp.services;

import com.springauthapp.dto.UserDto;
import com.springauthapp.exceptions.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {
     boolean checkIfUserExist(String username);
     void register(UserDto user) throws UserAlreadyExistException;
}
