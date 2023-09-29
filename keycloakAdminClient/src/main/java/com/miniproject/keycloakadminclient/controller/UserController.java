package com.miniproject.keycloakadminclient.controller;

import com.miniproject.keycloakadminclient.entity.User;
import com.miniproject.keycloakadminclient.request.UserRequest;
import com.miniproject.keycloakadminclient.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "miniproject02")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/search")
    public List<User> getAllUser(@RequestParam String username) {
        return userService.findByUsername(username);
    }


    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }
}


//{
//        "userName": "khamann",
//        "password": "1111",
//        "email": "khamann",
//        "fistName": "kha",
//        "lastName": "mann"
//        }

