package com.miniproject.keycloakadminclient.mapper;

import com.miniproject.common.UserDto;
import com.miniproject.keycloakadminclient.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import java.util.UUID;

public class UserMapper {

    public static User toEntity(UserRepresentation userRepresentation) {
        User user = new User();
        user.setId(UUID.fromString(userRepresentation.getId()));
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        user.setFirstName(userRepresentation.getFirstName());
        user.setLastName(userRepresentation.getLastName());
//        String school = userRepresentation.getAttributes().get("school").get(0);
//        String subject = userRepresentation.getAttributes().get("subject").get(0);
//        user.setSchool(school);
//        user.setSubject(subject);
        return user;
    }


//    public static UserDto toDto(UserRepresentation userRepresentation) {
//
//
//    }
}
