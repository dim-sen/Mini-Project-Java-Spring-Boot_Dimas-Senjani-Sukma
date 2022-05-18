package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.ProfileTypeDto;
import com.dimsen.miniproject.service.ProfileTypeService;
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
@RequestMapping(value = "/v1/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileTypeController {

    @Autowired
    private ProfileTypeService profileTypeService;

    @ApiOperation(value = "Create new profile", notes = "Returns save a profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> createProfileType(@RequestBody ProfileTypeDto profileTypeDto) {
        return profileTypeService.createProfileType(profileTypeDto);
    }

    @ApiOperation(value = "Get a profile by an id", notes = "Returns a profile as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getProfleTypeById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return profileTypeService.getProfileTypeById(id);
    }

    @ApiOperation(value = "Get all profiles", notes = "Returns all profiles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllProfileType() {
        return profileTypeService.getAllProfileType();
    }

    @ApiOperation(value = "Update a profile by an id", notes = "Returns an updated profile as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateProfileTypeById(@PathVariable Long id, @RequestBody ProfileTypeDto profileTypeDto) {
        return profileTypeService.updateProfileTypeById(id, profileTypeDto);
    }

    @ApiOperation(value = "Delete a profile by an id", notes = "Returns deleted status a profile as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteProfileTypeById(@PathVariable Long id) {
        return profileTypeService.deleteProfileTypeById(id);
    }

}
