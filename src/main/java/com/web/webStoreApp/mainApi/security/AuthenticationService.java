package com.web.webStoreApp.mainApi.security;

import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.entity.Role;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.security.dto.JwtAuthenticationResponse;
import com.web.webStoreApp.mainApi.security.dto.SignInRequest;
import com.web.webStoreApp.mainApi.security.dto.SignUpRequest;
import com.web.webStoreApp.mainApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        System.out.println("Service start");

        var user = User.builder()
                .name(request.getName())
                .phone_number(request.getPhone_number())
                .birthDay(request.getBirthDay())
                .password(passwordEncoder.encode(request.getPassword()))
                .existing(true)
                .level_of_loyalty(LevelsOfLoyalty.bronze)
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);

        System.out.println("Service end");

        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        System.out.println("Service start");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getPhone_number(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getPhone_number());

        var jwt = jwtService.generateToken(user);
        System.out.println("Service end");
        return new JwtAuthenticationResponse(jwt);
    }
}