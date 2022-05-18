package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.ApplicationDao;
import com.dimsen.miniproject.domain.dto.ApplicationDto;
import com.dimsen.miniproject.repository.ApplicationRepository;
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
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createApplication(ApplicationDto applicationDto) {
        log.info("Creating new application");
        try {
            log.info("Save new application");
            ApplicationDao applicationDao = modelMapper.map(applicationDto, ApplicationDao.class);
            applicationRepository.save(applicationDao);

            ApplicationDto dto = modelMapper.map(applicationDao, ApplicationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new application. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getApplicationById(Long id) {
        log.info("Getting an information by id");
        try {
            log.info("Find by id");
            Optional<ApplicationDao> optionalApplicationDao = applicationRepository.findById(id);
            if (optionalApplicationDao.isEmpty()) {
                log.info("Application not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Application found");
            ApplicationDto applicationDto = modelMapper.map(optionalApplicationDao.get(), ApplicationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, applicationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting an information by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllApplication() {
        log.info("Getting all applications");
        try {
            log.info("Get application in list");
            List<ApplicationDao> applicationDaos;
            List<ApplicationDto> applicationDtoList = new ArrayList<>();

            applicationDaos = applicationRepository.findAll();

            for (ApplicationDao applicationDao : applicationDaos) {
                applicationDtoList.add(modelMapper.map(applicationDao, ApplicationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, applicationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all applications");
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateApplicationById(Long id, ApplicationDto applicationDto) {
        log.info("Updating an applcation by id");
        try {
            log.info("Find by id");
            Optional<ApplicationDao> optionalApplicationDao = applicationRepository.findById(id);
            if (optionalApplicationDao.isEmpty()) {
                log.info("Application not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Application found");
            ApplicationDao applicationDao = optionalApplicationDao.get();
            applicationDao.setApplicationStatus(applicationDto.getApplicationStatus());
            applicationDao.setDateSubmitted(applicationDto.getDateSubmitted());
            applicationRepository.save(applicationDao);

            ApplicationDto dto = modelMapper.map(applicationDao, ApplicationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating an information by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteApplicationById(Long id) {
        log.info("Deleting an information by id");
        try {
            log.info("Delete by id");
            Optional<ApplicationDao> optionalApplicationDao = applicationRepository.findById(id);
            if (optionalApplicationDao.isEmpty()) {
                log.info("Application not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Application found");
            applicationRepository.delete(optionalApplicationDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting an application by id. Error: {}", e.getMessage());
            throw e;
        }
    }
}
