package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.ApiResponse;
import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.repository.JobCategoryRepository;
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
@SpringBootTest(classes = JobCategoryService.class)
class JobCategoryServiceTest {

    @MockBean
    private JobCategoryRepository jobCategoryRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private JobCategoryService jobCategoryService;

    @Test
    void testCreateJobCategorySucceed() {
        JobCategoryDao categoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();

        JobCategoryDto categoryDto = JobCategoryDto.builder()
                .id(1L)
                .categoryName("Manager")
                .build();

        when(modelMapper.map(any(), eq(JobCategoryDao.class))).thenReturn(categoryDao);
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(categoryDto);

        ResponseEntity<Object> response = jobCategoryService.createCategory(JobCategoryDto.builder()
                        .categoryName("Manager")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        JobCategoryDto jobCategoryDto = (JobCategoryDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, jobCategoryDto.getId());
        assertEquals("Manager", jobCategoryDto.getCategoryName());
    }

    @Test
    void testCreateJobCategoryFailed() {
        when(jobCategoryRepository.save(any())).thenThrow(NullPointerException.class);

        ApiResponse response = (ApiResponse) jobCategoryService.createCategory(JobCategoryDto.builder()
                        .categoryName("Manager")
                .build()).getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(response).getStatus().getCode());
    }

    @Test
    void testGetJobCategoryByIdSucceed() {
        JobCategoryDao jobCategoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();

        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(jobCategoryDao));
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(JobCategoryDto.builder()
                .id(1L)
                .categoryName("Manager")
                .build());

        ResponseEntity<Object> response = jobCategoryService.getCategoryById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobCategoryByIdIsNull() {
        JobCategoryDao jobCategoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();

        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(jobCategoryDao));
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(JobCategoryDto.builder()
                .id(1L)
                .categoryName("Manager")
                .build());

        ResponseEntity<Object> response = jobCategoryService.getCategoryById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testGetJobCategoryByIdFailed() {
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Manager")
                .build()));

        doThrow(NullPointerException.class).when(jobCategoryRepository).findById(any());
        assertThrows(Exception.class, () -> jobCategoryService.getCategoryById(1L));
    }

    @Test
    void testGetAllJobCategoriesSucceed() {
        JobCategoryDao jobCategoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();

        when(jobCategoryRepository.findAll()).thenReturn(List.of(jobCategoryDao));
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(JobCategoryDto.builder()
                        .id(1L)
                        .categoryName("Manager")
                .build());

        ResponseEntity<Object> response = jobCategoryService.getAllCategory();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());

    }

    @Test
    void testGetAllJobCategoriesFailed() {
        when(jobCategoryRepository.findAll()).thenThrow(NullPointerException.class);

        ApiResponse apiResponse = (ApiResponse) jobCategoryService.getAllCategory().getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobCategoryByIdSucceed() {
        JobCategoryDao jobCategoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(jobCategoryDao));
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(JobCategoryDto.builder()
                        .id(1L)
                        .categoryName("Technician")
                .build());
        ResponseEntity<Object> response = jobCategoryService.updateCategoryById(1L, JobCategoryDto.builder()
                        .categoryName("Technician")
                .build());
        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobCategoryByIdIsNull() {
        JobCategoryDao jobCategoryDao = JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build();
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(jobCategoryDao));
        when(modelMapper.map(any(), eq(JobCategoryDto.class))).thenReturn(JobCategoryDto.builder()
                .id(1L)
                .categoryName("Manager")
                .build());
        ResponseEntity<Object> response = jobCategoryService.updateCategoryById(null, null);
        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testUpdateJobCategoryByIdFailed() {
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Manager")
                .build()));
        doThrow(NullPointerException.class).when(jobCategoryRepository).findById(any());
        assertThrows(Exception.class, () -> jobCategoryService.updateCategoryById(1L, JobCategoryDto.builder()
                        .categoryName("Technician")
                .build()));
    }

    @Test
    void testDeleteJobCategoryByIdSucceed() {
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Manager")
                .build()));
        doNothing().when(jobCategoryRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) jobCategoryService.deleteCategoryById(1L).getBody();
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        verify(jobCategoryRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteJobCategoryByIdIsNull() {
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(JobCategoryDao.builder()
                .id(1L)
                .categoryName("Manager")
                .build()));

        ApiResponse apiResponse = (ApiResponse) jobCategoryService.deleteCategoryById(null).getBody();
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void testDeleteJobCategoryByIdFailed() {
        when(jobCategoryRepository.findById(anyLong())).thenReturn(Optional.of(JobCategoryDao.builder()
                        .id(1L)
                        .categoryName("Manager")
                .build()));
        doThrow(NullPointerException.class).when(jobCategoryRepository).delete(any());
        assertThrows(Exception.class, () -> jobCategoryService.deleteCategoryById(1L));
    }
}