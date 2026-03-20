package com.example.demo.service;

import com.example.demo.dto.CreateEmployeeDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateEmployeeDto;
import com.example.demo.model.enums.Status;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto getById(Long id);

    EmployeeDto createDepartment(CreateEmployeeDto employeeDto);

    EmployeeDto update(Long id, UpdateEmployeeDto updateEmployeeDto);

    String delete(Long id);

    Map<Status, List<TaskDto>> getTasks(Long id);
}
