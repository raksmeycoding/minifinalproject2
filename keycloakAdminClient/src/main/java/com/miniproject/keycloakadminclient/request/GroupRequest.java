package com.miniproject.keycloakadminclient.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequest {
    private String groupName;

    public GroupRepresentation toGroupRepresentation(){
        GroupRepresentation groupRepresentation=new GroupRepresentation();
        groupRepresentation.setId(UUID.randomUUID().toString());
        groupRepresentation.setName(groupName);
        return groupRepresentation;
    }

}
