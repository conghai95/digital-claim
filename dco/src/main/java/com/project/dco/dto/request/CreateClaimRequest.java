package com.project.dco.dto.request;

import lombok.Data;

@Data
public class CreateClaimRequest {

    String claimTitle;
    String claimContent;
    String template;
    String userEndIdentifier;
    Integer userEndId;
    Integer userClaimId;
    String status;
}
