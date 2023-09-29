package com.miniproject.keycloakadminclient.controller;

import com.miniproject.keycloakadminclient.entity.Group;
import com.miniproject.keycloakadminclient.entity.User;
import com.miniproject.keycloakadminclient.request.GroupRequest;
import com.miniproject.keycloakadminclient.service.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "miniproject02")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/groups")
    public List<Group> getAllGroup (){
        return groupService.getAllGroups();
    }

    @GetMapping("/groups/{id}")
    public Group getGroupById(@PathVariable UUID id){
        return groupService.findGroupById(id);
    }

    @DeleteMapping("/groups/{id}")
    public void deleteGroupById(@PathVariable UUID id){
        groupService.deleteById(id);
    }

    @PostMapping("/groups")
    public  ResponseEntity<?>  createGroup(@RequestBody GroupRequest groupRequest){
        return ResponseEntity.ok(groupService.createGroup(groupRequest));
    }

    @PostMapping("/groups/{groupId}/users/{userId}")
    public void addUser(@PathVariable UUID userId, @PathVariable UUID groupId){
         groupService.addUser(userId,groupId);
    }

    @GetMapping("/groups/{groupId}/users")
    public List<User> getUserInGroup(@PathVariable UUID groupId){
        return groupService.getAllUser(groupId);
    }
}
