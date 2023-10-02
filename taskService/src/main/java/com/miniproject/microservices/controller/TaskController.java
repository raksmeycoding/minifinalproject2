package com.miniproject.microservices.controller;


import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.entity.Task;
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
@RequestMapping("/api/v1/tasks")
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

    @GetMapping
    public ResponseEntity<?> getAllTasks(@AuthenticationPrincipal Jwt jwt){
    List<TaskResponseDto> getAllTask = taskService.getAllTasks(jwt);
    return ResponseEntity.status(HttpStatus.OK).body(getAllTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TaskResponseDto> deleteTask (@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt){
      var taskResponseDto =taskService.deleteTask(id,jwt);
      return ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskRequest taskRequest,@AuthenticationPrincipal Jwt jwt){
        var updateTask = taskService.updateTask(id,taskRequest,jwt);
        return ResponseEntity.status(HttpStatus.OK).body(updateTask);
    }

}
