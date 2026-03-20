package com.example.demo.dto;

import com.example.demo.model.entity.Employee;
import com.example.demo.model.enums.Priority;
import com.example.demo.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private Priority taskPriority;
    private Status taskStatus;
    private LocalDate taskDueDate;
    private LocalDateTime taskAssignedAt;
    private EmployeeDto employee;
}
