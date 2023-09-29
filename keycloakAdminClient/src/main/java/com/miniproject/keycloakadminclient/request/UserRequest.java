package com.miniproject.keycloakadminclient.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String userName;
    private String password;
    private String email;

    private String fistName;
    private String lastName;


    public UserRepresentation toUserRepresentation() {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(UUID.randomUUID().toString());
        userRepresentation.setUsername(userName);
        userRepresentation.setEmail(this.email);
        userRepresentation.setFirstName(this.fistName);
        userRepresentation.setLastName(this.lastName);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(this.password);


        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;

    }
}
