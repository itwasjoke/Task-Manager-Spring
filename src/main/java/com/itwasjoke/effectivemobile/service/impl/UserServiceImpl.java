package com.itwasjoke.effectivemobile.service.impl;

import com.itwasjoke.effectivemobile.DTO.UserRequestDTO;
import com.itwasjoke.effectivemobile.DTO.UserResponseDTO;
import com.itwasjoke.effectivemobile.DTO.mapper.UserMapper;
import com.itwasjoke.effectivemobile.customEnum.Role;
import com.itwasjoke.effectivemobile.entity.User;
import com.itwasjoke.effectivemobile.exception.user.UserAlreadyExistsException;
import com.itwasjoke.effectivemobile.exception.user.UserNotFoundException;
import com.itwasjoke.effectivemobile.repository.UserRepository;
import com.itwasjoke.effectivemobile.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO getUserProfile(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            String role = null;
            switch (user.getRole()){
                case USER -> role = "Пользователь";
                case ADMIN -> role = "Админ";
            }
            return userMapper.userToUserResponse(user, role);
        }{
            throw new UserNotFoundException("User not found in getUserProfile() func");
        }
    }

    @Override
    public User createUser(UserRequestDTO userRequestDTO, String password) {
        if (userRepository.existsByEmail(userRequestDTO.email())){
            throw new UserAlreadyExistsException("With this email user already exists in createUser() func");
        } else {
            User user = userMapper.userRequestToUser(userRequestDTO);
            user.setPassword(password);
            return userRepository.save(user);
        }
    }

    @Override
    public User getUser(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User not found in getUser() func");
        }
    }


    @Override
    public UserDetailsService userDetailsService() {
        return this::getUser;
    }
}
