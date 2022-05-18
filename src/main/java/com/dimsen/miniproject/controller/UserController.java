package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get a user by an id", notes = "Returns a user as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUserById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "Get all users", notes = "Returns all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUser();
    }

    @ApiOperation(value = "Update a user by an id", notes = "Returns an updated user as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }

    @ApiOperation(value = "Delete a user by an id", notes = "Returns deleted status a user as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
