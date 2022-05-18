package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobInformationDto;
import com.dimsen.miniproject.service.JobInformationService;
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
@RequestMapping(value = "/v1/information", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobInformationController {

    @Autowired
    private JobInformationService jobInformationService;

    @ApiOperation(value = "Create new information", notes = "Returns save an information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> createJobInformation(@RequestBody JobInformationDto jobInformationDto) {
        return jobInformationService.createInformation(jobInformationDto);
    }

    @ApiOperation(value = "Get an information by an id", notes = "Returns an information as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> getJobInformationById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return jobInformationService.getInformationById(id);
    }

    @ApiOperation(value = "Get all information", notes = "Returns all information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> getAllJobInformation() {
        return jobInformationService.getAllInformation();
    }

    @ApiOperation(value = "Update an information by an id", notes = "Returns an updated information as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> updateJobInformationById(@PathVariable Long id, @RequestBody JobInformationDto jobInformationDto) {
        return jobInformationService.updateInformationById(id, jobInformationDto);
    }

    @ApiOperation(value = "Delete an information by an id", notes = "Returns deleted status an updated information as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> deleteJobInformationById(@PathVariable Long id) {
        return jobInformationService.deleteInformationById(id);
    }

    @ApiOperation(value = "Search an information by an id", notes = "Returns a list of information as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/search")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> searchJobInformation(@RequestParam(value = "location_name", required = false) String locationName,
                                                       @RequestParam(value = "job_title", required = false) String jobTitle,
                                                       @RequestParam(value = "category_name", required = false) String categoryName) {
        if (jobTitle != null) {
            return jobInformationService.searchJobInformationByJobTitle(jobTitle);
        }

        if (locationName != null) {
            return jobInformationService.searchJobInformationByLocationName(locationName);
        }

        if (categoryName != null) {
            return jobInformationService.searchJobInformationByCategoryName(categoryName);
        }

        return jobInformationService.getAllInformation();
    }
}
