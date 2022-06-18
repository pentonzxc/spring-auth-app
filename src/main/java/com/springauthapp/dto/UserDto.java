package com.springauthapp.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
public class UserDto implements Serializable {

    private String username;

    private String password;
}
