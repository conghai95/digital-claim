package com.project.dco.controller;

import com.project.dco.dto.model.Claim;
import com.project.dco.dto.request.CreateClaimRequest;
import com.project.dco.service.ClaimService;
import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(value = "/claims")
@CrossOrigin
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping(value = "/create")
    public AppResponseEntity<?> createNewClaim(@RequestPart("createClaimRequest") String createClaimRequest, @RequestPart("files") MultipartFile[] multipartFile) {
        CreateClaimRequest claimRequest = JsonHelper.convertJson2Object(createClaimRequest, CreateClaimRequest.class);
        Optional<Claim> res = claimService.createNewClaim(claimRequest, multipartFile);
        return AppResponseEntity.withSuccess(res.get());
    }

    @PostMapping(value = "/add")
    public AppResponseEntity<?> addNewClaim(@RequestBody CreateClaimRequest createClaimRequest) {
        return AppResponseEntity.withSuccess(claimService.addNewClaim(createClaimRequest));
    }

    @GetMapping(value = "")
    public AppResponseEntity<?> getClaimsInfo() {
        return AppResponseEntity.withSuccess(claimService.getClaimInfo(952));
    }
}
