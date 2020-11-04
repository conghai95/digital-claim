package com.project.dco.dto.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    String DNI;

    String name;

    String age;

    String sex;

    String identifier;

    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    String address;

    @Column(name = "create_at")
    String createAt;
}
