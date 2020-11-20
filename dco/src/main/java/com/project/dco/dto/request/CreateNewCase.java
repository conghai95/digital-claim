package com.project.dco.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewCase {

    @JsonProperty(value = "pro_uid")
    String projectUid;

    @JsonProperty(value = "tas_uid")
    String taskUid;

    Object[] variables;
}
