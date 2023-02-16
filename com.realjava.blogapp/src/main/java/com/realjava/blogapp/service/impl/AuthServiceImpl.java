package com.realjava.blogapp.service.impl;

import com.realjava.blogapp.entity.Role;
import com.realjava.blogapp.entity.User;
import com.realjava.blogapp.exception.InternalException;
import com.realjava.blogapp.payload.LoginDto;
import com.realjava.blogapp.payload.RegisterDto;
import com.realjava.blogapp.repository.RoleRepository;
import com.realjava.blogapp.repository.UserRepository;
import com.realjava.blogapp.security.JwtProvider;
import com.realjava.blogapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        return token;
    }

    // Register with role_user
    @Override
    public String signup(RegisterDto registerDto) {

        // Check username is duplicated or not
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new InternalException(HttpStatus.BAD_REQUEST, "Username is duplicated!");
        }

        // Check e-mail is duplicated or not
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new InternalException(HttpStatus.BAD_REQUEST, "Email is duplicated!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User signed-up successfully!";
    }
}
