package com.example.demo.service.impl;

import com.example.demo.dto.CreateEmployeeDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateEmployeeDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Task;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeTaskRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeTaskRepository employeeTaskRepository;

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(CommonUtil::mapToEmployeeDto).toList();
    }

    @Override
    public EmployeeDto getById(Long id) {
        return CommonUtil.mapToEmployeeDto(employeeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Employee not found with id: " + id)
                ));
    }

    @Override
    @Transactional
    public EmployeeDto createDepartment(CreateEmployeeDto employeeDto) {
        Department department = departmentRepository.findById(employeeDto.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department not found with id: " + employeeDto.getDepartmentId()));
        Employee employee = new Employee();
        employee.setName(employeeDto.getEmployeeName());
        employee.setDepartment(department);
        employee.setDesignation(employeeDto.getEmployeeDesignation());
        employee.setEmail(employeeDto.getEmployeeEmail());
        employee.setJoinedDate(employeeDto.getEmployeeJoinedDate());
        return CommonUtil.mapToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeDto update(Long id, UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        if (updateEmployeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(updateEmployeeDto.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department not found with id: " + updateEmployeeDto.getDepartmentId()));
            employee.setDepartment(department);
        }
        if (updateEmployeeDto.getEmployeeName() != null && !updateEmployeeDto.getEmployeeName().isEmpty()) {
            employee.setName(updateEmployeeDto.getEmployeeName());
        }
        if (updateEmployeeDto.getEmployeeDesignation() != null && !updateEmployeeDto.getEmployeeDesignation().isEmpty()) {
            employee.setDesignation(updateEmployeeDto.getEmployeeDesignation());
        }
        if (updateEmployeeDto.getEmployeeEmail() != null && !updateEmployeeDto.getEmployeeEmail().isEmpty()) {
            employee.setEmail(updateEmployeeDto.getEmployeeEmail());
        }
        return CommonUtil.mapToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        if (Boolean.TRUE.equals(employee.getDeleted())) {
            return "Employee already deleted";
        }
        employee.setDeleted(true);
        employee.setActive(false);
        employeeRepository.save(employee);
        return "Employee deleted Successfully";
    }

    @Override
    public Map<Status, List<TaskDto>> getTasks(Long id) {
        List<Task> tasks= employeeTaskRepository.findByEmployeeId(id);
        return tasks.stream().map(CommonUtil::mapToTaskDto).collect(Collectors.groupingBy(TaskDto::getTaskStatus, Collectors.toList()));
    }

}
