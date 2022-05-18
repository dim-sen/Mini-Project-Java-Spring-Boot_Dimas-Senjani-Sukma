package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.ProfileTypeDto;
import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.repository.ProfileTypeRepository;
import com.dimsen.miniproject.repository.UserRepository;
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
public class ProfileTypeService {

    @Autowired
    private ProfileTypeRepository profileTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createProfileType(ProfileTypeDto profileTypeDto) {
        log.info("Creating profile type");
        try {
            log.info("Save new profile type");
            Optional<UserDao> optionalUserDao = userRepository.findById(profileTypeDto.getUserId());
            if (optionalUserDao.isEmpty()) {
                log.info("User not found");
                ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("User found");
            UserDao userDao = optionalUserDao.get();
            userRepository.save(userDao);

            UserDto dto = UserDto.builder()
                    .id(userDao.getId())
                    .username(userDao.getUsername())
                    .password(userDao.getPassword())
                    .role(userDao.getRole())
                    .accountStatus(userDao.getAccountStatus())
                    .build();

            ProfileTypeDao profileTypeDao = ProfileTypeDao.builder()
                    .userId(profileTypeDto.getUserId())
                    .name(profileTypeDto.getName())
                    .gender(profileTypeDto.getGender())
                    .address(profileTypeDto.getAddress())
                    .contact(profileTypeDto.getContact())
                    .website(profileTypeDto.getWebsite())
                    .email(profileTypeDto.getEmail())
                    .education(profileTypeDto.getEducation())
                    .professionalSummary(profileTypeDto.getProfessionalSummary())
                    .profileImage(profileTypeDto.getProfileImage())
                    .user(userDao)
                    .build();
            profileTypeRepository.save(profileTypeDao);

            ProfileTypeDto typeDto = ProfileTypeDto.builder()
                    .userId(profileTypeDao.getUserId())
                    .name(profileTypeDao.getName())
                    .gender(profileTypeDao.getGender())
                    .address(profileTypeDao.getAddress())
                    .contact(profileTypeDao.getContact())
                    .website(profileTypeDao.getWebsite())
                    .email(profileTypeDao.getEmail())
                    .education(profileTypeDao.getEducation())
                    .professionalSummary(profileTypeDao.getProfessionalSummary())
                    .profileImage(profileTypeDao.getProfileImage())
                    .user(dto)
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new profile type. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getProfileTypeById(Long id) {
        log.info("Getting a profile type by id");
        try {
            log.info("Find by id");
            Optional<ProfileTypeDao> optionalProfileTypeDao = profileTypeRepository.findById(id);
            if (optionalProfileTypeDao.isEmpty()) {
                log.info("Profile Type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Profile type found");
            Optional<UserDao> userDao = userRepository.findById(optionalProfileTypeDao.get().getUserId());

            UserDto dto = modelMapper.map(userDao.get(), UserDto.class);

            ProfileTypeDto typeDto = ProfileTypeDto.builder()
                    .userId(optionalProfileTypeDao.get().getUserId())
                    .name(optionalProfileTypeDao.get().getName())
                    .gender(optionalProfileTypeDao.get().getGender())
                    .address(optionalProfileTypeDao.get().getAddress())
                    .contact(optionalProfileTypeDao.get().getContact())
                    .website(optionalProfileTypeDao.get().getWebsite())
                    .email(optionalProfileTypeDao.get().getEmail())
                    .education(optionalProfileTypeDao.get().getEducation())
                    .professionalSummary(optionalProfileTypeDao.get().getProfessionalSummary())
                    .profileImage(optionalProfileTypeDao.get().getProfileImage())
                    .user(dto)
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a profile type by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllProfileType() {
        log.info("Getting all profile types");
        try {
            log.info("Get profile types in list");

            List<ProfileTypeDao> profileTypeDaos;
            List<ProfileTypeDto> profileTypeDtoList = new ArrayList<>();

            profileTypeDaos = profileTypeRepository.findAll();

            for (ProfileTypeDao profileTypeDao : profileTypeDaos) {
                Optional<UserDao> userDao = userRepository.findById(profileTypeDao.getUserId());

                UserDto dto = modelMapper.map(userDao.get(), UserDto.class);

                profileTypeDtoList.add(ProfileTypeDto.builder()
                                .userId(profileTypeDao.getUserId())
                                .name(profileTypeDao.getName())
                                .gender(profileTypeDao.getGender())
                                .address(profileTypeDao.getAddress())
                                .contact(profileTypeDao.getContact())
                                .website(profileTypeDao.getWebsite())
                                .email(profileTypeDao.getEmail())
                                .education(profileTypeDao.getEducation())
                                .professionalSummary(profileTypeDao.getProfessionalSummary())
                                .profileImage(profileTypeDao.getProfileImage())
                                .user(dto)
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, profileTypeDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all profile types. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateProfileTypeById(Long id, ProfileTypeDto profileTypeDto) {
        log.info("Updating a profile type by id");
        try {
            log.info("Find by id");
            Optional<ProfileTypeDao> optionalProfileTypeDao = profileTypeRepository.findById(id);
            if (optionalProfileTypeDao.isEmpty()) {
                log.info("Profile type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Profile type found");
            ProfileTypeDao profileTypeDao = optionalProfileTypeDao.get();
            profileTypeDao.setName(profileTypeDto.getName());
            profileTypeDao.setGender(profileTypeDto.getGender());
            profileTypeDao.setAddress(profileTypeDto.getAddress());
            profileTypeDao.setContact(profileTypeDto.getContact());
            profileTypeDao.setWebsite(profileTypeDto.getWebsite());
            profileTypeDao.setEmail(profileTypeDto.getEmail());
            profileTypeDao.setEducation(profileTypeDto.getEducation());
            profileTypeDao.setProfessionalSummary(profileTypeDto.getProfessionalSummary());
            profileTypeDao.setProfileImage(profileTypeDto.getProfileImage());
            profileTypeRepository.save(profileTypeDao);

            Optional<UserDao> userDao = userRepository.findById(optionalProfileTypeDao.get().getUserId());

            UserDto dto = modelMapper.map(userDao.get(), UserDto.class);

            ProfileTypeDto typeDto = ProfileTypeDto.builder()
                    .userId(profileTypeDao.getUserId())
                    .name(profileTypeDao.getName())
                    .gender(profileTypeDao.getGender())
                    .address(profileTypeDao.getAddress())
                    .contact(profileTypeDao.getContact())
                    .website(profileTypeDao.getWebsite())
                    .email(profileTypeDao.getEmail())
                    .education(profileTypeDao.getEducation())
                    .professionalSummary(profileTypeDao.getProfessionalSummary())
                    .profileImage(profileTypeDao.getProfileImage())
                    .user(dto)
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating profile type by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteProfileTypeById(Long id) {
        log.info("Deleting a profile type by id");
        try {
            log.info("Delete by id");
            Optional<ProfileTypeDao> optionalProfileTypeDao = profileTypeRepository.findById(id);
            if (optionalProfileTypeDao.isEmpty()) {
                log.info("Profile type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Profile type found");
            profileTypeRepository.delete(optionalProfileTypeDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting a profile type by id. Error: {}", e.getMessage());
            throw e;
        }
    }
}
