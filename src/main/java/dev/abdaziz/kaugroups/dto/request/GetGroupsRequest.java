package dev.abdaziz.kaugroups.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetGroupsRequest {
    @NotBlank
    @Size(min = 2, max = 4)
    private String courseCode;

    @NotNull
    @Min(100)
    @Max(999)
    private Integer courseNumber;
}