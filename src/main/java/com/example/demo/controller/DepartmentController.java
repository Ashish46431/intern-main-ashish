package com.example.demo.controller;

import com.example.demo.dto.CreateDepartmentDto;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateDepartmentDto;
import com.example.demo.model.enums.Status;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return new ResponseEntity<>(departmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> create(@Valid @RequestBody CreateDepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<DepartmentDto> update(@PathVariable Long id, @RequestBody UpdateDepartmentDto updateDepartmentDto) {
        return new ResponseEntity<>(departmentService.update(id, updateDepartmentDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.delete(id), HttpStatus.OK);
    }

    @GetMapping("{id}/tasks/overdue")
    public ResponseEntity<Map<Status,List<TaskDto>>> getOverdueTasks(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.getTasks(id),HttpStatus.OK);
    }
}
