package com.miniproject.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String school;
    private String subject;
}
