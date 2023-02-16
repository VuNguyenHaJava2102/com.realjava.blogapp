package com.realjava.blogapp.service;

import com.realjava.blogapp.payload.LoginDto;
import com.realjava.blogapp.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String signup(RegisterDto registerDto);

}
