package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_end")
public class UserEnd extends EntityBase {

    @Column(name = "user_end_dni")
    String userEndDNI;

    @Column(name = "user_end_password")
    String userEndPassword;

    @Column(name = "user_end_phone")
    String userEndPhone;

    @Column(name = "user_end_email")
    String userEndMail;

    @Column(name = "user_end_address")
    String userEndAddress;
}
