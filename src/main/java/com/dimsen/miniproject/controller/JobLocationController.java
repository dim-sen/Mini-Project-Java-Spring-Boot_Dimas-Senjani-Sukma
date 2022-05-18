package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobLocationDto;
import com.dimsen.miniproject.service.JobLocationService;
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

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createJobLocation(@RequestBody JobLocationDto jobLocationDto) {
        return jobLocationService.createLocation(jobLocationDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getJobLocationById(@PathVariable Long id) {
        return jobLocationService.getLocationById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllJobLocations() {
        return jobLocationService.getAllLocation();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateJobLocationById(@PathVariable Long id, @RequestBody JobLocationDto jobLocationDto) {
        return jobLocationService.updateLocationById(id, jobLocationDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteJobLocationById(@PathVariable Long id) {
        return jobLocationService.deleteALocationById(id);
    }
}
