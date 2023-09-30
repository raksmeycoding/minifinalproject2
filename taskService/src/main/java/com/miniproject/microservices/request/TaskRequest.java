package com.miniproject.microservices.request;


import com.miniproject.microservices.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    private String title;

    private String description;


    private UUID createBy;


    private UUID assignTo;


    private UUID groupId;


    public Task toEntity() {
        var FIRST_TASK_CREATED_DATE = new Date(System.currentTimeMillis());
        return Task.builder()
                .id(null)
                .title(this.title)
                .description(this.description)
                .createdBy(this.createBy)
                .assignTo(this.assignTo)
                .groupId(this.groupId)
                .lastModified(FIRST_TASK_CREATED_DATE)
                .createDate(FIRST_TASK_CREATED_DATE)
                .build();
    }
}
