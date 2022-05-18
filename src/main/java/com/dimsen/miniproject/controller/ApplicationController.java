package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.ApplicationDto;
import com.dimsen.miniproject.service.ApplicationService;
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

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> createApplication(@RequestBody ApplicationDto applicationDto) {
        return applicationService.createApplication(applicationDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> getAllApplications() {
        return applicationService.getAllApplication();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> updateApplicationById(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
        return applicationService.updateApplicationById(id, applicationDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY')")
    public ResponseEntity<Object> deleteApplicationById(@PathVariable Long id) {
        return applicationService.deleteApplicationById(id);
    }
}
