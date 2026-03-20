package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

    @NotBlank
    private String employeeName;

    @Email
    private String employeeEmail;

    @NotBlank
    private String employeeDesignation;

    @NonNull
    private LocalDate employeeJoinedDate;

    @NotNull
    private Long departmentId;
}
