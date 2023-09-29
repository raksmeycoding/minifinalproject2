package com.miniproject.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto userDto;


    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private int status;
}
