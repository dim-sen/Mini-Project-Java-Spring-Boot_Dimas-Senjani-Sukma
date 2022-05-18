package com.dimsen.miniproject.controller;

import com.dimsen.miniproject.domain.dto.JobCategoryDto;
import com.dimsen.miniproject.service.JobCategoryService;
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
@RequestMapping(value = "/v1/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @ApiOperation(value = "Create new category", notes = "Returns save a category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createCategory(@RequestBody JobCategoryDto jobCategoryDto) {
        return jobCategoryService.createCategory(jobCategoryDto);
    }

    @ApiOperation(value = "Get a category by an id", notes = "Returns a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getCategoryById(@PathVariable @ApiParam(name = "id", value = "id", example = "1") Long id) {
        return jobCategoryService.getCategoryById(id);
    }

    @ApiOperation(value = "Get all categories", notes = "Returns all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllCategories() {
        return jobCategoryService.getAllCategory();
    }

    @ApiOperation(value = "Update a category by an id", notes = "Returns a updated category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateACategoryById(@PathVariable Long id, @RequestBody JobCategoryDto categoryDto) {
        return jobCategoryService.updateCategoryById(id, categoryDto);
    }

    @ApiOperation(value = "Delete a category by an id", notes = "Returns deleted status a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteACategoryById(@PathVariable Long id) {
        return jobCategoryService.deleteCategoryById(id);
    }
}
