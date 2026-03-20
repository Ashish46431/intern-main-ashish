package com.example.demo.controller;

import com.example.demo.dto.CreateEmployeeDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateEmployeeDto;
import com.example.demo.model.enums.Status;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody CreateEmployeeDto createEmployeeDto) {
        return new ResponseEntity<>(employeeService.createDepartment(createEmployeeDto), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody UpdateEmployeeDto updateEmployeeDto) {
        return new ResponseEntity<>(employeeService.update(id, updateEmployeeDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<Map<Status, List<TaskDto>>> getTasks(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getTasks(id),HttpStatus.OK);
    }
}
