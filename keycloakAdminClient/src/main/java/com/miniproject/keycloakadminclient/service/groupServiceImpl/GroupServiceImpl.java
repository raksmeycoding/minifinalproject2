package com.miniproject.keycloakadminclient.service.groupServiceImpl;

import com.miniproject.common.UserResponseDto;
import com.miniproject.keycloakadminclient.service.IGroupServiceV1;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class GroupServiceImpl implements IGroupServiceV1 {


    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    public GroupServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public List<String> getGroupName() {

        RealmResource realmResource = keycloak.realm(this.realm);
        List<GroupRepresentation> groupResourceList = realmResource.groups().groups();
        return groupResourceList.stream()
                .map(GroupRepresentation::getName)
                .collect(Collectors.toList());

    }


    @Override
    public UserResponseDto addUserToGroup(UUID userId, UUID groupId) {
        RealmResource realmResource = keycloak.realm(this.realm);
//        Get user resource
        UserResource userResource = realmResource.users().get(String.valueOf(userId));

//        add user to group
        userResource.joinGroup(String.valueOf(groupId));
        return UserResponseDto.builder()
                .message("Added user to group successfully.")
                .build();
    }
}

