package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dao.UserDetailsDao;
import com.dimsen.miniproject.domain.dto.JwtTokenProvider;
import com.dimsen.miniproject.domain.dto.TokenResponse;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.domain.dto.UsernamePassword;
import com.dimsen.miniproject.repository.UserRepository;
import com.dimsen.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> createUser(UserDto userDto) {
        log.info("Creating new User");
        try {
            log.info("Save new user");
            UserDao userDao = UserDao.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(userDto.getRole())
                    .build();
            userRepository.save(userDao);

            UserDto dto = modelMapper.map(userDao, UserDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new user. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> generateToken(UsernamePassword usernamePassword) {
        log.info("Generating token");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernamePassword.getUsername(), usernamePassword.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            UserDetailsDao userDetailsDao = (UserDetailsDao) authentication.getPrincipal();
            List<String> roles = userDetailsDao.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            TokenResponse tokenResponse = TokenResponse.builder()
                    .token(jwt)
                    .roles(roles)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, tokenResponse, HttpStatus.OK);

        } catch (Exception badCredentialsException) {
            throw new RuntimeException(badCredentialsException.getMessage(), badCredentialsException);
        }
    }
}
