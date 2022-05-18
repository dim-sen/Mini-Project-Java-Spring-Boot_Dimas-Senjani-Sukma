package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobLocationDto;
import com.dimsen.miniproject.service.JobLocationService;
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
@RequestMapping(value = "/v1/location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobLocationController {

    @Autowired
    private JobLocationService jobLocationService;


    @ApiOperation(value = "Create new location", notes = "Returns save a location")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createJobLocation(@RequestBody JobLocationDto jobLocationDto) {
        return jobLocationService.createLocation(jobLocationDto);
    }

    @ApiOperation(value = "Get an location by an id", notes = "Returns an location as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getJobLocationById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return jobLocationService.getLocationById(id);
    }

    @ApiOperation(value = "Get all locations", notes = "Returns all locations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllJobLocations() {
        return jobLocationService.getAllLocation();
    }

    @ApiOperation(value = "Update a location by an id", notes = "Returns an updated location as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateJobLocationById(@PathVariable Long id, @RequestBody JobLocationDto jobLocationDto) {
        return jobLocationService.updateLocationById(id, jobLocationDto);
    }

    @ApiOperation(value = "Delete a location by an id", notes = "Returns deleted status a location as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteJobLocationById(@PathVariable Long id) {
        return jobLocationService.deleteALocationById(id);
    }
}
