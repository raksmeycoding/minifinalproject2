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
import java.util.UUID;


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

    @GetMapping("/user/email")
    public List<User> getAllUserByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/user/{id}")
    public User getUserById (@PathVariable UUID uuid){
        return userService.findById(uuid);
    }

    @PutMapping("/user")
    public User updateUser (@PathVariable UUID uuid,@RequestBody UserRequest userRequest){
        return userService.updateUser(uuid,userRequest);
    }

    @DeleteMapping("/user")
    public void deleteUserById (@PathVariable UUID uuid){
        userService.deleteUser(uuid);
    }
}


//{
//        "userName": "khamann",
//        "password": "1111",
//        "email": "khamann",
//        "fistName": "kha",
//        "lastName": "mann"
//        }

