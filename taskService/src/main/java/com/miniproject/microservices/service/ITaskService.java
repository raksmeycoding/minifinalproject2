package com.miniproject.microservices.service;

import com.miniproject.microservices.dto.TaskDto;
import com.miniproject.microservices.entity.Task;
import com.miniproject.microservices.request.TaskRequest;

import java.util.UUID;

public interface ITaskService {
    Task saveTask(TaskRequest taskRequest);

    TaskDto getTaskById(UUID taskId);



}
