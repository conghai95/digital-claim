package com.project.dco.controller;

import com.project.dco_common.api.AppResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/claims")
public class ClaimController {

    @GetMapping
    public AppResponseEntity<?> getClaims() {
        return AppResponseEntity.withSuccess("Hello world");
    }
}
