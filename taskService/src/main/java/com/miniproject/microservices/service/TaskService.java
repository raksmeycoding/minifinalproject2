package com.miniproject.microservices.service;


import com.miniproject.common.GroupDto;
import com.miniproject.common.UserDto;
import com.miniproject.microservices.dto.TaskDto;
import com.miniproject.microservices.dto.TaskResponseDto;
import com.miniproject.microservices.entity.Task;
import com.miniproject.microservices.exception.NotFoundException;
import com.miniproject.microservices.repository.TaskRepository;
import com.miniproject.microservices.request.TaskRequest;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public List<TaskResponseDto> getAllTasks(Jwt jwt) {
        List<TaskResponseDto> findAllTasks = new ArrayList<>();
        List<TaskDto> taskDtos = taskRepository.findAll().stream().map(Task::toDto).toList();
        System.out.println(taskDtos);
        for (TaskDto taskDto:
             taskDtos) {
           findAllTasks.add(TaskResponseDto.builder()
                   .id(taskDto.getId())
                   .title(taskDto.getTitle())
                   .description(taskDto.getDescription())
                   .createdBy(getUserById(taskDto.getCreatedBy(), jwt).block())
                   .assignTo(getUserById(taskDto.getAssignTo(), jwt).block())
                   .groupId(getGroupById(taskDto.getGroupId(), jwt).block())
                   .lastModified(taskDto.getLastModified())
                   .createdDate(taskDto.getCreateDate()).build());
        }
        return findAllTasks;
    }

    @Override
    public TaskResponseDto deleteTask(UUID id, Jwt jwt) {
        taskRepository.deleteById(id);
        return  TaskResponseDto.builder().
                status(200).
                message("Delete task successfully.").
                build();
    }

    @Override
    public TaskDto updateTask(UUID id,TaskRequest taskRequest, Jwt jwt) {

        Task task = taskRepository.findById(id).get();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCreatedBy(taskRequest.getCreateBy());
        task.setAssignTo(taskRequest.getAssignTo());
        task.setGroupId(taskRequest.getGroupId());
        task.setCreateDate(taskRequest.toEntity().getCreateDate());
        task.setLastModified(taskRequest.toEntity().getLastModified());

        Task taskResponse = taskRepository.save(task);

        TaskDto dto = new TaskDto();

        dto.setId(taskResponse.getId());
        dto.setTitle(taskResponse.getTitle());
        dto.setDescription(taskResponse.getDescription());
        dto.setCreatedBy(taskResponse.getCreatedBy());
        dto.setAssignTo(taskResponse.getAssignTo());
        dto.setGroupId(taskResponse.getGroupId());
        dto.setCreateDate(taskResponse.getCreateDate());
        dto.setLastModified(taskResponse.getLastModified());

        return dto;
    }


}
