package com.miniproject.microservices.service;


import com.miniproject.microservices.dto.TaskDto;
import com.miniproject.microservices.entity.Task;
import com.miniproject.microservices.exception.NotFoundException;
import com.miniproject.microservices.repository.TaskRepository;
import com.miniproject.microservices.request.TaskRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task saveTask(TaskRequest taskRequest) {
        Task task = taskRequest.toEntity();
        return taskRepository.save(task);
    }


    @Override
    public TaskDto getTaskById(UUID taskId) {
        var findTask = taskRepository.findById(taskId).orElseThrow(() -> {
            throw new NotFoundException("Task no found with id " + taskId, "error");
        }).toDto();
        return findTask;
    }
}
