package com.miniproject.keycloakadminclient.service;

import com.miniproject.common.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface IGroupServiceV1 {
    List<String> getGroupName();

    UserResponseDto addUserToGroup(UUID userId, UUID groupId);
}
