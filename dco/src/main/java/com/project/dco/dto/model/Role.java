package com.project.dco.dto.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    String roleId;

    @Column(name = "role_name")
    RoleName roleName;

    @Column(name = "create_at")
    String createAt;

    @Column(name = "is_disable")
    String isDisabled;
}
