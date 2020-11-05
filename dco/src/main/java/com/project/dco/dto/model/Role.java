package com.project.dco.dto.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    Integer id;

    @Column(name = "role_name")
    String roleName;
}
