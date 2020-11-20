package com.project.dco.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMailRequest {

    String from;
    String fromPass;
    String to;
    String cc;
    String bcc;
    String subject;
    String content;

}
