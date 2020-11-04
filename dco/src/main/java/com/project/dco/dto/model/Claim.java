package com.project.dco.dto.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @Column(name = "claim_id")
    String claimId;

    @Column(name = "claim_title")
    String claimTitle;

    String content;

    @Column(name = "create_at")
    String createAt;

    String status;

    String DNI;
}
