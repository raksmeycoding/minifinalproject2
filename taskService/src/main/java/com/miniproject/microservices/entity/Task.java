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
    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;


    private UUID userId;

    public TaskDto toDto() {
        return TaskDto.builder()
                .id(this.id)
                .name(this.name)
                .date(this.date)
                .userId(this.userId)
                .build();
    }
}
