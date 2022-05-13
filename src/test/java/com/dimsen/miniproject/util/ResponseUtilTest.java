package com.dimsen.miniproject.util;

import com.dimsen.miniproject.constant.AppConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

class ResponseUtilTest {

    @Test
    void testResponseUtil() {
        ResponseEntity<Object> response = ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

}