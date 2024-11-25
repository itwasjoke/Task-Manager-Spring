package com.itwasjoke.effectivemobile.service;

import com.itwasjoke.effectivemobile.DTO.UserRequestDTO;
import com.itwasjoke.effectivemobile.DTO.UserResponseDTO;
import com.itwasjoke.effectivemobile.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponseDTO getUserProfile(String email);
    User createUser(UserRequestDTO userRequestDTO, String password);
    User getUser(String email);
    UserDetailsService userDetailsService();
}
