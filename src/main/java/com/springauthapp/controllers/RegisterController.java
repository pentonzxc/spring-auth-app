package com.springauthapp.controllers;

import com.springauthapp.dto.UserDto;
import com.springauthapp.exceptions.UserAlreadyExistException;
import com.springauthapp.repositories.UserRepository;
import com.springauthapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model){
        model.addAttribute("user" , new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(@ModelAttribute UserDto user , Model model){
        try{
            userService.register(user);
        }catch (UserAlreadyExistException e){
            model.addAttribute("user" , user);
            return "register";
        }
        return "redirect:/";
    }
}
