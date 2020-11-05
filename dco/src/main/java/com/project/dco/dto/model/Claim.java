package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "claims")
public class Claim extends EntityBase {

    @Column(name = "claim_title")
    String claimTitle;

    @Column(name = "claim_content")
    String claimContent;

    @Column(name = "create_by")
    Integer createBy;

    @Column(name = "update_by")
    Integer updateBy;

    @Column(name = "claim_file")
    Integer claimFile;

    @Column(name = "user_claims_id")
    Integer userClaimId;

    @Column(name = "user_end_id")
    Integer userEndId;

    String status;
}
