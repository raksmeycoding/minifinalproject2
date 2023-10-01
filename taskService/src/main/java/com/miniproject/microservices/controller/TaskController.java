package com.miniproject.microservices.controller;


import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.request.TaskRequest;
import com.miniproject.microservices.service.ITaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@RefreshScope
@SecurityRequirement(name = "miniproject02")
public class TaskController {


    private final ITaskService taskService;


    @GetMapping("{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID taskId, @AuthenticationPrincipal Jwt jwt) {
        var getTask = taskService.getTaskById(taskId, jwt);
        return ResponseEntity.status(HttpStatus.OK).body(getTask);
    }


    @PostMapping
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt) {
        var taskResponseDto = taskService.saveTask(taskRequest, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);

    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTask(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTask(jwt));

    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable UUID taskId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTaskById(taskId, jwt));

    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTaskById(@PathVariable UUID taskId, @RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(taskId, taskRequest, jwt));
    }


}
