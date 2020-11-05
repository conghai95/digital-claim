package com.project.dco.service;

import com.project.dco.dto.model.Claim;
import com.project.dco.dto.request.CreateClaimRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ClaimService {

    Claim createNewClaim(CreateClaimRequest createClaimRequest, MultipartFile[] MultipartFile);
}
