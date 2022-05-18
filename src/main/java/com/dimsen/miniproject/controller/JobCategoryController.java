package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createCategory(@RequestBody JobCategoryDto jobCategoryDto) {
        return jobCategoryService.createCategory(jobCategoryDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        return jobCategoryService.getCategoryById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllCategories() {
        return jobCategoryService.getAllCategory();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateACategoryById(@PathVariable Long id, @RequestBody JobCategoryDto categoryDto) {
        return jobCategoryService.updateCategoryById(id, categoryDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteACategoryById(@PathVariable Long id) {
        return jobCategoryService.deleteCategoryById(id);
    }
}
