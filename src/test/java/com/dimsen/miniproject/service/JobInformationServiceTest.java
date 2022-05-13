package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import com.dimsen.miniproject.domain.dao.JobInformationDao;
import com.dimsen.miniproject.domain.dao.JobLocationDao;
import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.domain.dto.JobInformationDto;
import com.dimsen.miniproject.domain.dto.JobLocationDto;
import com.dimsen.miniproject.repository.JobInformationRepository;
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
@SpringBootTest(classes = JobInformationService.class)
class JobInformationServiceTest {

    @MockBean
    private JobInformationRepository jobInformationRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private JobInformationService jobInformationService;

    @Test
    void testCreateJobInformationSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        JobInformationDto informationDto = JobInformationDto.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(modelMapper.map(any(), eq(JobInformationDao.class))).thenReturn(jobInformationDao);
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(informationDto);

        ResponseEntity<Object> response = jobInformationService.createInformation(JobInformationDto.builder()
                        .jobTitle("Sales Manager")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        JobInformationDto jobInformationDto = (JobInformationDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, jobInformationDto.getId());
        assertEquals("Sales Manager", jobInformationDto.getJobTitle());
    }

    @Test
    void testCreateJobInformationFailed() {
        when(jobInformationRepository.save(any())).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) jobInformationService.createInformation(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build()).getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobInformationByIdSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.getInformationById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobInformationByIdIsNull() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.getInformationById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobInformationByIdFailed() {
        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(JobInformationDao.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build()));

        doThrow(NullPointerException.class).when(jobInformationRepository).findById(any());
        assertThrows(Exception.class, () -> jobInformationService.getInformationById(1L));
    }

    @Test
    void testGetAllJobInformationSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(jobInformationRepository.findAll()).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.getAllInformation();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetAllJobInformationFailed() {
        when(jobInformationRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) jobInformationService.getAllInformation().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobInformationByIdSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Development Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.updateInformationById(1L, JobInformationDto.builder()
                        .jobTitle("Development Manager")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobInformationByIdIsNull() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build();

        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(jobInformationDao));

        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Development Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.updateInformationById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobInformationByIdFailed() {
        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(JobInformationDao.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build()));

        doThrow(NullPointerException.class).when(jobInformationRepository).findById(any());
        assertThrows(Exception.class, () -> jobInformationService.updateInformationById(1L, JobInformationDto.builder()
                        .jobTitle("Development Manager")
                .build()));
    }

    @Test
    void testDeleteJobInformationByIdSucceed() {
        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(JobInformationDao.builder()
                        .id(1L)
                        .jobTitle("Sales Manager")
                .build()));

        doNothing().when(jobInformationRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) jobInformationService.deleteInformationById(1L).getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(jobInformationRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteJobInformationByIdIsNull() {
        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build()));

        ApiResponse apiResponse = (ApiResponse) jobInformationService.deleteInformationById(null).getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteJobInformationByIdFailed() {
        when(jobInformationRepository.findById(anyLong())).thenReturn(Optional.of(JobInformationDao.builder()
                .id(1L)
                .jobTitle("Sales Manager")
                .build()));

        doThrow(NullPointerException.class).when(jobInformationRepository).delete(any());
        assertThrows(Exception.class, () -> jobInformationService.deleteInformationById(1L));
    }

    @Test
    void testSearchJobInformationByJobTitleSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Manager")
                .build();

        when(jobInformationRepository.findAllByJobTitle(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                        .id(1L)
                        .jobTitle("Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByJobTitle("Manager");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByJobTitleIsEmpty() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .jobTitle("Manager")
                .build();

        when(jobInformationRepository.findAllByJobTitle(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                .id(1L)
                .jobTitle("Manager")
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByJobTitle(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByJobTitleFailed() {
        when(jobInformationRepository.findAllByJobTitle(anyString())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByJobTitle("Manager");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByCategoryNameSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .category(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Technician")
                        .build())
                .build();

        when(jobInformationRepository.findAllByCategoryName(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                .id(1L)
                .category(JobCategoryDto.builder()
                        .id(1L)
                        .categoryName("Technician")
                        .build())
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByCategoryName("Technician");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByCategoryNameIsNull() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .category(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Technician")
                        .build())
                .build();

        when(jobInformationRepository.findAllByCategoryName(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                .id(1L)
                .category(JobCategoryDto.builder()
                        .id(1L)
                        .categoryName("Technician")
                        .build())
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByCategoryName(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByCategoryNameFailed() {
        when(jobInformationRepository.findAllByCategoryName(anyString())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByCategoryName("Technician");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByLocationNameSucceed() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .location(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                        .build())
                .build();

        when(jobInformationRepository.findAllByLocationName(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                .id(1L)
                .location(JobLocationDto.builder()
                        .id(1L)
                        .locationName("Bandung")
                        .build())
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByLocationName("Bandung");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByLocationNameIsNull() {
        JobInformationDao jobInformationDao = JobInformationDao.builder()
                .id(1L)
                .location(JobLocationDao.builder()
                        .id(1L)
                        .locationName("Bandung")
                        .build())
                .build();

        when(jobInformationRepository.findAllByLocationName(anyString())).thenReturn(List.of(jobInformationDao));
        when(modelMapper.map(any(), eq(JobInformationDto.class))).thenReturn(JobInformationDto.builder()
                .id(1L)
                .location(JobLocationDto.builder()
                        .id(1L)
                        .locationName("Bandung")
                        .build())
                .build());

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByLocationName(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testSearchJobInformationByLocationNameFailed() {
        when(jobInformationRepository.findAllByLocationName(anyString())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = jobInformationService.searchJobInformationByLocationName("Bandung");

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }
}