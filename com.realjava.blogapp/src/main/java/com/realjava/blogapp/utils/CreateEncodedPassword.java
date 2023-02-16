package com.realjava.blogapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateEncodedPassword {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password = passwordEncoder.encode("admin1");
        System.out.println(password);
    }
}
// id 1: User 1 - $2a$10$jPoB1etayKXtZy0K7i3.HOU7dbKjgi78ky0zVpCh56O/Aek/t7IlS
// id 2: Admin 1 - $2a$10$eCr9G.ZxDbZkDyMws7wfteK5BQQwXLOxN2JMBFaYf5jg9FGZJ8sIy