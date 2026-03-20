package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.enums.Status;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAll() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskDto> create(@Valid @RequestBody CreateTaskDto taskDto) {
        return new ResponseEntity<>(taskService.createDepartment(taskDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> getTaskStatus(@RequestBody UpdateStausDto updateStausDto) {
        return new ResponseEntity<>(taskService.updateStatus(updateStausDto),HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id, @RequestBody UpdateTaskDto updateTaskDto) {
        return new ResponseEntity<>(taskService.update(id, updateTaskDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.delete(id), HttpStatus.OK);
    }

}
