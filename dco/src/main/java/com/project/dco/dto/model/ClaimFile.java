package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "claims_file")
public class ClaimFile extends EntityBase {

    @Column(name = "claims_id")
    Integer claimsId;

    @Column(name = "file_id")
    Integer fileId;
}
