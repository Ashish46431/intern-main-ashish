package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDepartmentDto {

    private String departmentName;

    private String departmentLocation;
}
