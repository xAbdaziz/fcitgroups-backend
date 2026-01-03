package dev.abdaziz.kaugroups.controller;

import dev.abdaziz.kaugroups.dto.request.*;
import dev.abdaziz.kaugroups.dto.response.*;
import dev.abdaziz.kaugroups.model.Group;
import dev.abdaziz.kaugroups.model.User;
import dev.abdaziz.kaugroups.service.GroupService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/")
    public ResponseEntity<AddGroupResponse> addGroup(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody AddGroupRequest request
    ) {
        Group group = groupService.addGroup(user, request);
        AddGroupResponse response = AddGroupResponse.from(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<GetGroupsResponse> getGroups(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody GetGroupsRequest request
    ) {
        List<Group> groups = groupService.getGroups(user, request);
        GetGroupsResponse response = GetGroupsResponse.from(groups);
        return ResponseEntity.ok(response);
    }
}
