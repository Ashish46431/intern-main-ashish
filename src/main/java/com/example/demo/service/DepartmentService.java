package com.example.demo.service;

import com.example.demo.dto.CreateDepartmentDto;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateDepartmentDto;
import com.example.demo.model.enums.Status;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    List<DepartmentDto> getAll();

    DepartmentDto getById(Long id);

    DepartmentDto createDepartment(@Valid CreateDepartmentDto departmentDto);

    DepartmentDto update(Long id, UpdateDepartmentDto updateDepartmentDto);

    String delete(Long id);

    Map<Status, List<TaskDto>> getTasks(Long id);
}
