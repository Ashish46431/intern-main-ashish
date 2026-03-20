package com.example.demo.service.impl;

import com.example.demo.dto.CreateDepartmentDto;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateDepartmentDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Task;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeTaskRepository;
import com.example.demo.service.DepartmentService;
import com.example.demo.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;
    private final EmployeeTaskRepository employeeTaskRepository;

    @Override
    public List<DepartmentDto> getAll() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream().map(this::mapToDto).toList();
    }

    @Override
    public DepartmentDto getById(Long id) {
        return departmentRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(
                        () -> new NotFoundException("Department not found with id: " + id)
                );
    }

    @Override
    @Transactional
    public DepartmentDto createDepartment(CreateDepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getDepartmentName());
        department.setLocation(departmentDto.getDepartmentLocation());
        return mapToDto(departmentRepository.save(department));
    }

    @Override
    @Transactional
    public DepartmentDto update(Long id, UpdateDepartmentDto updateDepartmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Department not found with id: " + id)
                );
        if (updateDepartmentDto.getDepartmentName() != null && !updateDepartmentDto.getDepartmentName().isEmpty()) {
            department.setName(updateDepartmentDto.getDepartmentName());
        }
        if (updateDepartmentDto.getDepartmentLocation() != null && !updateDepartmentDto.getDepartmentLocation().isEmpty()) {
            department.setLocation(updateDepartmentDto.getDepartmentLocation());
        }
        return mapToDto(departmentRepository.save(department));
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id)
                );
        if (Boolean.TRUE.equals(department.getDeleted())) {
            return "Department already deleted";
        }
        department.setActive(false);
        department.setDeleted(true);
        departmentRepository.save(department);
        return "Department deleted Successfully";
    }

    @Override
    public Map<Status, List<TaskDto>> getTasks(Long id) {
        List<Employee> employee = employeeRepository.findByDepartmentId(id);
            List<Task> tasks = employee.stream().map(this::findTask).flatMap(List::stream).toList();
        return tasks.stream()
                    .sorted(Comparator.comparing(Task::getDueDate)
                            .thenComparing(Comparator.comparing(Task::getPriority).reversed()))
                    .map(CommonUtil::mapToTaskDto)
                    .collect(Collectors.groupingBy(
                            TaskDto::getTaskStatus,
                            Collectors.toList())
                    );
    }

    private List<Task> findTask(Employee employee) {
        return employeeTaskRepository.findByEmployeeId(employee.getId());

    }


    private DepartmentDto mapToDto(Department department) {
        return DepartmentDto.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .departmentLocation(department.getLocation())
                .build();
    }
}
