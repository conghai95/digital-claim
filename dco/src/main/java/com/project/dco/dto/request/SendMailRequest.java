package com.project.dco.dto.request;

import lombok.Data;

@Data
public class SendMailRequest {

    String from;
    String fromPass;
    String to;
    String cc;
    String bcc;
    String subject;
    String content;

}
