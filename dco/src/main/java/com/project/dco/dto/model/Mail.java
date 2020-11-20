package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mail")
public class Mail extends EntityBase {

    String from;
    String to;
    String cc;
    String bcc;
    String subject;
    String content;
}
