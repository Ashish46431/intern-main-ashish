package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Task;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeTaskRepository;
import com.example.demo.service.TaskService;
import com.example.demo.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final EmployeeTaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationServiceClient notificationServiceClient;

    @Override
    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(CommonUtil::mapToTaskDto).toList();
    }

    @Override
    public TaskDto getById(Long id) {
        return CommonUtil.mapToTaskDto(taskRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task not found with id: " + id))
        );
    }

    @Override
    @Transactional
    public TaskDto createDepartment(CreateTaskDto taskDto) {
        Employee employee = employeeRepository.findById(taskDto.getEmployeeId()).orElseThrow(() -> new NotFoundException("Employee not found with id: " + taskDto.getEmployeeId()));
        Task task = new Task();
        task.setTitle(taskDto.getTaskTitle());
        task.setDueDate(taskDto.getTaskDueDate());
        task.setAssignedAt(LocalDateTime.now());
        task.setEmployee(employee);
        task.setDescription(taskDto.getTaskDescription());
        task.setPriority(taskDto.getTaskPriority());
        task.setStatus(taskDto.getTaskStatus());
        notificationCall(taskDto.getChannel(),taskDto.getEmployeeId());
        return CommonUtil.mapToTaskDto(taskRepository.save(task));
    }

    @Async
    public void notificationCall(String channel,Long employeeId) {
        if(channel.equals("SMS")){
            NotificationDto dto = notificationServiceClient.createNotificationSms(channel,employeeId);
            log.info("Notification Status received: {}", dto);
        }else if(channel.equals("PUSH")){
            NotificationDto dto =notificationServiceClient.createNotificationPush(channel,employeeId);
        }

    }

    @Override
    @Transactional
    public TaskDto update(Long id, UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        if (updateTaskDto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(updateTaskDto.getEmployeeId()).orElseThrow(() -> new NotFoundException("Employee not found with id: " + updateTaskDto.getEmployeeId()));
            task.setEmployee(employee);
        }
        if (updateTaskDto.getTaskTitle() != null || !updateTaskDto.getTaskTitle().isEmpty()) {
            task.setTitle(updateTaskDto.getTaskTitle());
        }
        if (updateTaskDto.getTaskDueDate() != null) {
            task.setDueDate(updateTaskDto.getTaskDueDate());
        }
        if (updateTaskDto.getTaskPriority() != null) {
            task.setPriority(updateTaskDto.getTaskPriority());
        }
        if (updateTaskDto.getTaskDescription() != null || !updateTaskDto.getTaskDescription().isEmpty()) {
            task.setDescription(updateTaskDto.getTaskDescription());
        }
        return CommonUtil.mapToTaskDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        if (Boolean.TRUE.equals(task.getDeleted())) {
            return "Task Already Deleted";
        }
        task.setDeleted(true);
        task.setActive(false);
        taskRepository.save(task);
        return "task Deleted Successfully";
    }

    @Override
    public String updateStatus(UpdateStausDto status) {
        Task task = taskRepository.findById(status.getId()).orElseThrow(() -> new NotFoundException("Task not found with id: " + status.getId()));
        if (Status.DONE.equals(task.getStatus())) {
            return "Task is already done";
        }
        task.setStatus(status.getStatus());
        taskRepository.save(task);
        return "Task Status Updated Successfully";
    }
}
