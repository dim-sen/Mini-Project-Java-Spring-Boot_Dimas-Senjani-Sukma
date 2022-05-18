package com.dimsen.miniproject.service;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    public ResponseEntity<Object> hello() {
        log.info("Say Hello to Spring boot");
        return ResponseUtil.build(AppConstant.ResponseCode.HELLO, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> dataNotFound() {
        log.info("Test Response Data not Found");
        return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
    }
}
