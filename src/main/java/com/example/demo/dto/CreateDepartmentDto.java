package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateDepartmentDto {

    @NotBlank
    private String departmentName;

    @NotBlank
    private String departmentLocation;
}
