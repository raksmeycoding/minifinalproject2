package com.miniproject.microservices.controller;


import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.request.TaskRequest;
import com.miniproject.microservices.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {


    private final ITaskService taskService;


    @GetMapping("{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID taskId) {
        var getTask = taskService.getTaskById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(
                TaskResponseDto.builder()
                        .task(getTask)
                        .message("Get task successfully by id=" + taskId)
                        .status(HttpStatus.OK.value())
                        .build()
        );


    }


    @PostMapping
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequest taskRequest) {
        var saveTask = taskService.saveTask(taskRequest).toDto();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        TaskResponseDto.builder()
                                .task(saveTask)
                                .message("Task was saved successfully")
                                .status(200)
                                .build()
                );


    }

}
