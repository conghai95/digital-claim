package com.project.dco.controller;

import com.project.dco.dto.model.Claim;
import com.project.dco.dto.request.CreateClaimRequest;
import com.project.dco.service.ClaimService;
import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping(value = "/create")
    public AppResponseEntity<?> createNewClaim(@RequestPart("createClaimRequest") String createClaimRequest, @RequestPart("files") MultipartFile[] multipartFile) {
        CreateClaimRequest claimRequest = JsonHelper.convertJson2Object(createClaimRequest, CreateClaimRequest.class);
        Claim res = claimService.createNewClaim(claimRequest, multipartFile);
        return AppResponseEntity.withSuccess(res);
    }

    @PostMapping(value = "/add")
    public AppResponseEntity<?> addNewClaim(@RequestBody Claim claim) {
        return AppResponseEntity.withSuccess(claimService.addNewClaim(claim));
    }

    @GetMapping(value = "")
    public AppResponseEntity<?> getClaimsInfo() {
        return AppResponseEntity.withSuccess(claimService.getClaimInfo(952));
    }
}
