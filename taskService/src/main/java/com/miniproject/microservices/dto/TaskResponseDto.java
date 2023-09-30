package com.miniproject.microservices.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.miniproject.common.GroupDto;
import com.miniproject.common.UserDto;
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
public class TaskResponseDto {





    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private UserDto createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private UserDto assignTo;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private GroupDto groupId;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Date createdDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Date lastModified;





    //    section 2




    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TaskDto task;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    int status;


}
