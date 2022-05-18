package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.UserDto;
import com.dimsen.miniproject.service.UserService;
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

//    @PostMapping(value = "")
//    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
//        return userService.createUser(userDto);
//    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUser();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
