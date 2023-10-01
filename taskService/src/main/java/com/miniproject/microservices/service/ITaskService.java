package com.miniproject.microservices.service;

import com.miniproject.microservices.dto.TaskDto;
import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.entity.Task;
import com.miniproject.microservices.request.TaskRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    TaskResponseDto saveTask(TaskRequest taskRequest, Jwt jwt);
    TaskResponseDto getTaskById(UUID taskId, Jwt jwt);


    List<TaskResponseDto> getAllTask(Jwt jwt);


    String deleteTaskById(UUID taskId, Jwt jwt);




}
