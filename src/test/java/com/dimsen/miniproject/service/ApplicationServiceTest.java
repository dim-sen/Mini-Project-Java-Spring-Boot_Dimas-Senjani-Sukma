package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.ApplicationDao;
import com.dimsen.miniproject.domain.dto.ApplicationDto;
import com.dimsen.miniproject.repository.ApplicationRepository;
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
@SpringBootTest(classes = ApplicationService.class)
class ApplicationServiceTest {

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationService applicationService;

    @Test
    void testCreateApplicationSucceed() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        ApplicationDto applicationDto = ApplicationDto.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(modelMapper.map(any(), eq(ApplicationDao.class))).thenReturn(applicationDao);
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(applicationDto);

        ResponseEntity<Object> response = applicationService.createApplication(ApplicationDto.builder()
                        .applicationStatus("Approved")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        ApplicationDto dto = (ApplicationDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, dto.getId());
        assertEquals("Approved", dto.getApplicationStatus());
    }

    @Test
    void testCreateApplicationFailed() {
        when(applicationRepository.save(any())).thenReturn(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) applicationService.createApplication(ApplicationDto.builder()
                        .applicationStatus("Approved")
                .build()).getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetApplicationByIdSucceed() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(applicationDao));
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(ApplicationDto.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build());

        ResponseEntity<Object> response = applicationService.getApplicationById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetApplicationByIdIsNull() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(applicationDao));
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(ApplicationDto.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build());

        ResponseEntity<Object> response = applicationService.getApplicationById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetApplicationByIdFailed() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationDao.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build()));

        doThrow(NullPointerException.class).when(applicationRepository).findById(any());
        assertThrows(Exception.class, () -> applicationService.getApplicationById(1L));
    }

    @Test
    void testGetAllApplicationsSucceed() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(applicationRepository.findAll()).thenReturn(List.of(applicationDao));
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(ApplicationDto.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build());

        ResponseEntity<Object> response = applicationService.getAllApplication();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetAllApplicationFailed() {
        when(applicationRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) applicationService.getAllApplication().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateApplicationByIdSucceed() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(applicationDao));
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(ApplicationDto.builder()
                        .id(1L)
                        .applicationStatus("Interviews")
                .build());

        ResponseEntity<Object> response = applicationService.updateApplicationById(1L, ApplicationDto.builder()
                        .applicationStatus("Interviews")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateApplicationByIdIsNull() {
        ApplicationDao applicationDao = ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(applicationDao));
        when(modelMapper.map(any(), eq(ApplicationDto.class))).thenReturn(ApplicationDto.builder()
                .id(1L)
                .applicationStatus("Interviews")
                .build());

        ResponseEntity<Object> response = applicationService.updateApplicationById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateApplicationByIdFailed() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationDao.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build()));

        doThrow(NullPointerException.class).when(applicationRepository).findById(any());
        assertThrows(Exception.class, () -> applicationService.updateApplicationById(1L, ApplicationDto.builder()
                        .applicationStatus("Interviews")
                .build()));
    }

    @Test
    void testDeleteApplicationByIdSucceed() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationDao.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build()));
        doNothing().when(applicationRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) applicationService.deleteApplicationById(1L).getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(applicationRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteApplicationByIdIsNull() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationDao.builder()
                .id(1L)
                .applicationStatus("Approved")
                .build()));
        doNothing().when(applicationRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) applicationService.deleteApplicationById(null).getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteApplicationByIdFailed() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationDao.builder()
                        .id(1L)
                        .applicationStatus("Approved")
                .build()));

        doThrow(NullPointerException.class).when(applicationRepository).delete(any());
        assertThrows(Exception.class, () -> applicationService.deleteApplicationById(1L));
    }

}