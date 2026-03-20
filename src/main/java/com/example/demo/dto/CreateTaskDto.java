package com.example.demo.dto;

import com.example.demo.model.enums.Priority;
import com.example.demo.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {

    @NotBlank
    private String taskTitle;

    @NotBlank
    private String taskDescription;

    private Priority taskPriority;

    private Status taskStatus;

    @NotNull
    private LocalDate taskDueDate;

    @NotNull
    private Long employeeId;

    private String channel;
}
