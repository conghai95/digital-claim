package com.project.dco.service;

import com.project.dco.dto.model.Claim;
import com.project.dco.dto.request.CreateClaimRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ClaimService {

    Optional<Claim> createNewClaim(CreateClaimRequest createClaimRequest, MultipartFile[] MultipartFile);

    String getClaimInfo(Integer id);

    Optional<CreateClaimRequest> addNewClaim(CreateClaimRequest createClaimRequest);
}
