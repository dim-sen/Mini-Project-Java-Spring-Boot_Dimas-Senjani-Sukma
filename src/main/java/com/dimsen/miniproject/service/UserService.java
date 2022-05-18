package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dto.UserDto;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createUser(UserDto userDto) {
        log.info("Creating new User");
        try {
            log.info("Save new user");
            UserDao userDao = UserDao.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .role(userDto.getRole())
                    .accountStatus(userDto.getAccountStatus())
                    .build();
            userRepository.save(userDao);

            UserDto dto = modelMapper.map(userDao, UserDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new user. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getUserById(Long id) {
        log.info("Getting a user by id");
        try {
            log.info("Find by id");
            Optional<UserDao> optionalUserDao = userRepository.findById(id);
            if (optionalUserDao.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("User found");
            UserDto userDto = modelMapper.map(optionalUserDao.get(), UserDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, userDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting user by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllUser() {
        log.info("Getting all users");
        try {
            log.info("Get all users in list");
            List<UserDao> userDaos;
            List<UserDto> userDtoList = new ArrayList<>();

            userDaos = userRepository.findAll();

            for (UserDao userDao : userDaos) {
                userDtoList.add(modelMapper.map(userDao, UserDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, userDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all users. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateUserById(Long id, UserDto userDto) {
        log.info("Updating user by id");
        try {
            log.info("Find by id");
            Optional<UserDao> optionalUserDao = userRepository.findById(id);
            if (optionalUserDao.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("User found");
            UserDao userDao = optionalUserDao.get();
            userDao.setUsername(userDto.getUsername());
            userDao.setPassword(userDto.getPassword());
//            userDao.setRole();
//            userDao.setAccountStatus();
            userRepository.save(userDao);

            UserDto dto = modelMapper.map(userDao, UserDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating user by id. Error: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteUserById(Long id) {
        log.info("Deleting user by id");
        try {
            log.info("Delete by id");
            Optional<UserDao> optionalUserDao = userRepository.findById(id);
            if (optionalUserDao.isEmpty()) {
                log.info("User found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("User found");
            userRepository.delete(optionalUserDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting user by id. Error: {}", e.getMessage());
            throw e;
        }
    }
}
