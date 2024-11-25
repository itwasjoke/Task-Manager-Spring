package com.itwasjoke.effectivemobile.security;

import com.itwasjoke.effectivemobile.DTO.UserRequestDTO;
import com.itwasjoke.effectivemobile.entity.User;
import com.itwasjoke.effectivemobile.security.jwt.JwtAuthenticationResponse;
import com.itwasjoke.effectivemobile.security.jwt.JwtService;
import com.itwasjoke.effectivemobile.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthenticationResponse signUp(UserRequestDTO userRequestDTO){
        String password = passwordEncoder.encode(userRequestDTO.password());
        User user = userService.createUser(userRequestDTO, password);
        var jwt = "Bearer " + jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(String email, String password){
        User user = userService.getUser(email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                password
        ));
        String jwt = "Bearer "+jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
