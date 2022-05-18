package com.dimsen.miniproject.domain.dto;

import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import com.dimsen.miniproject.domain.dao.JobLocationDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JobInformationDto {

    private Long id;

    private String jobTitle;

    private String description;

    private Long salary;

    private String jobStatus;

    private LocalDateTime postingDate;

    private LocalDateTime lastApplicationDate;

    private JobCategoryDto category;

    private JobLocationDto location;

    private UserDto company;

}
