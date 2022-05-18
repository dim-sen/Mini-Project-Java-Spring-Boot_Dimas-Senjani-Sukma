package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.ApplicationDto;
import com.dimsen.miniproject.service.ApplicationService;
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
@RequestMapping(value = "/v1/application", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation(value = "Create new application", notes = "Returns save an application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> createApplication(@RequestBody ApplicationDto applicationDto) {
        return applicationService.createApplication(applicationDto);
    }

    @ApiOperation(value = "Get an application by an id", notes = "Returns an application as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> getApplicationById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return applicationService.getApplicationById(id);
    }

    @ApiOperation(value = "Get all application", notes = "Returns all application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> getAllApplications() {
        return applicationService.getAllApplication();
    }

    @ApiOperation(value = "Update an application by an id", notes = "Returns an updated application as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> updateApplicationById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id, @RequestBody ApplicationDto applicationDto) {
        return applicationService.updateApplicationById(id, applicationDto);
    }

    @ApiOperation(value = "Delete an application by an id", notes = "Returns deleted status an application as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> deleteApplicationById(@PathVariable Long id) {
        return applicationService.deleteApplicationById(id);
    }
}
