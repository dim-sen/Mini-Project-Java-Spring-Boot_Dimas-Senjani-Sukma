package com.dimsen.miniproject.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileTypeDto {

    private Long userId;

    private String name;

    private String gender;

    private String address;

    private String contact;

    private String website;

    private String email;

    private String education;

    private String professionalSummary;

    private String profileImage;

    private UserDto user;

}
