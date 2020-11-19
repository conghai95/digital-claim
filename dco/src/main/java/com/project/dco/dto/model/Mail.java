package com.project.dco.dto.model;

import lombok.Data;

@Data
public class Mail {

    String mailUserName;
    String mailPassword;
    String domain;
    String to;
    String cc;
    String bcc;
    String sub;
    String content;
}
