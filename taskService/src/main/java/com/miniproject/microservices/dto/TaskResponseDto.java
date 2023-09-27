package com.miniproject.microservices.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.miniproject.microservices.entity.Task;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TaskDto task;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    int status;


}
