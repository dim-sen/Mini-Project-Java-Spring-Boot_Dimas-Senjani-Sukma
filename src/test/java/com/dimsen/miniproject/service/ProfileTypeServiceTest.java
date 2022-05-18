package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.ProfileTypeDto;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.repository.ProfileTypeRepository;
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
@SpringBootTest(classes = ProfileTypeService.class)
class ProfileTypeServiceTest {

    @MockBean
    private ProfileTypeRepository profileTypeRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ProfileTypeService profileTypeService;

    @Test
    void testCreateProfileTypeSucceed() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(profileTypeRepository.save(any())).thenReturn(profileTypeDao);

        ProfileTypeDto typeDto = ProfileTypeDto.builder()
                .userId(1L)
                .name("Name")
                .build();

        ResponseEntity<Object> response = profileTypeService.createProfileType(typeDto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testCreateProfileTypeFailed() {
        when(profileTypeRepository.save(any())).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) profileTypeService.createProfileType(ProfileTypeDto.builder()
                .userId(1L)
                .name("Name")
                .build()).getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetProfileTypeByIdSucceed() {
        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(profileTypeDao));

        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = profileTypeService.getProfileTypeById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());

    }

    @Test
    void testGetProfileTypeByIdIsNull() {
        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(profileTypeDao));

        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = profileTypeService.getProfileTypeById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());

    }

    @Test
    void testGetProfileTypeByIdFailed() {
        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(ProfileTypeDao.builder()
                        .name("Company")
                .build()));

        doThrow(NullPointerException.class).when(profileTypeRepository).findById(any());
        assertThrows(Exception.class, () -> profileTypeService.getProfileTypeById(1L));
    }

    @Test
    void testGetAllProfileTypesSucceed() {
        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(profileTypeRepository.findAll()).thenReturn(List.of(profileTypeDao));

        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = profileTypeService.getAllProfileType();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetAllProfileTypesFailed() {
        when(profileTypeRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) profileTypeService.getAllProfileType().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateProfileTypeByIdSucceed() {
        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(profileTypeDao));

        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = profileTypeService.updateProfileTypeById(1L, ProfileTypeDto.builder()
                        .name("Updated Name")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateProfileTypeByIdIsNull() {
        ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                .userId(1L)
                .name("Name")
                .build();

        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(profileTypeDao));

        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("New User")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(UserDto.builder()
                .id(1L)
                .username("New User")
                .build());

        ResponseEntity<Object> response = profileTypeService.updateProfileTypeById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateProfileTypeByIdFailed() {
        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(ProfileTypeDao.builder()
                        .name("Company")
                .build()));

        doThrow(NullPointerException.class).when(profileTypeRepository).findById(any());
        assertThrows(Exception.class, () -> profileTypeService.updateProfileTypeById(1L, ProfileTypeDto.builder()
                        .name("Applicant")
                .build()));
    }

    @Test
    void testDeleteProfileTypeByIdSucceed() {
        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(ProfileTypeDao.builder()
                        .name("Company")
                .build()));
        doNothing().when(profileTypeRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) profileTypeService.deleteProfileTypeById(1L).getBody();
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(profileTypeRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteProfileTypeByIdIsNull() {
        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(ProfileTypeDao.builder()
                        .name("Company")
                .build()));

        ApiResponse apiResponse = (ApiResponse) profileTypeService.deleteProfileTypeById(null).getBody();
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteProfileTypeByIdFailed() {
        when(profileTypeRepository.findById(anyLong())).thenReturn(Optional.of(ProfileTypeDao.builder()
                        .name("Company")
                .build()));

        doThrow(NullPointerException.class).when(profileTypeRepository).delete(any());
        assertThrows(Exception.class, () -> profileTypeService.deleteProfileTypeById(1L));
    }
}