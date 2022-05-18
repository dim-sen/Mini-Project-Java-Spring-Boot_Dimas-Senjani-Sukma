package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.TokenResponse;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.domain.dto.UsernamePassword;
import com.dimsen.miniproject.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserDto userDto) {
        return authenticationService.createUser(userDto);
    }

//    @PostMapping(value = "/token")
//    public TokenResponse generateToken(@Valid @RequestBody UsernamePassword usernamePassword) {
//        return authenticationService.generateToken(usernamePassword);
//    }

    @PostMapping(value = "/token")
    public ResponseEntity<Object> generateToken(@Valid @RequestBody UsernamePassword usernamePassword) {
        return authenticationService.generateToken(usernamePassword);
    }
}
