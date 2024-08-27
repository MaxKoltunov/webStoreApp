package com.web.webStoreApp.mainApi.security;

import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.entity.Role;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.security.dto.JwtAuthenticationResponse;
import com.web.webStoreApp.mainApi.security.dto.SignInRequest;
import com.web.webStoreApp.mainApi.security.dto.SignUpRequest;
import com.web.webStoreApp.mainApi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("signUp() - starting signing up");

        var user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDay(request.getBirthDay())
                .password(passwordEncoder.encode(request.getPassword()))
                .existing(true)
                .levelOfLoyalty(LevelsOfLoyalty.bronze)
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        log.info("signUp() - signing up completed for user with name: '{}' and phone number: '{}'", user.getName(), user.getPhoneNumber());
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getPhoneNumber(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getPhoneNumber());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}