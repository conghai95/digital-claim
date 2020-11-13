package com.project.dco.dto.response;

import lombok.Data;

@Data
public class ClaimInfoResponse {

    String claimId;
    String customerDNI;
    String claimTitle;
    String status;
    String date;
}
