package com.example.demo.service;

import com.example.demo.dto.CreateTaskDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateStausDto;
import com.example.demo.dto.UpdateTaskDto;
import com.example.demo.model.enums.Status;
import jakarta.validation.Valid;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAll();

    TaskDto getById(Long id);

    TaskDto createDepartment(@Valid CreateTaskDto taskDto);

    TaskDto update(Long id, UpdateTaskDto updateTaskDto);

    String delete(Long id);

    String updateStatus(UpdateStausDto updateStausDto);
}
