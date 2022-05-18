package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.ProfileTypeDto;
import com.dimsen.miniproject.service.ProfileTypeService;
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

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMPANY') or hasRole('ROLE_APPLICAN')")
    public ResponseEntity<Object> createProfileType(@RequestBody ProfileTypeDto profileTypeDto) {
        return profileTypeService.createProfileType(profileTypeDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getProfleTypeById(@PathVariable Long id) {
        return profileTypeService.getProfileTypeById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllProfileType() {
        return profileTypeService.getAllProfileType();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateProfileTypeById(@PathVariable Long id, @RequestBody ProfileTypeDto profileTypeDto) {
        return profileTypeService.updateProfileTypeById(id, profileTypeDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteProfileTypeById(@PathVariable Long id) {
        return profileTypeService.deleteProfileTypeById(id);
    }

}
