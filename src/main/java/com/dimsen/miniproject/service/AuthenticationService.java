package com.dimsen.miniproject.service;

import com.dimsen.miniproject.config.SecurityConfiguration;
import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.JwtTokenProvider;
import com.dimsen.miniproject.domain.dto.TokenResponse;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.domain.dto.UsernamePassword;
import com.dimsen.miniproject.repository.UserRepository;
import com.dimsen.miniproject.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final SecurityConfiguration securityConfiguration;

//    public ResponseEntity<Object> register(UsernamePassword usernamePassword) {
//        UserDao userDao = new UserDao();
//        userDao.setUsername(usernamePassword.getUsername());
//        userDao.setPassword(passwordEncoder.encode(usernamePassword.getPassword()));
//
//        return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
//
//    }

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

    public TokenResponse generateToken(UsernamePassword usernamePassword) {
        log.info("Generating token");
        try {
            log.info("Authentication. Username: {}", usernamePassword.getUsername());
            log.info("Authentication. Password: {}", usernamePassword.getPassword());
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            usernamePassword.getUsername(), usernamePassword.getPassword()
//                    )
//            );
            Authentication authentication = securityConfiguration.authenticationManagerBean().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernamePassword.getUsername(), usernamePassword.getPassword()
                    )
            );

            log.info("security");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            log.info("Generated token: {}", jwt);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwt);

            return tokenResponse;

        } catch (Exception badCredentialsException) {
            throw new RuntimeException(badCredentialsException.getMessage(), badCredentialsException);
        }

//        } catch (BadCredentialsException e) {
//            log.error("Bad credentials. Error: {}", e.getMessage());
//            throw e;
//        } catch (Exception e) {
//            log.error("An error occurred in generate token. Error: {}", e.getMessage());
//            throw e;
//        }
    }
}
