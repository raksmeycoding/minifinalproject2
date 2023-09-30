package com.miniproject.microservices.entity;


import com.miniproject.microservices.dto.TaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "task_tb")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;

    private String description;

    private UUID createdBy;

    private UUID assignTo;


    private UUID groupId;


    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Temporal(TemporalType.DATE)

    private Date lastModified;

    public TaskDto toDto() {
        return TaskDto.builder()
                .id(this.id)
                .title(this.title)
                .assignTo(this.assignTo)
                .description(this.description)
                .lastModified(this.lastModified)
                .createDate(this.createDate)
                .createdBy(this.createdBy)
                .groupId(this.groupId)
                .build();
    }
}
