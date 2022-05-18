package com.dimsen.miniproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.common.ApiResponseStatus;
import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dao.UserDetailsDao;
import com.dimsen.miniproject.domain.dto.JwtTokenProvider;
import com.dimsen.miniproject.domain.dto.TokenResponse;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.domain.dto.UsernamePassword;
import com.dimsen.miniproject.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateUserSucceed() {
        UserDao userDao = new UserDao();
        userDao.setId(1L);
        userDao.setPassword("iloveyou");
        userDao.setUsername("dim");

        when(this.userRepository.save((UserDao) any())).thenReturn(userDao);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        ResponseEntity<Object> actualCreateUserResult = this.authenticationService.createUser(new UserDto());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateUserResult.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
    }

    @Test
    void testCreateUserFailed() {
        UserDao userDao = new UserDao();
        userDao.setId(1L);
        userDao.setPassword("iloveyou");
        userDao.setUsername("dim");

        when(this.userRepository.save((UserDao) any())).thenReturn(userDao);
        when(this.modelMapper.map((Object) any(), (Class<UserDto>) any()))
                .thenThrow(new RuntimeException("An error occurred"));


        ResponseEntity<Object> actualCreateUserResult = this.authenticationService.createUser(new UserDto());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateUserResult.getBody()).getStatus();

        assertEquals("Unknown Error Happened", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());
    }

    @Test
    void testGenerateTokenSucceed() throws AuthenticationException {
        when(this.jwtTokenProvider.generateToken((Authentication) any())).thenReturn("ABC123");

        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        when(this.authenticationManager.authenticate((Authentication) any())).thenReturn(new TestingAuthenticationToken(
                new UserDetailsDao(123L, "dim", "iloveyou", grantedAuthorityList), "Credentials"));

        ResponseEntity<Object> actualGenerateTokenResult = this.authenticationService
                .generateToken(new UsernamePassword("dim", "iloveyou"));

        assertTrue(actualGenerateTokenResult.hasBody());
        assertTrue(actualGenerateTokenResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualGenerateTokenResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualGenerateTokenResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualGenerateTokenResult.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals(grantedAuthorityList, ((TokenResponse) data).getRoles());
        assertEquals("SUCCESS", status.getCode());
        assertEquals("ABC123", ((TokenResponse) data).getToken());
    }


    @Test
    void testGenerateTokenFailed() throws AuthenticationException {
        when(this.jwtTokenProvider.generateToken((org.springframework.security.core.Authentication) any()))
                .thenReturn("ABC123");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        UsernamePassword usernamePassword = mock(UsernamePassword.class);

        when(usernamePassword.getPassword()).thenThrow(new RuntimeException("An error occurred"));
        when(usernamePassword.getUsername()).thenThrow(new RuntimeException("An error occurred"));

        assertThrows(RuntimeException.class, () -> this.authenticationService.generateToken(usernamePassword));
    }
}

