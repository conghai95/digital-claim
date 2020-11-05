package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_claims")
public class UserClaims extends EntityBase {

    @Column(name = "user_claims_name")
    String userClaimsName;

    @Column(name = "user_claims_username")
    String userClaimsUsername;

    @Column(name = "user_claims_password")
    String userClaimsPassword;

    @Column(name = "user_claims_email")
    String userClaimsEmail;

    @Column(name = "user_claims_phone")
    String userClaimsPhone;

    @Column(name = "user_claims_role")
    Integer userClaimsRole;
}
