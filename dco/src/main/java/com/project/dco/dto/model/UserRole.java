package com.project.dco.dto.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    String id;

    @Column(name = "DNI", nullable = false)
    String DNI;

    @Column(name = "role_id", nullable = false)
    String roleId;
}
