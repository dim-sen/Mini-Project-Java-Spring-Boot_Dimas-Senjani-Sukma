package com.dimsen.miniproject.domain.dto;

import com.dimsen.miniproject.domain.dao.JobInformationDao;
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
public class ApplicationDto {

    private Long id;

    private String applicationStatus;

    private LocalDateTime dateSubmitted;

    private JobInformationDto information;

    private UserDto applicant;

}
