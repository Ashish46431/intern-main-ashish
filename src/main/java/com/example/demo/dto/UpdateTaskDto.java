package com.example.demo.dto;

import com.example.demo.model.enums.Priority;
import com.example.demo.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {
    private String taskTitle;
    private String taskDescription;
    private Priority taskPriority;
    private LocalDate taskDueDate;
    private Long employeeId;
}

