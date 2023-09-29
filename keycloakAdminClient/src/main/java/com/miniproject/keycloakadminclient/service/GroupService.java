package com.miniproject.keycloakadminclient.service;

import com.miniproject.keycloakadminclient.entity.Group;
import com.miniproject.keycloakadminclient.entity.User;
import com.miniproject.keycloakadminclient.mapper.GroupMapper;
import com.miniproject.keycloakadminclient.mapper.UserMapper;
import com.miniproject.keycloakadminclient.request.GroupRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public List<Group> getAllGroups() {
        List<GroupRepresentation> groupRepresentations = keycloak.realm(realm).groups().groups();
        return groupRepresentations.stream().map(GroupMapper::toEntity).collect(Collectors.toList());

    }
    public Group findGroupById(UUID id) {
        GroupRepresentation groupRepresentation = keycloak.realm(realm).groups().group(id.toString()).toRepresentation();
        return GroupMapper.toEntity(groupRepresentation);
    }

    public void deleteById(UUID id) {
        keycloak.realm(realm).groups().group(id.toString()).remove();
    }


    public Group createGroup(GroupRequest groupRequest) {
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(groupRequest.getGroupName());
        keycloak.realm(realm).groups().add(groupRepresentation);
      return GroupMapper.toEntity(groupRepresentation);
    }

    public void addUser(UUID userId, UUID groupId) {
        keycloak.realm(realm).users().get(userId.toString()).joinGroup(groupId.toString());
    }

    public List<User> getAllUser(UUID groupId) {
        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupsResource = realmResource.groups();
        GroupResource groupResource = groupsResource.group(groupId.toString());
        List<UserRepresentation> groupRepresentation = groupResource.members();
        return groupRepresentation.stream().map(UserMapper::toEntity).collect(Collectors.toList());

    }
}
