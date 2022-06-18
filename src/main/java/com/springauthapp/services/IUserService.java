package com.springauthapp.services;

import com.springauthapp.exceptions.UserAlreadyExistException;
import com.springauthapp.model.CustomUserDetails;
import com.springauthapp.dto.UserDto;
import com.springauthapp.model.User;
import com.springauthapp.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IUserService implements UserService {

    private static final String USER_NOT_FOUND =
            "user with username %s not found";


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IUserService(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findUserByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found" , username))));
    }


    @Override
    public void register(UserDto nonUser) throws UserAlreadyExistException {
        if(checkIfUserExist(nonUser.getUsername())){
            throw new UserAlreadyExistException("User with this name already exist");
        }
        User user = new User();
        BeanUtils.copyProperties(nonUser , user);
        encodePassword(user);
        userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExist(String username){
        return userRepository.findUserByUsername(username).isPresent();
    }


    private void encodePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
