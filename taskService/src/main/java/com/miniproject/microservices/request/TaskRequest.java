package com.miniproject.microservices.request;


import com.miniproject.microservices.entity.Task;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;


    private UUID userId;



    public Task toEntity() {
        return Task.builder()
                .id(null)
                .name(this.name)
                .date(this.date)
                .userId(this.userId)
                .build();
    }
}
