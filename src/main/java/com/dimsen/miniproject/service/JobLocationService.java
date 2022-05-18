package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import com.dimsen.miniproject.domain.dao.JobLocationDao;
import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.domain.dto.JobLocationDto;
import com.dimsen.miniproject.repository.JobLocationRepository;
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
public class JobLocationService {

    @Autowired
    private JobLocationRepository jobLocationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createLocation(JobLocationDto jobLocationDto) {
        log.info("Creating new Job Location");
        try {
            log.info("Save new location");
            JobLocationDao jobLocationDao = modelMapper.map(jobLocationDto, JobLocationDao.class);
            jobLocationRepository.save(jobLocationDao);

            JobLocationDto locationDto = modelMapper.map(jobLocationDao, JobLocationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, locationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new location. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getLocationById(Long id) {
        log.info("Getting a location by id");
        try {
            log.info("Find by id");
            Optional<JobLocationDao> optionalJobLocationDao = jobLocationRepository.findById(id);
            if (optionalJobLocationDao.isEmpty()) {
                log.info("Location not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Location found");
            JobLocationDto jobLocationDto = modelMapper.map(optionalJobLocationDao.get(), JobLocationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobLocationDto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in getting a location by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllLocation() {
        log.info("Getting all Locations");
        try {
            log.info("Get location in list form");
            List<JobLocationDao> locations;
            List<JobLocationDto> jobLocationDtoList = new ArrayList<>();

            locations = jobLocationRepository.findAll();

            for (JobLocationDao jobLocationDao : locations) {
                jobLocationDtoList.add(modelMapper.map(jobLocationDao, JobLocationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobLocationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all locations. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateLocationById(Long id, JobLocationDto jobLocationDto) {
        log.info("Updating a location by id");
        try {
            log.info("Find by id");
            Optional<JobLocationDao> optionalJobLocationDao = jobLocationRepository.findById(id);
            if (optionalJobLocationDao.isEmpty()) {
                log.info("Location not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Location found");
            JobLocationDao jobLocationDao = optionalJobLocationDao.get();
            jobLocationDao.setLocationName(jobLocationDto.getLocationName());
            jobLocationRepository.save(jobLocationDao);

            JobLocationDto locationDto = modelMapper.map(optionalJobLocationDao.get(), JobLocationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, locationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating a location by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteALocationById(Long id) {
        log.info("Deleting a location by id");
        try {
            log.info("Delete by id");
            Optional<JobLocationDao> optionalJobLocationDao = jobLocationRepository.findById(id);
            if (optionalJobLocationDao.isEmpty()) {
                log.info("Location not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Location found");
            jobLocationRepository.delete(optionalJobLocationDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting a location by id. Error: {}", e.getMessage());
            throw e;
        }
    }
}
