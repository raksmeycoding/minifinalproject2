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
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().searchByEmail(email, false);
        System.out.println(userRepresentations);
        return userRepresentations.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }

    public User findById(UUID userId) {
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(String.valueOf(userId)).toRepresentation();
        return UserMapper.toEntity(userRepresentation);

    }

    public User updateUser(UUID userId, UserRequest userRequest) {

        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(String.valueOf(userId)).toRepresentation();

        userRepresentation.setUsername(userRequest.getUserName());
        userRepresentation.setFirstName(userRequest.getFistName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.setEmail(userRequest.getEmail());

        keycloak.realm(realm).users().get(String.valueOf(userId)).update(userRepresentation);

        return UserMapper.toEntity(userRepresentation);

    }

    public void deleteUser(UUID userId) {
        keycloak.realm(realm).users().delete(String.valueOf(userId));
    }
}

