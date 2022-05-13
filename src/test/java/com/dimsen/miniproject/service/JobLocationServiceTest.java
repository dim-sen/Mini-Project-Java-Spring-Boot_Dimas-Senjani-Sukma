package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.JobLocationDao;
import com.dimsen.miniproject.domain.dto.JobLocationDto;
import com.dimsen.miniproject.repository.JobLocationRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JobLocationService.class)
class JobLocationServiceTest {

    @MockBean
    private JobLocationRepository jobLocationRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private JobLocationService jobLocationService;

    @Test
    void testCreateJobLocationSucceed() {
        JobLocationDao locationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        JobLocationDto locationDto = JobLocationDto.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(modelMapper.map(any(), eq(JobLocationDao.class))).thenReturn(locationDao);
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(locationDto);

        ApiResponse apiResponse = (ApiResponse) jobLocationService.createLocation(JobLocationDto.builder()
                        .locationName("Bandung")
                .build()).getBody();

        JobLocationDto jobLocationDto = (JobLocationDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, jobLocationDto.getId());
        assertEquals("Bandung", jobLocationDto.getLocationName());
    }

    @Test
    void testCreateJobLocationFailed() {
        when(jobLocationRepository.save(any())).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) jobLocationService.createLocation(JobLocationDto.builder()
                        .locationName("Bandung")
                .build()).getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobLocationByIdSucceed() {
        JobLocationDao jobLocationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(jobLocationDao));
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(JobLocationDto.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build());

        ResponseEntity<Object> response = jobLocationService.getLocationById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobLocationByIdIsNull() {
        JobLocationDao jobLocationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(jobLocationDao));
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(JobLocationDto.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build());

        ResponseEntity<Object> response = jobLocationService.getLocationById(null);
        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobLocationByIdFailed() {
        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build()));

        doThrow(NullPointerException.class).when(jobLocationRepository).findById(any());
        assertThrows(Exception.class, () -> jobLocationService.getLocationById(1L));
    }

    @Test
    void testGetAllLocationsSucceed() {
        JobLocationDao jobLocationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(jobLocationRepository.findAll()).thenReturn(List.of(jobLocationDao));
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(JobLocationDto.builder()
                        .id(1L)
                .build());

        ResponseEntity<Object> response = jobLocationService.getAllLocation();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetAllJobLocationsFailed() {
        when(jobLocationRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) jobLocationService.getAllLocation().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobLocationByIdSucceed() {
        JobLocationDao jobLocationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(jobLocationDao));
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(JobLocationDto.builder()
                        .id(1L)
                        .locationName("Jakarta")
                .build());

        ResponseEntity<Object> response = jobLocationService.updateLocationById(1L, JobLocationDto.builder()
                        .locationName("Jakarta")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }


    @Test
    void testUpdateJobLocationByIdIsNull() {
        JobLocationDao jobLocationDao = JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build();

        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(jobLocationDao));
        when(modelMapper.map(any(), eq(JobLocationDto.class))).thenReturn(JobLocationDto.builder()
                .id(1L)
                .locationName("Jakarta")
                .build());

        ResponseEntity<Object> response = jobLocationService.updateLocationById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobLocationByIdFailed() {
        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build()));

        doThrow(NullPointerException.class).when(jobLocationRepository).findById(any());
        assertThrows(Exception.class, () -> jobLocationService.updateLocationById(1L, JobLocationDto.builder()
                        .locationName("Jakarta")
                .build()));
    }

    @Test
    void testDeleteJobLocationByIdSucceed() {
        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(JobLocationDao.builder()
                .id(1L)
                .locationName("Bandung")
                .build()));
        doNothing().when(jobLocationRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) jobLocationService.deleteALocationById(1L).getBody();
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(jobLocationRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteJobLocationByIdIsNull() {
        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build()));

        ApiResponse apiResponse = (ApiResponse) jobLocationService.deleteALocationById(null).getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteJobLocationByIdFailed() {
        when(jobLocationRepository.findById(anyLong())).thenReturn(Optional.of(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                .build()));

        doThrow(NullPointerException.class).when(jobLocationRepository).delete(any());
        assertThrows(Exception.class, () -> jobLocationService.deleteALocationById(1L));
    }

}