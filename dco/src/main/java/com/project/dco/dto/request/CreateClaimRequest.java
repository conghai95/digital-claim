package com.project.dco.dto.request;

import lombok.Data;

@Data
public class CreateClaimRequest {

    String claimTitle;
    String claimContent;
    String claimFile;
    String userEndIdentifier;
    String userPhone;
    String userEmail;
    String userAddress;
    Integer userClaimsId;
    Integer userEndId;
}
