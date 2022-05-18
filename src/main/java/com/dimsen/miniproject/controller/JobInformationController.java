package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobInformationDto;
import com.dimsen.miniproject.service.JobInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/information", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobInformationController {

    @Autowired
    private JobInformationService jobInformationService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createJobInformation(@RequestBody JobInformationDto jobInformationDto) {
        return jobInformationService.createInformation(jobInformationDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getJobInformationById(@PathVariable Long id) {
        return jobInformationService.getInformationById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllJobInformation() {
        return jobInformationService.getAllInformation();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateJobInformationById(@PathVariable Long id, @RequestBody JobInformationDto jobInformationDto) {
        return jobInformationService.updateInformationById(id, jobInformationDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteJobInformationById(@PathVariable Long id) {
        return jobInformationService.deleteInformationById(id);
    }

    @GetMapping(value = "/search")
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
