package com.miniproject.keycloakadminclient.mapper;

import com.miniproject.keycloakadminclient.entity.Group;
import com.miniproject.keycloakadminclient.entity.User;
import org.keycloak.representations.idm.GroupRepresentation;

import java.util.UUID;

public class GroupMapper {
    public static Group toEntity(GroupRepresentation groupRepresentation){
        Group group = new Group();
        group.setGroupId(UUID.fromString(groupRepresentation.getId()));
        group.setGroupName(groupRepresentation.getName());

        return group;
    }


}
