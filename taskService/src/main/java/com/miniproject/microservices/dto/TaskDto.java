package com.miniproject.microservices.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
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


}
