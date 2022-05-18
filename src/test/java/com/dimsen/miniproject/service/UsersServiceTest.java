package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UsersServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Test
    void testGetUserByIdSucceed() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                        .id(1L)
                        .username("New User")
                .build());

        ResponseEntity<Object> response = userService.getUserById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetUserByIdIsNull() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = userService.getUserById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetUserByIdFailed() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                        .id(1L)
                        .username("New User")
                .build()));

        doThrow(NullPointerException.class).when(userRepository).findById(any());
        assertThrows(Exception.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetAllUsersSucceed() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                        .id(1L)
                        .username("New User")
                .build());

        ResponseEntity<Object> response = userService.getAllUser();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetAllUsersFailed() {
        when(userRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) userService.getAllUser().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateUserByIdSucceed() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                        .id(1L)
                        .username("Updated User")
                .build());

        ResponseEntity<Object> response = userService.updateUserById(1L, UserDto.builder()
                        .username("Updated User")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateUserByIdIsNull() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("Updated User")
                .build());

        ResponseEntity<Object> response = userService.updateUserById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateUserByIdFailed() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                        .id(1L)
                        .username("New User")
                .build()));

        doThrow(NullPointerException.class).when(userRepository).findById(any());
        assertThrows(Exception.class, () -> userService.updateUserById(1L, UserDto.builder()
                        .username("Updated User")
                .build()));
    }

    @Test
    void testDeleteUserByIdSucceed() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                        .id(1L)
                        .username("New User")
                .build()));
        doNothing().when(userRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) userService.deleteUserById(1L).getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteUserByIdIsNull() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                .id(1L)
                .username("New User")
                .build()));

        ApiResponse apiResponse = (ApiResponse) userService.deleteUserById(null).getBody();
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteUserByIdFailed() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                .id(1L)
                .username("New User")
                .build()));
        doThrow(NullPointerException.class).when(userRepository).delete(any());
        assertThrows(Exception.class, () -> userService.deleteUserById(1L));
    }
}