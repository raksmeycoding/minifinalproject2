package com.miniproject.microservices.service;


import com.miniproject.common.GroupDto;
import com.miniproject.common.UserDto;
import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.entity.Task;
import com.miniproject.microservices.exception.NotFoundException;
import com.miniproject.microservices.repository.TaskRepository;
import com.miniproject.microservices.request.TaskRequest;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    @LoadBalanced
    private final WebClient.Builder webClient;

    @Override
    public TaskResponseDto saveTask(TaskRequest taskRequest, Jwt jwt) {
        Task task = taskRequest.toEntity();
        var savedTask = taskRepository.save(task);
        return TaskResponseDto.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .createdBy(getUserById(taskRequest.getCreateBy(), jwt).block())
                .assignTo(getUserById(taskRequest.getAssignTo(), jwt).block())
                .groupId(getGroupById(taskRequest.getGroupId(), jwt).block())
                .createdDate(savedTask.getCreateDate())
                .lastModified(savedTask.getLastModified())
                .message("Create task successfully")
                .status(HttpStatus.CREATED.value())
                .build();
    }


    private Mono<GroupDto> getGroupById(UUID groupId, Jwt jwt) {
        return webClient
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(jwt.getTokenValue()))
                .build()
                .get()
                .uri("http://localhost:9002/keycloak-admin-client/api/v1/groups/" + groupId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            throw new NotFoundException("NotFound", "Please check your arguments, because this is client error.");
                        })
                .bodyToMono(GroupDto.class);
    }


    private Mono<UserDto> getUserById(UUID userId, Jwt jwt) {
        return webClient
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(jwt.getTokenValue()))
                .build()
                .get()
                .uri("http://localhost:9002/keycloak-admin-client/api/v1/user/" + userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            throw new NotFoundException("NotFound", "Please check your arguments, because this is client error.");
                        })
                .bodyToMono(UserDto.class);
    }

    @Override
    public TaskResponseDto getTaskById(UUID taskId, Jwt jwt) {
        var findTask = taskRepository.findById(taskId).orElseThrow(() -> {
            throw new NotFoundException("Task no found with id " + taskId, "error");
        }).toDto();

        System.out.println(findTask);

        return TaskResponseDto.builder()
                .id(findTask.getId())
                .title(findTask.getTitle())
                .description(findTask.getDescription())
                .createdBy(getUserById(findTask.getCreatedBy(), jwt).block())
                .assignTo(getUserById(findTask.getAssignTo(), jwt).block())
                .groupId(getGroupById(findTask.getGroupId(), jwt).block())
                .lastModified(findTask.getLastModified())
                .createdDate(findTask.getCreateDate())
                .status(200)
                .message("Get task successfully.")
                .build();
    }


    @Override
    public List<TaskResponseDto> getAllTask(Jwt jwt) {
        var tasks = taskRepository.findAll();
        System.out.println(tasks);
        return tasks.stream().map(task -> {
            return TaskResponseDto.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .createdBy(getUserById(task.getCreatedBy(), jwt).block())
                    .assignTo(getUserById(task.getAssignTo(), jwt).block())
                    .groupId(getGroupById(task.getGroupId(), jwt).block())
                    .lastModified(task.getLastModified())
                    .createdDate(task.getCreateDate())
                    .build();
        }).collect(Collectors.toList());
    }


    @Override
    public String deleteTaskById(UUID taskId, Jwt jwt) {
            if(!taskRepository.existsById(taskId)){
                throw new NotFoundException("TaskNotFound", "Task not found with id=" + taskId);
            }
            taskRepository.deleteById(taskId);
            return "Task deleted successfully";
    }


    @Override
    public TaskResponseDto updateTaskById(UUID taskId, TaskRequest taskRequest, Jwt jwt) {
        Task existTask = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("TaskNotFound", "Task is not found with id=" + taskId));
        existTask.setTitle(taskRequest.getTitle());
        existTask.setDescription(taskRequest.getDescription());
        existTask.setLastModified(new Date(System.currentTimeMillis()));
        existTask.setGroupId(taskRequest.getGroupId());
        existTask.setAssignTo(taskRequest.getAssignTo());
        return  TaskResponseDto.builder()
                .id(existTask.getId())
                .title(existTask.getTitle())
                .description(existTask.getDescription())
                .message("Task has been updated successfully")
                .status(200)
                .lastModified(existTask.getLastModified())
                .createdBy(getUserById(taskRequest.getCreateBy(), jwt).block())
                .assignTo(getUserById(taskRequest.getAssignTo(), jwt).block())
                .createdDate(existTask.getCreateDate()).build();
    }
}


