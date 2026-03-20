package com.example.demo.util;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Task;

public class CommonUtil {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .employeeEmail(employee.getEmail())
                .employeeDesignation(employee.getDesignation())
                .employeeJoinedDate(employee.getJoinedDate())
                .build();
    }

    public static TaskDto mapToTaskDto(Task task) {
        return TaskDto.builder()
                .taskId(task.getId())
                .taskTitle(task.getTitle())
                .taskPriority(task.getPriority())
                .taskDueDate(task.getDueDate())
                .taskAssignedAt(task.getAssignedAt())
                .taskStatus(task.getStatus())
                .taskDescription(task.getDescription())
                .employee(CommonUtil.mapToEmployeeDto(task.getEmployee()))
                .build();
    }
}
