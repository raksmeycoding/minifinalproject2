package com.miniproject.keycloakadminclient.service;

import com.miniproject.keycloakadminclient.entity.User;
import com.miniproject.keycloakadminclient.mapper.UserMapper;
import com.miniproject.keycloakadminclient.request.UserRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public UserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public List<User> getAllUsers() {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().list();
        return userRepresentations.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<User> findByUsername(String username) {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(username);
        return userRepresentations.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }


    public User createUser(UserRequest userRequest) {
//        try {
            var userRepresentation = userRequest.toUserRepresentation();
            keycloak.realm(realm).users().create(userRepresentation);
            return UserMapper.toEntity(userRepresentation);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }

    }

    public List<User> findByEmail(String email) {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(email);
        return userRepresentations.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }

    public User findById(UUID uuid) {
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(String.valueOf(uuid)).toRepresentation();
        return UserMapper.toEntity(userRepresentation);

    }

    public User updateUser(UUID uuid, UserRequest userRequest) {

        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(String.valueOf(uuid)).toRepresentation();
        UserRepresentation updateUserRepresentation = new UserRepresentation();

        updateUserRepresentation.setUsername(userRequest.getUserName());
        updateUserRepresentation.setFirstName(userRequest.getFistName());
        updateUserRepresentation.setLastName(userRequest.getLastName());
        updateUserRepresentation.setEmail(userRequest.getEmail());

        keycloak.realm(realm).users().get(String.valueOf(uuid)).update(userRepresentation);

        return UserMapper.toEntity(userRepresentation);

    }

    public void deleteUser(UUID uuid) {
        keycloak.realm(realm).users().get(String.valueOf(uuid)).toRepresentation();

    }
}

