package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.JobInformationDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.JobInformationDto;
import com.dimsen.miniproject.repository.JobInformationRepository;
import com.dimsen.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobInformationService {

    @Autowired
    private JobInformationRepository jobInformationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createInformation(JobInformationDto jobInformationDto) {
        log.info("Creating new Job Information");

        try {
            log.info("Save new information");
            JobInformationDao jobInformationDao = modelMapper.map(jobInformationDto, JobInformationDao.class);
            jobInformationRepository.save(jobInformationDao);

            JobInformationDto informationDto = modelMapper.map(jobInformationDao, JobInformationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, informationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new information. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getInformationById(Long id) {
        log.info("Getting an information by id");
        try {
            log.info("Find by id");
            Optional<JobInformationDao> optionalJobInformationDao = jobInformationRepository.findById(id);
            if (optionalJobInformationDao.isEmpty()) {
                log.info("Information not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Information found");
            JobInformationDto informationDto = modelMapper.map(optionalJobInformationDao.get(), JobInformationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, informationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting an information by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllInformation() {
        log.info("Getting all information");
        try {
            log.info("Get information");
            List<JobInformationDao> information;
            List<JobInformationDto> jobInformationDtoList = new ArrayList<>();

            information = jobInformationRepository.findAll();

            for (JobInformationDao jobInformationDao : information) {
                jobInformationDtoList.add(modelMapper.map(jobInformationDao, JobInformationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobInformationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all information. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateInformationById(Long id, JobInformationDto jobInformationDto) {
        log.info("Updating an information by id");
        try {
            log.info("Find by id");
            Optional<JobInformationDao> optionalJobInformationDao = jobInformationRepository.findById(id);
            if (optionalJobInformationDao.isEmpty()) {
                log.info("Information not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Information found");
            JobInformationDao jobInformationDao = optionalJobInformationDao.get();
            jobInformationDao.setJobTitle(jobInformationDto.getJobTitle());
            jobInformationDao.setDescription(jobInformationDto.getDescription());
            jobInformationDao.setSalary(jobInformationDto.getSalary());
            jobInformationDao.setJobStatus(jobInformationDto.getJobStatus());
            jobInformationDao.setPostingDate(jobInformationDto.getPostingDate());
            jobInformationDao.setLastApplicationDate(jobInformationDto.getLastApplicationDate());
            jobInformationRepository.save(jobInformationDao);

            JobInformationDto informationDto = modelMapper.map(jobInformationDao, JobInformationDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, informationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating an information by id. Error: {}", e.getMessage());
            throw  e;
        }
    }

    public ResponseEntity<Object> deleteInformationById(Long id) {
        log.info("Deleting an information by id");
        try {
            log.info("Delete by id");
            Optional<JobInformationDao> optionalJobInformationDao = jobInformationRepository.findById(id);
            if (optionalJobInformationDao.isEmpty()) {
                log.info("Information found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Information found");
            jobInformationRepository.delete(optionalJobInformationDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting an information by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> searchJobInformationByJobTitle(String jobTitle) {
        log.info("Search by job title");
        try {
            log.info("Searching for job information by job title: [{}]", jobTitle);
            List<JobInformationDao> jobInformationDaos;
            List<JobInformationDto> jobInformationDtoList = new ArrayList<>();

            jobInformationDaos = jobInformationRepository.findAllByJobTitle(jobTitle);
            if (jobInformationDaos.isEmpty()) {
                log.info("Job title not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Job title found");
            for (JobInformationDao jobInformationDao : jobInformationDaos) {
                jobInformationDtoList.add(modelMapper.map(jobInformationDao, JobInformationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobInformationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in searching for job information by job title. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchJobInformationByCategoryName(String categoryName) {
        log.info("Search by category name");
        try {
            log.info("Searching for job information by category name: [{}]", categoryName);
            List<JobInformationDao> informationDaos;
            List<JobInformationDto> informationDtoList = new ArrayList<>();

            informationDaos = jobInformationRepository.findAllByCategoryName(categoryName);
            if (informationDaos.isEmpty()) {
                log.info("Category name not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Category name found");
            for (JobInformationDao jobInformationDao : informationDaos) {
                informationDtoList.add(modelMapper.map(jobInformationDao, JobInformationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, informationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in searching for job information by category name. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchJobInformationByLocationName(String locationName) {
        log.info("Search by location name");
        try {
            log.info("Searching for job information by location name: [{}]", locationName);
            List<JobInformationDao> jobInformationDaos;
            List<JobInformationDto> jobInformationDtoList = new ArrayList<>();

            jobInformationDaos = jobInformationRepository.findAllByLocationName(locationName);
            if (jobInformationDaos.isEmpty()) {
                log.info("Location name not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Location name found");
            for (JobInformationDao jobInformationDao : jobInformationDaos) {
                jobInformationDtoList.add(modelMapper.map(jobInformationDao, JobInformationDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, jobInformationDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in searching for job information by location name: Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<Object> searchJobInformationByCompanyName(String companyName) {
//        log.info("Search by company name");
//        try {
//            log.info("Searching for job information by company name: [{}]", companyName);
//            UserDao userDao;
//
//            if (userDao.getRole().equals(AppConstant.UserRole.COMPANY)) {
//
//            }
//
//        } catch (Exception e) {
//            log.error("An error occurred in searching for job information by company name. Error: {}", e.getMessage());
//            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
