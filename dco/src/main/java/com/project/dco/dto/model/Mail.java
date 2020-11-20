package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mails")
public class Mail extends EntityBase {

    @Column(name = "mail_from")
    String from;
    @Column(name = "mail_to")
    String to;
    @Column(name = "mail_cc")
    String cc;
    @Column(name = "mail_bcc")
    String bcc;
    @Column(name = "mail_subject")
    String subject;
    String content;
}
