package dev.abdaziz.kaugroups.dto.response;

import dev.abdaziz.kaugroups.model.Group;

import java.util.UUID;

public record AddGroupResponse(
    UUID id,
    String link
) {
    public static AddGroupResponse from(Group group) {
        return new AddGroupResponse(group.getId(), group.getLink());
    }
}


