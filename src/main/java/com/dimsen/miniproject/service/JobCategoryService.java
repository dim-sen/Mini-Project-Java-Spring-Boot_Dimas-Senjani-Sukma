package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.repository.JobCategoryRepository;
import com.dimsen.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createCategory(JobCategoryDto jobCategoryDto) {
        log.info("Creating new Job Category");
        try {
            log.info("Save new category");
            JobCategoryDao jobCategoryDao = modelMapper.map(jobCategoryDto, JobCategoryDao.class);
            jobCategoryRepository.save(jobCategoryDao);

            JobCategoryDto categoryDto = modelMapper.map(jobCategoryDao, JobCategoryDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new category. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getCategoryById(Long id) {
        log.info("Getting a category by id");
        try {
            log.info("Find by id");
            Optional<JobCategoryDao> optionalJobCategoryDao = jobCategoryRepository.findById(id);
            if (optionalJobCategoryDao.isEmpty()) {
                log.info("Category not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Category found");
            JobCategoryDto categoryDto = modelMapper.map(optionalJobCategoryDao.get(), JobCategoryDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, categoryDto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in getting a category by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllCategory() {
        log.info("Getting all categories");
        try {
            log.info("Get categories in list form");
            List<JobCategoryDao> categories;
            List<JobCategoryDto> jobCategoryDtoList = new ArrayList<>();

            categories = jobCategoryRepository.findAll();

            for (JobCategoryDao jobCategoryDao : categories) {
                jobCategoryDtoList.add(modelMapper.map(jobCategoryDao, JobCategoryDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobCategoryDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all categories. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateCategoryById(Long id, JobCategoryDto jobCategoryDto) {
        log.info("Updating a category by id");
        try {
            log.info("Find by id");
            Optional<JobCategoryDao> optionalJobCategoryDao = jobCategoryRepository.findById(id);
            if (optionalJobCategoryDao.isEmpty()) {
                log.info("Category not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Category found");
            JobCategoryDao jobCategoryDao = optionalJobCategoryDao.get();
            jobCategoryDao.setCategoryName(jobCategoryDto.getCategoryName());
            jobCategoryRepository.save(jobCategoryDao);

            JobCategoryDto categoryDto = modelMapper.map(jobCategoryDao, JobCategoryDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating a category by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteCategoryById(Long id) {
        log.info("Deleting a category by id");
        try {
            log.info("Delete by id");
            Optional<JobCategoryDao> optionalJobCategoryDao = jobCategoryRepository.findById(id);
            if (optionalJobCategoryDao.isEmpty()) {
                log.info("Category not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Category found");
            jobCategoryRepository.delete(optionalJobCategoryDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting a category by id. Error: {}", e.getMessage());
            throw e;
        }
    }
}
